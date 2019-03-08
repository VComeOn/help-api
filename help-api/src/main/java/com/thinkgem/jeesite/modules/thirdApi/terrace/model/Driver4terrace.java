package com.thinkgem.jeesite.modules.thirdApi.terrace.model;

public class Driver4terrace {
    //驾驶员身份证号
    private String code;
    //驾驶员从业资格证号
    private String codeCertificate;
    //电话号码
    private String telDriver;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCodeCertificate() {
        return codeCertificate;
    }

    public void setCodeCertificate(String codeCertificate) {
        this.codeCertificate = codeCertificate;
    }

    public String getTelDriver() {
        return telDriver;
    }

    public void setTelDriver(String telDriver) {
        this.telDriver = telDriver;
    }
}
