package com.thinkgem.jeesite.modules.thirdApi.terrace.model;

import java.math.BigDecimal;

public class Truck4terrace {

    private String code;		// 车牌号

    private String typeTruck;  //车型

    private BigDecimal qtyLoad;		// 荷载量

    private String codeTeam;	//所属运输车队公司机构代码

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTypeTruck() {
        return typeTruck;
    }

    public void setTypeTruck(String typeTruck) {
        this.typeTruck = typeTruck;
    }

    public BigDecimal getQtyLoad() {
        return qtyLoad;
    }

    public void setQtyLoad(BigDecimal qtyLoad) {
        this.qtyLoad = qtyLoad;
    }

    public String getCodeTeam() {
        return codeTeam;
    }

    public void setCodeTeam(String codeTeam) {
        this.codeTeam = codeTeam;
    }
}
