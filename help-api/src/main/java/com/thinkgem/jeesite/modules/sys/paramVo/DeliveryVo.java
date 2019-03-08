package com.thinkgem.jeesite.modules.sys.paramVo;

import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * @Description:
 * @Date: 2018/2/1
 * @Author: wcf
 */
public class DeliveryVo {
    @NotEmpty
    private String deliveryBillNo;
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
