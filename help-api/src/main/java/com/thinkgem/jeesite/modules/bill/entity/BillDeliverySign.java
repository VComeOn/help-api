/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.bill.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.thinkgem.jeesite.common.persistence.DataEntity;
import org.hibernate.validator.constraints.Length;

import java.util.Date;

/**
 * 运输任务签收记录Entity
 * @author qj
 * @version 2018-10-06
 */
public class BillDeliverySign extends DataEntity<BillDeliverySign> {
	
	private static final long serialVersionUID = 1L;
	private String deliveryBillNo;		// 运输任务号
	private String ladingBillNo;		// 提货单号
	private String arriveSignQuantity;		// 司机送到签收数
	private Date arriveSignDate;		// 司机送到签收时间
	private Integer driverId;		// 司机id
	private String plateNumber;		// 车牌号
	private String arriveSignPhoto;		// 送到签收文件
	private String arriveSignRemark;		// 送到签收备注
	private Integer arriveSignType;		// 送到签收类型，0：正常签收，1：代签，2：其他
	private Date startDeliveryDate;		// 开始运输时间
	private Date endDeliveryDate;		// 结束运输时间
	private String remark;		// 备注


	private String DriverPhone; //司机手机号

	private String driverName;    //司机姓名
	private String capacity;		// 荷载量

	public BillDeliverySign() {
		super();
	}

	public BillDeliverySign(Integer id){
		super(id);
	}

	@Length(min=0, max=255, message="运输任务号长度必须介于 0 和 255 之间")
	public String getDeliveryBillNo() {
		return deliveryBillNo;
	}

	public void setDeliveryBillNo(String deliveryBillNo) {
		this.deliveryBillNo = deliveryBillNo;
	}
	
	@Length(min=0, max=50, message="提货单号长度必须介于 0 和 50 之间")
	public String getLadingBillNo() {
		return ladingBillNo;
	}

	public void setLadingBillNo(String ladingBillNo) {
		this.ladingBillNo = ladingBillNo;
	}
	
	public String getArriveSignQuantity() {
		return arriveSignQuantity;
	}

	public void setArriveSignQuantity(String arriveSignQuantity) {
		this.arriveSignQuantity = arriveSignQuantity;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getArriveSignDate() {
		return arriveSignDate;
	}

	public void setArriveSignDate(Date arriveSignDate) {
		this.arriveSignDate = arriveSignDate;
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
	
	@Length(min=0, max=1000, message="送到签收文件长度必须介于 0 和 1000 之间")
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
	
	public Integer getArriveSignType() {
		return arriveSignType;
	}

	public void setArriveSignType(Integer arriveSignType) {
		this.arriveSignType = arriveSignType;
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
	
	@Length(min=0, max=200, message="备注长度必须介于 0 和 200 之间")
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getDriverPhone() {
		return DriverPhone;
	}

	public void setDriverPhone(String driverPhone) {
		DriverPhone = driverPhone;
	}

	public String getDriverName() {
		return driverName;
	}

	public void setDriverName(String driverName) {
		this.driverName = driverName;
	}

	public String getCapacity() {
		return capacity;
	}

	public void setCapacity(String capacity) {
		this.capacity = capacity;
	}
}