package com.thinkgem.jeesite.modules.adminApi.Vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.thinkgem.jeesite.common.persistence.DataEntity;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

/**
 * @Description:
 * @Date: 2018/3/7
 * @Author: wcf
 */
public class DriverVo extends DataEntity<DriverVo> {
    private String headImg;		// 头像
    private String name;		// 姓名
    private Integer isOnDuty;		// 是否在执勤
    private String companyName;     //公司名称
    private String deliveryBillNo;      //运单号
    private Integer deliveryStatus;         //运单状态
    private Integer companyId;

    public String getHeadImg() {
        return headImg;
    }

    public void setHeadImg(String headImg) {
        this.headImg = headImg;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getIsOnDuty() {
        return isOnDuty;
    }

    public void setIsOnDuty(Integer isOnDuty) {
        this.isOnDuty = isOnDuty;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getDeliveryBillNo() {
        return deliveryBillNo;
    }

    public void setDeliveryBillNo(String deliveryBillNo) {
        this.deliveryBillNo = deliveryBillNo;
    }

    public Integer getDeliveryStatus() {
        return deliveryStatus;
    }

    public void setDeliveryStatus(Integer deliveryStatus) {
        this.deliveryStatus = deliveryStatus;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }
}
