/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.adminApi.Vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.modules.bill.entity.BillDeliveryComment;
import com.thinkgem.jeesite.modules.bill.entity.BillLading;
import org.hibernate.validator.constraints.Length;

import java.util.Date;

/**
 * 运单信息Entity
 * @author wcf
 * @version 2018-01-15
 */
public class BillDeliveryVo extends DataEntity<BillDeliveryVo> {

	private static final long serialVersionUID = 1L;
	private String deliveryBillNo;		// 运单号
	private String ladingBillNo;		// 提货单号
	private String outboundBillNo;		//出库单号
	private Double signQuantity;		// 司机提货签收数量
	private Date signDate;		// 提货签收时间
	private Double arriveSignQuantity;		// 司机送到签收数
	private Date arriveSignDate;		// 司机送到签收时间
	private Integer status;		// 状态，0：未分配，1：已分配，2：运输中，3：已完成，4：中途停车，5：已到达
	private Integer driverId;		// 司机id
	private String plateNumber;		// 车牌号
	private Integer companyId;		// 运输公司id
	private String signPhoto;		// 提货签收单文件
	private String arriveSignPhoto;		// 送到签收文件
	private String arriveSignRemark;		// 送到签收备注
	private Integer arriveSignType;		//送到签收类型，0：正常签收，1：代签，2：其他
	private Date startDeliveryDate;		//开始运输时间
	private Date endDeliveryDate;		//结束运输时间
	private Double willQuantity;		//应发数
	private Double tareWeight;			//皮重
	private Double grossWeight;			//毛重
	private String tankNo;				//罐号
	private String shipperName;			//货主名称
	private Integer isBalance;			//是否结算
	private Integer balanceUser;		//结算人
	private Date balanceDate;			//结算时间
	private Integer isErrorStop;		//是否有异常停靠
	private Integer isCloseApp;			//是否有关闭app
	private Integer isAdminComment;		//后台是否评价
	private Integer isCustomerComment;	//客户是否评价

	private Date lastRunDate;			//最后一次开始运输时间


	public BillDeliveryVo() {
		super();
	}

	public BillDeliveryVo(Integer id){
		super(id);
	}

	@Length(min=1, max=50, message="出库单号长度必须介于 1 和 50 之间")
	public String getDeliveryBillNo() {
		return deliveryBillNo;
	}

	public void setDeliveryBillNo(String deliveryBillNo) {
		this.deliveryBillNo = deliveryBillNo;
	}
	
	@Length(min=1, max=50, message="提货单号长度必须介于 1 和 50 之间")
	public String getLadingBillNo() {
		return ladingBillNo;
	}

	public void setLadingBillNo(String ladingBillNo) {
		this.ladingBillNo = ladingBillNo;
	}

	@JsonInclude(JsonInclude.Include.ALWAYS)
	public Double getSignQuantity() {
		return signQuantity;
	}

	public void setSignQuantity(Double signQuantity) {
		this.signQuantity = signQuantity;
	}

	@JsonInclude(JsonInclude.Include.ALWAYS)
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getSignDate() {
		return signDate;
	}

	public void setSignDate(Date signDate) {
		this.signDate = signDate;
	}

	@JsonInclude(JsonInclude.Include.ALWAYS)
	public Double getArriveSignQuantity() {
		return arriveSignQuantity;
	}

	public void setArriveSignQuantity(Double arriveSignQuantity) {
		this.arriveSignQuantity = arriveSignQuantity;
	}

	@JsonInclude(JsonInclude.Include.ALWAYS)
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getArriveSignDate() {
		return arriveSignDate;
	}

	public void setArriveSignDate(Date arriveSignDate) {
		this.arriveSignDate = arriveSignDate;
	}
	
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	
	public Integer getDriverId() {
		return driverId;
	}

	public void setDriverId(Integer driverId) {
		this.driverId = driverId;
	}
	
	@Length(min=0, max=20, message="车牌号长度必须介于 0 和 20 之间")
	public String getPlateNumber() {
		return plateNumber;
	}

