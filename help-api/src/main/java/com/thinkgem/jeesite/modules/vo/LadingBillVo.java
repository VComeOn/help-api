package com.thinkgem.jeesite.modules.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.thinkgem.jeesite.common.persistence.BaseEntity;
import com.thinkgem.jeesite.common.persistence.DataEntity;

import java.util.Date;

/**
 * @Description:
 * @Date: 2018/2/6
 * @Author: wcf
 */
public class LadingBillVo extends DataEntity<LadingBillVo> {
    private Integer id;
    private String ladingBillNo;		// 提货单号
    private Integer status;		// 提单状态，0：未完成，1：已完成
    private String storageAddress;		// 储存地点
    private String materielName;		// 物料名称
    private String deliveryAddress;		// 送货地址
    private Date startDeliveryDate;		//开始运输时间

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLadingBillNo() {
        return ladingBillNo;
    }

    public void setLadingBillNo(String ladingBillNo) {
        this.ladingBillNo = ladingBillNo;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getStorageAddress() {
        return storageAddress;
    }

    public void setStorageAddress(String storageAddress) {
        this.storageAddress = storageAddress;
    }

    public String getMaterielName() {
        return materielName;
    }

    public void setMaterielName(String materielName) {
        this.materielName = materielName;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public Date getStartDeliveryDate() {
        return startDeliveryDate;
    }

    public void setStartDeliveryDate(Date startDeliveryDate) {
        this.startDeliveryDate = startDeliveryDate;
    }
}
