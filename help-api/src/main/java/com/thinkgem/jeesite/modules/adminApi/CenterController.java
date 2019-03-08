package com.thinkgem.jeesite.modules.adminApi;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.oauth.entity.Result;
import com.thinkgem.jeesite.common.oauth.entity.ResultStatusCode;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.paramVo.Account;
import com.thinkgem.jeesite.modules.sys.service.SystemService;
import com.thinkgem.jeesite.modules.sys.service.UserService;
import com.thinkgem.jeesite.modules.validGroups.EditAdminInfo;
import com.thinkgem.jeesite.modules.validGroups.EditAdminPwd;
import com.thinkgem.jeesite.modules.validGroups.SetPushType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * @Description:
 * @Date: 2018/1/24
 * @Author: wcf
 */
@RestController("adminCenterController")
@RequestMapping("/admin/api/center")
public class CenterController extends BaseController{
    @Autowired
    private UserService userService;

    /**
     * 获取用户基础信息
     * @param request
     * @return
     */
    @RequestMapping("getAdminInfo")
    public Result getAdminInfo(HttpServletRequest request){
        return new Result(ResultStatusCode.OK, userService.get(getAdminId(request)));
    }
     
     /**    
      * @description 修改密码
      * @param 
      * @author wcf
      * @date 2018/1/24 
      * @return   
      */  
    @RequestMapping("editPwd")
    public Result editPwd(@Validated({EditAdminPwd.class}) Account account, HttpServletRequest request){
        User user = userService.get(getAdminId(request));

        if(!account.getNewPwd().equals(account.getNewPwd2())){
            return new Result(Global.ERROR_CODE, "两次输入的密码不一致");
        }
        user.setPassword(SystemService.entryptPassword(account.getNewPwd()));
        userService.updatePasswordById(user);

        return new Result(ResultStatusCode.OK);
    }
    
     
     /**    
      * @description  编辑个人资料
      * @param 
      * @author wcf
      * @date 2018/1/24 
      * @return   
      */  
    @RequestMapping("eidtUserInfo")
    public Result eidtUserInfo(@Validated({EditAdminInfo.class}) User user, HttpServletRequest request){
        user.setId(getAdminId(request));
        user.setUpdateDate(new Date());
        userService.updateUserInfo(user);

        return new Result(ResultStatusCode.OK);
    }

     
     /**    
      * @description 设置推送方式
      * @param 
      * @author wcf
      * @date 2018/1/24 
      * @return   
      */  
    @RequestMapping("setPushType")
    public Result setPushType(@Validated({SetPushType.class}) User user, HttpServletRequest request){
        user.setId(getAdminId(request));
        userService.updatePushType(user);

        return new Result(ResultStatusCode.OK);
    }
}
