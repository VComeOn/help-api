package com.thinkgem.jeesite.modules.openApi.model;

import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;

/**
 * @Description:
 * @Date: 2018/3/5
 * @Author: wcf
 */
public class BillLadingCheckModel {
    @NotEmpty
    private String ladingBillNo;
    private Boolean status;
    @NotNull
    private Integer ladingStatus;		// 提单状态，0：未完成，1：已完成2：已挂起

    public String getLadingBillNo() {
        return ladingBillNo;
    }

    public void setLadingBillNo(String ladingBillNo) {
        this.ladingBillNo = ladingBillNo;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Integer getLadingStatus() {
        return ladingStatus;
    }

    public void setLadingStatus(Integer ladingStatus) {
        this.ladingStatus = ladingStatus;
    }
}
