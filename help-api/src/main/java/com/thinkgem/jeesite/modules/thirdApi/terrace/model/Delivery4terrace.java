package com.thinkgem.jeesite.modules.thirdApi.terrace.model;

public class Delivery4terrace {

    /**
     * 添加车辆任务
     */
    private String code;          //车辆任务

    private String codeTranorder; //运输委托单号

    private String typeFrombill;  //运输任务类型 车辆是 1

    private String codeTruck;     //车牌号

    private String idcardDriver; //驾驶员身份证号

    private  String statusTruckmission;  //运输任务状态

    private String qtyLoad; //核载量

    private String nameDriver; //司机姓名

    private String telDriver; //司机手机号

    public String getCodeTranorder() {
        return codeTranorder;
    }

    public void setCodeTranorder(String codeTranorder) {
        this.codeTranorder = codeTranorder;
    }

    public String getTypeFrombill() {
        return typeFrombill;
    }

    public void setTypeFrombill(String typeFrombill) {
        this.typeFrombill = typeFrombill;
    }

    public String getCodeTruck() {
        return codeTruck;
    }

    public void setCodeTruck(String codeTruck) {
        this.codeTruck = codeTruck;
    }

    public String getIdcardDriver() {
        return idcardDriver;
    }

    public void setIdcardDriver(String idcardDriver) {
        this.idcardDriver = idcardDriver;
    }

    public String getStatusTruckmission() {
        return statusTruckmission;
    }

    public void setStatusTruckmission(String statusTruckmission) {
        this.statusTruckmission = statusTruckmission;
    }

    public String getQtyLoad() {
        return qtyLoad;
    }

    public void setQtyLoad(String qtyLoad) {
        this.qtyLoad = qtyLoad;
    }

    public String getNameDriver() {
        return nameDriver;
    }

    public void setNameDriver(String nameDriver) {
        this.nameDriver = nameDriver;
    }

    public String getTelDriver() {
        return telDriver;
    }

    public void setTelDriver(String telDriver) {
        this.telDriver = telDriver;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
