package com.thinkgem.jeesite.modules.thirdApi.terrace.model;

public class Company4terrace {
    /**
     * 添加运输公司
     */
    //运输公司组织机构代码
    private String code;
    //公司名称
    private String nameTranteam;
    //公司负责人
    private  String nameTranteamleader;
    //公司负责人电话
    private String telTranteamleader;
    //运输公司组织机构代码
    private String codeOrganizationid;
    //公司角色
    private String typeTranteam;
    //公司地址
    private String addressTranteam;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getNameTranteam() {
        return nameTranteam;
    }

    public void setNameTranteam(String nameTranteam) {
        this.nameTranteam = nameTranteam;
    }

    public String getNameTranteamleader() {
        return nameTranteamleader;
    }

    public void setNameTranteamleader(String nameTranteamleader) {
        this.nameTranteamleader = nameTranteamleader;
    }

    public String getTelTranteamleader() {
        return telTranteamleader;
    }

    public void setTelTranteamleader(String telTranteamleader) {
        this.telTranteamleader = telTranteamleader;
    }

    public String getCodeOrganizationid() {
        return codeOrganizationid;
    }

    public void setCodeOrganizationid(String codeOrganizationid) {
        this.codeOrganizationid = codeOrganizationid;
    }

    public String getTypeTranteam() {
        return typeTranteam;
    }

    public void setTypeTranteam(String typeTranteam) {
        this.typeTranteam = typeTranteam;
    }

    public String getAddressTranteam() {
        return addressTranteam;
    }

    public void setAddressTranteam(String addressTranteam) {
        this.addressTranteam = addressTranteam;
    }
}
