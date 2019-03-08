package com.thinkgem.jeesite.modules.baiduApi.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * @Description:
 * @Date: 2018/3/27
 * @Author: wcf
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class AddressResult{
    private int precise;

    private AddressLocation location;

    public int getPrecise() {
        return precise;
    }

    public void setPrecise(int precise) {
        this.precise = precise;
    }

    public AddressLocation getLocation() {
        return location;
    }

    public void setLocation(AddressLocation location) {
        this.location = location;
    }
}
