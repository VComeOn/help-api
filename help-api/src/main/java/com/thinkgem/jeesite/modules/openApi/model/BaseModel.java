package com.thinkgem.jeesite.modules.openApi.model;

import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;

/**
 * @Description:
 * @Date: 2018/1/30
 * @Author: wcf
 */
public class BaseModel {

    @NotNull
    private Long t;
    @NotEmpty
    private String sign;

    public Long getT() {
        return t;
    }

    public void setT(Long t) {
        this.t = t;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }
}
