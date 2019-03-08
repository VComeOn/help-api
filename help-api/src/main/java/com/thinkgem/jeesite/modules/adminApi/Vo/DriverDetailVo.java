package com.thinkgem.jeesite.modules.adminApi.Vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.modules.bill.entity.BillDeliverySign;
import com.thinkgem.jeesite.modules.bill.entity.BillLading;
import com.thinkgem.jeesite.modules.driver.entity.DriverFaultRecord;

import java.util.Date;
import java.util.List;

/**
 * @Description:
 * @Date: 2018/3/7
 * @Author: wcf
 */
public class DriverDetailVo extends DataEntity<DriverDetailVo>{
    @JsonInclude(JsonInclude.Include.ALWAYS)
    private String headImg;		// 头像
    private String account;		// 帐号
    private String name;		// 姓名
    private String phone;		// 电话号码
    private Integer companyId;		// 公司id
    private Integer isOnDuty;		// 是否在执勤
    @JsonInclude(JsonInclude.Include.ALWAYS)
    private String plateNumber;		// 绑定车牌号
    @JsonInclude(JsonInclude.Include.ALWAYS)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date lastSignDate;      //最后执勤时间
    private String companyName;     //公司名称
    @JsonInclude(JsonInclude.Include.ALWAYS)
    private String label;           //标签
    private Double arriveSign;      //已签收数量

    private BillDeliveryVo delivery;      //运单信息
    private BillLading billLading;      //提单信息
    List<DriverFaultRecord> faultRecordList;    //异常状态列表

    List<BillDeliverySign> billDeliverySigns;  //运输任务签收表

    public String getHeadImg() {
        return headImg;
    }

    public void setHeadImg(String headImg) {
        this.headImg = headImg;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public Integer getIsOnDuty() {
        return isOnDuty;
    }

    public void setIsOnDuty(Integer isOnDuty) {
        this.isOnDuty = isOnDuty;
    }

    public String getPlateNumber() {
        return plateNumber;
    }

    public void setPlateNumber(String plateNumber) {
        this.plateNumber = plateNumber;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public BillDeliveryVo getDelivery() {
        return delivery;
    }

    public void setDelivery(BillDeliveryVo delivery) {
        this.delivery = delivery;
    }

    public BillLading getBillLading() {
        return billLading;
    }

    public void setBillLading(BillLading billLading) {
        this.billLading = billLading;
    }

    public List<DriverFaultRecord> getFaultRecordList() {
        return faultRecordList;
    }

    public void setFaultRecordList(List<DriverFaultRecord> faultRecordList) {
        this.faultRecordList = faultRecordList;
    }

    public Date getLastSignDate() {
        return lastSignDate;
    }

    public void setLastSignDate(Date lastSignDate) {
        this.lastSignDate = lastSignDate;
    }

    public Double getArriveSign() {
        return arriveSign;
    }

    public void setArriveSign(Double arriveSign) {
        this.arriveSign = arriveSign;
    }

    public List<BillDeliverySign> getBillDeliverySigns() {
        return billDeliverySigns;
    }

    public void setBillDeliverySigns(List<BillDeliverySign> billDeliverySigns) {
        this.billDeliverySigns = billDeliverySigns;
    }
}
