package com.thinkgem.jeesite.modules.api;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.oauth.entity.Result;
import com.thinkgem.jeesite.common.oauth.entity.ResultStatusCode;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.vo.GridParam;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.base.entity.BaseDriver;
import com.thinkgem.jeesite.modules.base.service.BaseDriverService;
import com.thinkgem.jeesite.modules.sys.service.SystemService;
import com.thinkgem.jeesite.modules.utils.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
        driverService.save(driver);

        return new Result(ResultStatusCode.OK);
    }


}
