/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.api;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.oauth.entity.Audience;
import com.thinkgem.jeesite.common.oauth.entity.Result;
import com.thinkgem.jeesite.common.oauth.entity.ResultStatusCode;
import com.thinkgem.jeesite.common.oauth.utils.JwtHelper;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.annotation.IgnoreSecurity;
import com.thinkgem.jeesite.modules.base.entity.BaseDriver;
import com.thinkgem.jeesite.modules.base.service.BaseDriverService;
import com.thinkgem.jeesite.modules.sys.paramVo.Account;
import com.thinkgem.jeesite.modules.sys.service.SystemService;
import com.thinkgem.jeesite.modules.utils.RedisUtils;
import com.thinkgem.jeesite.modules.utils.SmsUtil;
import com.thinkgem.jeesite.modules.validGroups.EditPwdValid;
import com.thinkgem.jeesite.modules.validGroups.LoginValid;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 登录Controller
 * @author ThinkGem
 * @version 2013-5-31
 */
@RestController
@RequestMapping("/api/autho")
public class LoginController extends BaseController{
    @Autowired
    private BaseDriverService baseDriverService;
    @Autowired
    private Audience audience;
    @Autowired
    private RedisUtils redisUtils;

    /**
     * @description 新增车辆
     * @param
     * @author wcf
     * @date 2018/1/5
     * @return
     */
/*    @RequestMapping("register")
    @IgnoreSecurity
    public Result register(@Validated({EditPwdValid.class}) Account account){
        if(!account.getValidCode().equals(redisUtils.getValue(RedisUtils.PHONE_VALID_CODE + account.getPhone()))){
            return new Result(Global.ERROR_CODE, "验证码不正确");
        }
        BaseDriver driver=baseDriverService.getByPhone(account.getPhone());
        if(driver != null){
            return new Result(Global.ERROR_CODE, "该手机号已存在");
        }
        if(!account.getNewPwd().equals(account.getNewPwd2())){
            return new Result(Global.ERROR_CODE, "两次输入密码不一致");
        }
        BaseDriver driverInsert=new BaseDriver();
        driverInsert.setPhone(account.getPhone());
        driverInsert.setPwd(SystemService.entryptPassword(account.getNewPwd()));
        driverInsert.setPoint(0);
        driverInsert.setPointTotal(0);
        driverInsert.setLevel(0);
        driverInsert.setIsOnDuty(0);//不在执勤
        driverInsert.setStatus(0);
        baseDriverService.save(driverInsert);
        //删除验证码
        redisUtils.delete(RedisUtils.PHONE_VALID_CODE + account.getPhone());
        return new Result(ResultStatusCode.OK);
    }*/



    /**
     *
     * @Description:救援车辆登陆--普通登陆
     * @return
     * @author: qj
     * @date: 2019年3月13日
     */
    @RequestMapping("login")
    public Result login(@Validated({LoginValid.class}) Account account){
        try{
            if(account.getClientId() == null || !account.getClientId().equals(audience.getClientId())){
                return new Result(ResultStatusCode.INVALID_CLIENTID, null);
            }

            BaseDriver driver = baseDriverService.getBaseDriverByPlateNumber(account.getAccount());

            if(driver != null){
                //一个用户同时只能有一台设备登录（用户端）
                String redisToken = redisUtils.getToken(driver.getId());
                if(StringUtils.isNotEmpty(redisToken)){
                    String HeadStr = redisToken.substring(0, 6).toLowerCase();
                    if(HeadStr.equals("bearer")) {
                        redisToken = redisToken.substring(6);

                        Claims claims = JwtHelper.parseJWT(redisToken, audience.getBase64Secret());
                        //判断密钥是否相等，如果不等则认为时无效的token
                        if (claims != null) {
                            return new Result(ResultStatusCode.LOGINED_IN.getCode(), ResultStatusCode.LOGINED_IN.getMsg(), null);
                        }
                    }
                }
                if(SystemService.validatePassword(account.getPwd(), driver.getPwd())){
                    //设置单次的token的过期时间为凌晨3点-4点，用于避免token在即将失效时继续使用旧的token访问
                    Calendar cal = Calendar.getInstance();
                    cal.add(Calendar.DAY_OF_MONTH, +12);
                    cal.set(Calendar.HOUR_OF_DAY, 3);
                    //cal.add(Calendar.MINUTE, +2);//用于测试，只有2分钟有效期

                    //拼装accessToken
                    String accessToken = JwtHelper.createJWT(driver.getPlateNumber(), driver.getId(),
                            audience.getClientId(), audience.getName(),
                            cal.getTimeInMillis() - new Date().getTime(), audience.getBase64Secret());
                    //将该用户的access_token储存到redis服务器，保证一段时间内只能有一个有效的access_token
                    redisUtils.setToken(driver.getId(), accessToken, cal.getTimeInMillis() - new Date().getTime());

                    //获取refresh_token，有效期为7天，每次通过refresh_token获取access_token时，会刷新refresh_token的时间
                    String refreshToken =  JwtHelper.creteaRefreshToken(driver.getPlateNumber(), driver.getId(), audience.getClientId(), audience.getName(), audience.getBase64Secret());
                    redisUtils.setRefreshToken(driver.getId(), refreshToken);

                    Map<String, Object> result = new HashMap<String, Object>();
                    result.put("access_token", "bearer" + accessToken);
                    result.put("refresh_token", "bearer" + refreshToken);
                    result.put("driver", driver);
                    //添加登陆日志代码

                    return new Result(ResultStatusCode.OK, result);
                }else{
                    return new Result(ResultStatusCode.NOT_EXIST_USER_OR_ERROR_PWD, null);
                }
            }else{
                return new Result(ResultStatusCode.NOT_EXIST_USER_OR_ERROR_PWD, null);
            }

        }catch(Exception e){
            e.printStackTrace();
            return new Result(ResultStatusCode.SYSTEM_ERR, null);
        }
    }
    /**
     * @Description: 用户退出登陆
     * @param
     * @return
     * @author: wcf
     * @date: 2017年7月6日
     */
    @RequestMapping("logout")
    public Result logout(HttpServletRequest request){
        redisUtils.delete(RedisUtils.ACCESS_TOKEN + getDriverId(request));
        redisUtils.delete(RedisUtils.REFRESH_TOKEN + getDriverId(request));
        return new Result(ResultStatusCode.OK.getCode(), ResultStatusCode.OK.getMsg(), null);
    }
    /**
     * @Description: 用于检测token是否还有效，如果无效则可以通过getToken方法获取新的token
     * @return
     * @author: wcf
     * @date: 2017年7月7日
     */
    @RequestMapping("checkToken")
    public Result checkToken(){
        return new Result(ResultStatusCode.OK.getCode(), ResultStatusCode.OK.getMsg(), null);
    }

