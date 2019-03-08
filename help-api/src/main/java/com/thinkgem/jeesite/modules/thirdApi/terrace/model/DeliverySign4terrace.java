package com.thinkgem.jeesite.modules.thirdApi.terrace.model;

/**
 * @Description:
 * @Date: 2018/10/16
 * @Author: wcf
 */
public class DeliverySign4terrace {


    public String codeMaster; //车辆任务单号

    public String codeTruck; //车牌号

    public String telDriver; //司机手机号

    public String dateAppsign;//签收时间

    public String qtyAppsign;// 签收量

    public String memo;//备注

    public String nameDriver; //司机姓名

    public String qtyLoad; //核载量

    public String getCodeMaster() {
        return codeMaster;
    }

    public void setCodeMaster(String codeMaster) {
        this.codeMaster = codeMaster;
    }

    public String getCodeTruck() {
        return codeTruck;
    }

    public void setCodeTruck(String codeTruck) {
        this.codeTruck = codeTruck;
    }

    public String getTelDriver() {
        return telDriver;
    }

    public void setTelDriver(String telDriver) {
        this.telDriver = telDriver;
    }

    public String getDateAppsign() {
        return dateAppsign;
    }

    public void setDateAppsign(String dateAppsign) {
        this.dateAppsign = dateAppsign;
    }

    public String getQtyAppsign() {
        return qtyAppsign;
    }

    public void setQtyAppsign(String qtyAppsign) {
        this.qtyAppsign = qtyAppsign;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getNameDriver() {
        return nameDriver;
    }

    public void setNameDriver(String nameDriver) {
        this.nameDriver = nameDriver;
    }

    public String getQtyLoad() {
        return qtyLoad;
    }

    public void setQtyLoad(String qtyLoad) {
        this.qtyLoad = qtyLoad;
    }
}
