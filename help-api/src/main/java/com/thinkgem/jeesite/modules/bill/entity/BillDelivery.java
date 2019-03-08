/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.bill.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.thinkgem.jeesite.common.persistence.DataEntity;
import org.hibernate.validator.constraints.Length;

import java.util.Date;

/**
 * 运单信息Entity
 * @author wcf
 * @version 2018-01-15
 */
public class BillDelivery extends DataEntity<BillDelivery> {
	
	private static final long serialVersionUID = 1L;
	private String deliveryBillNo;		// 运单号
	private String ladingBillNo;		// 提货单号
	//private String outboundBillNo;		//出库单号
	private Double signQuantity;		// 司机提货签收数量
	private Date signDate;		// 确认接单时间
	//private Double arriveSignQuantity;		// 司机送到签收数
	//private Date arriveSignDate;		// 司机送到签收时间
	private Integer status;		// 状态，0未审核，1已审核，2已派单，3已接单，4执行中，5已挂起，6已中止，7已到达 ，8已完成，9中途停车
	private Integer driverId;		// 司机id
	private String plateNumber;		// 车牌号
	private Integer companyId;		// 运输公司id
	//private String signPhoto;		// 提货签收单文件
	//private String arriveSignPhoto;		// 送到签收文件
	//private String arriveSignRemark;		// 送到签收备注
	//private Integer arriveSignType;		//送到签收类型，0：正常签收，1：代签，2：其他
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

	private BillLading billLading;		//提货单信息
	private BillDeliveryComment comment;	//运单评价信息

	private String driverPhone;      //司机手机号
	private String  companyName;   //运输公司名
	private String driverName;    //司机姓名

	private String estimatedArrivalTime; //预计到达时间

	private Integer signCount; //签收次数

	private Double totalSign; //已签收数

	private String idcardDriver;  //所属司机身份证号

	private String capacity;		// 荷载量

	private String licenceId;      //司机驾驶证号

	private String certificateId;      //从业资格证号

	private String signImageList;   //签收图片

	private String faultImageList;     //故障图片


	public static final int STATUS_UNALLOCAT = 0;		//未分配
	public static final int STATUS_ALLOCAT = 1;			//已分配
	public static final int STATUS_DISPATCH = 2;		//已派单
	public static final int STATUS_TAKING= 3;			//已接单
	public static final int STATUS_TRANSIT = 4;			//运输中
	public static final int STATUS_HOOK = 5;			//已挂起
	public static final int STATUS_SUSPEND = 6;			//已中止
	public static final int STATUS_ARRIVE = 7;			//已到达
	public static final int STATUS_FINISH = 8;			//已完成
	public static final int STATUS_STOP = 9;			//中途停车
	public static final int STATUS_RBUT = 10;			//驳回


	public BillDelivery() {
		super();
	}

	public BillDelivery(Integer id){
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
	
	public BillLading getBillLading() {
		return billLading;
	}

	public void setBillLading(BillLading billLading) {
		this.billLading = billLading;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getStartDeliveryDate() {
		return startDeliveryDate;
	}

	public void setStartDeliveryDate(Date startDeliveryDate) {
		this.startDeliveryDate = startDeliveryDate;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getEndDeliveryDate() {
		return endDeliveryDate;
	}

	public void setEndDeliveryDate(Date endDeliveryDate) {
		this.endDeliveryDate = endDeliveryDate;
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

	public BillDeliveryComment getComment() {
		return comment;
	}

	public void setComment(BillDeliveryComment comment) {
		this.comment = comment;
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

	public String getDriverPhone() {
		return driverPhone;
	}

	public void setDriverPhone(String driverPhone) {
		this.driverPhone = driverPhone;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getEstimatedArrivalTime() {
		return estimatedArrivalTime;
	}

	public void setEstimatedArrivalTime(String estimatedArrivalTime) {
		this.estimatedArrivalTime = estimatedArrivalTime;
	}

	public String getDriverName() {
		return driverName;
	}

	public void setDriverName(String driverName) {
		this.driverName = driverName;
	}

	public Double getSignQuantity() {
		return signQuantity;
	}

	public void setSignQuantity(Double signQuantity) {
		this.signQuantity = signQuantity;
	}

	public Date getSignDate() {
		return signDate;
	}

	public void setSignDate(Date signDate) {
		this.signDate = signDate;
	}

	public Integer getSignCount() {
		return signCount;
	}

	public void setSignCount(Integer signCount) {
		this.signCount = signCount;
	}

	public Double getTotalSign() {
		return totalSign;
	}

	public void setTotalSign(Double totalSign) {
		this.totalSign = totalSign;
	}

	public String getIdcardDriver() {
		return idcardDriver;
	}

	public void setIdcardDriver(String idcardDriver) {
		this.idcardDriver = idcardDriver;
	}

	public String getCapacity() {
		return capacity;
	}

	public void setCapacity(String capacity) {
		this.capacity = capacity;
	}

	public String getLicenceId() {
		return licenceId;
	}

	public void setLicenceId(String licenceId) {
		this.licenceId = licenceId;
	}

	public String getCertificateId() {
		return certificateId;
	}

	public void setCertificateId(String certificateId) {
		this.certificateId = certificateId;
	}

	public String getSignImageList() {
		return signImageList;
	}

	public void setSignImageList(String signImageList) {
		this.signImageList = signImageList;
	}

	public String getFaultImageList() {
		return faultImageList;
	}

	public void setFaultImageList(String faultImageList) {
		this.faultImageList = faultImageList;
	}
}