    /**
     * @Description: 通过refreshToken获取新的access_token，同时也刷新refreshToken的有效期
     * @return
     * @author: wcf
     * @date: 2017年7月7日
     */
    @RequestMapping("getToken")
    @IgnoreSecurity
    public Result getToken(String refreshToken){
        try{
            if(StringUtils.isNotEmpty(refreshToken)){
                String HeadStr = refreshToken.substring(0, 6).toLowerCase();
                if(HeadStr.equals("bearer")){
                    refreshToken = refreshToken.substring(6);

                    Claims claims = JwtHelper.parseJWT(refreshToken, audience.getBase64Secret());
                    //判断密钥是否相等，如果不等则认为时无效的token
                    if(claims != null){
                        //refresh_token未失效，refresh_token需要和redis服务器中的储存的refresh_token值一样才有效
                        Integer driverId = (Integer)claims.get("driverId");
                        if(claims.getAudience().equals(audience.getClientId()) && refreshToken.equals(redisUtils.getRefreshToken(driverId))){

                            BaseDriver driver = baseDriverService.get(driverId);

                            Map<String, String> result = new HashMap<String, String>();

                            //设置单次的token的过期时间为凌晨3点-4点，用于避免token在即将失效时继续使用旧的token访问
                            Calendar cal = Calendar.getInstance();
                            cal.add(Calendar.DAY_OF_MONTH, +1);
                            cal.set(Calendar.HOUR_OF_DAY, 3);

                            //拼装accessToken
                            String accessToken = JwtHelper.createJWT(driver.getPlateNumber(), driver.getId(),
                                    audience.getClientId(), audience.getName(),
                                    cal.getTimeInMillis() - new Date().getTime(), audience.getBase64Secret());

                            //获取refresh_token，有效期为7天，每次通过refresh_token获取access_token时，会刷新refresh_token的时间
                            String refreshToken_new = JwtHelper.creteaRefreshToken(driver.getPlateNumber(), driver.getId(),
                                    audience.getClientId(), audience.getName(), audience.getBase64Secret());

                            result.put("access_token", "bearer" + accessToken);
                            result.put("refresh_token", "bearer" + refreshToken_new);
                            //更新redis数据
                            redisUtils.delete(RedisUtils.ACCESS_TOKEN + driver.getId());
                            redisUtils.delete(RedisUtils.REFRESH_TOKEN + driver.getId());
                            redisUtils.setToken(driver.getId(), accessToken, cal.getTimeInMillis() - new Date().getTime());
                            redisUtils.setRefreshToken(driver.getId(), refreshToken_new);

                            return new Result(ResultStatusCode.OK.getCode(), ResultStatusCode.OK.getMsg(), result);
                        }
                    }
                }
            }
            return new Result(ResultStatusCode.INVALID_TOKEN.getCode(), ResultStatusCode.INVALID_TOKEN.getMsg(), null);
        }catch(Exception e){
            return new Result(ResultStatusCode.SYSTEM_ERR.getCode(), ResultStatusCode.SYSTEM_ERR.getMsg(), null);
        }
    }
}
