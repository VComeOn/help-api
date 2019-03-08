package com.thinkgem.jeesite.modules.sys.paramVo;

import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * @Description:
 * @Date: 2018/2/12
 * @Author: wcf
 */
public class DeliverySignVo {
    @NotEmpty
    private String deliveryBillNo;
    @NotEmpty
    private String arriveSignPhoto;		// 送到签收文件

    private String arriveSignRemark;		// 送到签收备注
    @NotNull
    private Integer arriveSignType;		//送到签收类型，0：正常签收，1：代签，2：其他
    @NotNull
    private Double arriveSignQuantity;		// 司机送到签收数
    @NotNull
    private BigDecimal lng;
    @NotNull
    private BigDecimal lat;

    public String getDeliveryBillNo() {
        return deliveryBillNo;
    }

    public void setDeliveryBillNo(String deliveryBillNo) {
        this.deliveryBillNo = deliveryBillNo;
    }

    public String getArriveSignPhoto() {
        return arriveSignPhoto;
    }

    public void setArriveSignPhoto(String arriveSignPhoto) {
        this.arriveSignPhoto = arriveSignPhoto;
    }

    public String getArriveSignRemark() {
        return arriveSignRemark;
    }

    public void setArriveSignRemark(String arriveSignRemark) {
        this.arriveSignRemark = arriveSignRemark;
    }

    public Integer getArriveSignType() {
        return arriveSignType;
    }

    public void setArriveSignType(Integer arriveSignType) {
        this.arriveSignType = arriveSignType;
    }

    public Double getArriveSignQuantity() {
        return arriveSignQuantity;
    }

    public void setArriveSignQuantity(Double arriveSignQuantity) {
        this.arriveSignQuantity = arriveSignQuantity;
    }

    public BigDecimal getLng() {
        return lng;
    }

    public void setLng(BigDecimal lng) {
        this.lng = lng;
    }

    public BigDecimal getLat() {
        return lat;
    }

    public void setLat(BigDecimal lat) {
        this.lat = lat;
    }
}
