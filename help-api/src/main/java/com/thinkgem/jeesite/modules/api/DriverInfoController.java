package com.thinkgem.jeesite.modules.api;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.oauth.entity.Result;
import com.thinkgem.jeesite.common.oauth.entity.ResultStatusCode;
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.vo.GridParam;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.base.entity.BaseCar;
import com.thinkgem.jeesite.modules.base.entity.BaseCompany;
import com.thinkgem.jeesite.modules.base.entity.BaseDriver;
import com.thinkgem.jeesite.modules.base.service.BaseCompanyService;
import com.thinkgem.jeesite.modules.base.service.BaseDriverService;
import com.thinkgem.jeesite.modules.bill.service.BillDeliveryService;
import com.thinkgem.jeesite.modules.sys.paramVo.Account;
import com.thinkgem.jeesite.modules.sys.service.SystemService;
import com.thinkgem.jeesite.modules.utils.RedisUtils;
import com.thinkgem.jeesite.modules.utils.SmsUtil;
import com.thinkgem.jeesite.modules.validGroups.EditPwdValid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;

/**
 * @Description: 司机个人信息相关api接口
 * @Date: 2018/1/8
 * @Author: wcf
 */
@RestController
@RequestMapping("/api/driver")
public class DriverInfoController extends BaseController{

    @Autowired
    private BaseDriverService driverService;
    @Autowired
    private RedisUtils redisUtils;
    @Autowired
    private BaseCompanyService baseCompanyService;

    /**
     * 获取当前登录的司机信息
     * @param request
     * @return
     */
    @RequestMapping("getInfo")
    public Result getInfo(HttpServletRequest request){
        return new Result(ResultStatusCode.OK, driverService.get(getDriverId(request)));
    }


     /**
      * @description 修改个人信息
      * @param
      * @author wcf
      * @date 2018/1/8
      * @return
      */
    @RequestMapping("editInfo")
    public Result editInfo(HttpServletRequest request, String headImg,
                           String name,String cardId,String cardImgA,
                           String cardImgB,String licenceId,
                           String licenceImgA,String licenceImgB,
                           String certificateId,String certificateImgA,String certificateImgB,Integer companyId){
        BaseDriver driver = driverService.get(getDriverId(request));
        if(StringUtils.isNotEmpty(headImg))
            driver.setHeadImg(headImg);
        if(StringUtils.isNotEmpty(name))
            driver.setName(name);
        if(StringUtils.isNotEmpty(cardId))
            driver.setCardId(cardId);
        if(StringUtils.isNotEmpty(cardImgA))
            driver.setCardImgA(cardImgA);
        if(StringUtils.isNotEmpty(cardImgB))
            driver.setCardImgB(cardImgB);
        if(StringUtils.isNotEmpty(licenceId))
            driver.setLicenceId(licenceId);
        if(StringUtils.isNotEmpty(licenceImgA))
            driver.setLicenceImgA(licenceImgA);
        if(StringUtils.isNotEmpty(licenceImgB))
            driver.setLicenceImgB(licenceImgB);
        if(StringUtils.isNotEmpty(certificateId))
            driver.setCertificateId(certificateId);
        if(StringUtils.isNotEmpty(certificateImgA))
            driver.setCertificateImgA(certificateImgA);
        if(StringUtils.isNotEmpty(certificateImgB))
            driver.setCertificateImgB(certificateImgB);
        driver.setCompanyId(companyId);
        driver.setStatus(0);
        driverService.save(driver);

        return new Result(ResultStatusCode.OK);
    }

    /**
     * @description 绑定手机-获取验证码
     * @param
     * @author wcf
     * @date 2018/1/9
     * @return
     */
    @RequestMapping("bindPhoneValidCode")
    public Result bindPhoneValidCode(@RequestParam(required = true) String phone){
        BaseDriver driver = driverService.getByPhone(phone);
        if(driver != null){
            return new Result(Global.ERROR_CODE, "该手机号已存在");
        }
        //String validCode = StringUtils.getRandomNumberByLength(6);
        String validCode = "1234";
        //SmsUtil.sendValidCodeSms(phone, validCode);
        redisUtils.setValue(RedisUtils.PHONE_VALID_CODE + phone, validCode, Global.VALID_CODE_TIME);

        return new Result(ResultStatusCode.OK);
    }

     /**
      * @description 绑定手机
      * @param
      * @author wcf
      * @date 2018/1/8
      * @return
      */
    @RequestMapping("bindPhone")
    public Result bindPhone(HttpServletRequest request, @RequestParam(required = true) String phone, @RequestParam(required = true) String validCode){
        if(!validCode.equals(redisUtils.getValue(RedisUtils.PHONE_VALID_CODE + phone))){
            return new Result(Global.ERROR_CODE, "验证码错误");
        }
        BaseDriver driver = driverService.get(getDriverId(request));
        driver.setPhone(phone);
        driverService.save(driver);
        //删除validcode
        redisUtils.delete(RedisUtils.PHONE_VALID_CODE + phone);

        return new Result(ResultStatusCode.OK);
    }

    /**
     * 修改密码发送验证码
     * @param request
     * @return
     */
    @RequestMapping("editPwdValidCode")
    public Result editPwdValidCode(HttpServletRequest request){
        BaseDriver driver = driverService.get(getDriverId(request));
        //String validCode = StringUtils.getRandomNumberByLength(6);
        String validCode = "1234";
        //SmsUtil.sendValidCodeSms(driver.getPhone(), validCode);
        //将验证码存放到redis，有效期5分钟
        redisUtils.setValue(RedisUtils.PHONE_VALID_CODE + driver.getPhone(), validCode, Global.VALID_CODE_TIME);
        return new Result(ResultStatusCode.OK);
    }
     /**
      * @description 修改密码
      * @param
      * @author wcf
      * @date 2018/1/8
      * @return
      */
    @RequestMapping("editPwd")
    public Result editPwd(HttpServletRequest request, @RequestParam(required = true) String validCode, @RequestParam(required = true) String newPwd){
        if(StringUtils.isBlank(newPwd)){
            return new Result(Global.ERROR_CODE,"新密码不能空");
        }
        BaseDriver driver = driverService.get(getDriverId(request));
        if(!validCode.equals(redisUtils.getValue(RedisUtils.PHONE_VALID_CODE + driver.getPhone()))){
            return new Result(Global.ERROR_CODE, "验证码错误");
        }
        driver.setPwd(SystemService.entryptPassword(newPwd));
        driverService.save(driver);

        //删除validcode
        redisUtils.delete(RedisUtils.PHONE_VALID_CODE + driver.getPhone());

        return new Result(ResultStatusCode.OK);
    }

    /**
     * @description 获取可选择的运输公司
     * @param
     * @author wcf
     * @date 2018/1/26
     * @return
     */
    @RequestMapping("/getCompanyList")
    public Result getCarList(@Validated GridParam param, HttpServletRequest request, String baseCompany){

        BaseCompany company=new BaseCompany();
        company.setName(baseCompany);

        return new Result(ResultStatusCode.OK, baseCompanyService.getCompanyList(company, param));
    }
}
