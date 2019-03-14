package com.thinkgem.jeesite.modules.sys.paramVo;

import com.thinkgem.jeesite.modules.validGroups.EditAdminPwd;
import com.thinkgem.jeesite.modules.validGroups.EditPwdValid;
import com.thinkgem.jeesite.modules.validGroups.LoginValid;
import com.thinkgem.jeesite.modules.validGroups.SetPushType;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @Description:
 * @Date: 2018/1/4
 * @Author: wcf
 */
public class Account {

    @NotEmpty(groups = LoginValid.class, message = "请填写车牌号")
    private String account;
    @NotEmpty(groups = LoginValid.class, message = "请填写密码")
    private String pwd;
    @NotEmpty(groups = LoginValid.class, message = "请填写clientId")
    private String clientId;

    @NotEmpty(groups = EditPwdValid.class, message = "请填写手机号")
    private String phone;
    @NotEmpty(groups = EditPwdValid.class, message = "请填写验证码")
    private String validCode;
    @NotEmpty(groups = {EditPwdValid.class, EditAdminPwd.class}, message = "请填写密码")
    private String newPwd;
    @NotEmpty(groups = EditAdminPwd.class, message = "")
    private String newPwd2;

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getValidCode() {
        return validCode;
    }

    public void setValidCode(String validCode) {
        this.validCode = validCode;
    }

    public String getNewPwd() {
        return newPwd;
    }

    public void setNewPwd(String newPwd) {
        this.newPwd = newPwd;
    }

    public String getNewPwd2() {
        return newPwd2;
    }

    public void setNewPwd2(String newPwd2) {
        this.newPwd2 = newPwd2;
    }
}
