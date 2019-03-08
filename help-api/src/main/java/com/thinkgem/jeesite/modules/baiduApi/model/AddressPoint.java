package com.thinkgem.jeesite.modules.baiduApi.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * @Description:
 * @Date: 2018/3/27
 * @Author: wcf
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class AddressPoint extends BaseResponse{
    private AddressResult result;

    public AddressResult getResult() {
        return result;
    }

    public void setResult(AddressResult result) {
        this.result = result;
    }
}