	public void setPlateNumber(String plateNumber) {
		this.plateNumber = plateNumber;
	}
	
	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}
	
	@Length(min=0, max=1000, message="提货签收单文件长度必须介于 0 和 1000 之间")
	@JsonInclude(JsonInclude.Include.ALWAYS)
	public String getSignPhoto() {
		return signPhoto;
	}

	public void setSignPhoto(String signPhoto) {
		this.signPhoto = signPhoto;
	}
	
	@Length(min=0, max=1000, message="送到签收文件长度必须介于 0 和 1000 之间")
	@JsonInclude(JsonInclude.Include.ALWAYS)
	public String getArriveSignPhoto() {
		return arriveSignPhoto;
	}

	public void setArriveSignPhoto(String arriveSignPhoto) {
		this.arriveSignPhoto = arriveSignPhoto;
	}
	
	@Length(min=0, max=500, message="送到签收备注长度必须介于 0 和 500 之间")
	public String getArriveSignRemark() {
		return arriveSignRemark;
	}

	public void setArriveSignRemark(String arriveSignRemark) {
		this.arriveSignRemark = arriveSignRemark;
	}

	public Date getStartDeliveryDate() {
		return startDeliveryDate;
	}

	public void setStartDeliveryDate(Date startDeliveryDate) {
		this.startDeliveryDate = startDeliveryDate;
	}

	public Date getEndDeliveryDate() {
		return endDeliveryDate;
	}

	public void setEndDeliveryDate(Date endDeliveryDate) {
		this.endDeliveryDate = endDeliveryDate;
	}

	@JsonInclude(JsonInclude.Include.ALWAYS)
	public String getOutboundBillNo() {
		return outboundBillNo;
	}

	public void setOutboundBillNo(String outboundBillNo) {
		this.outboundBillNo = outboundBillNo;
	}

	public Double getWillQuantity() {
		return willQuantity;
	}

	public void setWillQuantity(Double willQuantity) {
		this.willQuantity = willQuantity;
	}

	public Double getTareWeight() {
		return tareWeight;
	}

	public void setTareWeight(Double tareWeight) {
		this.tareWeight = tareWeight;
	}

	public Double getGrossWeight() {
		return grossWeight;
	}

	public void setGrossWeight(Double grossWeight) {
		this.grossWeight = grossWeight;
	}

	public String getTankNo() {
		return tankNo;
	}

	public void setTankNo(String tankNo) {
		this.tankNo = tankNo;
	}

	public String getShipperName() {
		return shipperName;
	}

	public void setShipperName(String shipperName) {
		this.shipperName = shipperName;
	}

	public Integer getArriveSignType() {
		return arriveSignType;
	}

	public void setArriveSignType(Integer arriveSignType) {
		this.arriveSignType = arriveSignType;
	}

	public Integer getIsErrorStop() {
		return isErrorStop;
	}

	public void setIsErrorStop(Integer isErrorStop) {
		this.isErrorStop = isErrorStop;
	}

	public Integer getIsCloseApp() {
		return isCloseApp;
	}

	public void setIsCloseApp(Integer isCloseApp) {
		this.isCloseApp = isCloseApp;
	}

	@JsonIgnore
	public Date getLastRunDate() {
		return lastRunDate;
	}

	public void setLastRunDate(Date lastRunDate) {
		this.lastRunDate = lastRunDate;
	}

	@JsonIgnore
	public Integer getIsBalance() {
		return isBalance;
	}

	public void setIsBalance(Integer isBalance) {
		this.isBalance = isBalance;
	}

	@JsonIgnore
	public Integer getBalanceUser() {
		return balanceUser;
	}

	public void setBalanceUser(Integer balanceUser) {
		this.balanceUser = balanceUser;
	}

	@JsonIgnore
	public Date getBalanceDate() {
		return balanceDate;
	}

	public void setBalanceDate(Date balanceDate) {
		this.balanceDate = balanceDate;
	}

	public Integer getIsAdminComment() {
		return isAdminComment;
	}

	public void setIsAdminComment(Integer isAdminComment) {
		this.isAdminComment = isAdminComment;
	}

	public Integer getIsCustomerComment() {
		return isCustomerComment;
	}

	public void setIsCustomerComment(Integer isCustomerComment) {
		this.isCustomerComment = isCustomerComment;
	}
}