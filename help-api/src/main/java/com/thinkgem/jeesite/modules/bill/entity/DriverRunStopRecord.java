/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.bill.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.thinkgem.jeesite.common.persistence.DataEntity;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * 运输途中司机启停记录Entity
 * @author wcf
 * @version 2018-01-30
 */
public class DriverRunStopRecord extends DataEntity<DriverRunStopRecord> {
	
	private static final long serialVersionUID = 1L;
	private Integer deliveryBillId;		// 配货单id
	private Date runTime;		// 启动时间
	private Date stopTime;		// 停止时间
	private Integer driverId;		// 司机id
	private Integer companyId;		// 公司id
	private String ladingBillNo;		// 提货单编号
	
	public DriverRunStopRecord() {
		super();
	}

	public DriverRunStopRecord(Integer id){
		super(id);
	}

	@NotNull(message="配货单id不能为空")
	public Integer getDeliveryBillId() {
		return deliveryBillId;
	}

	public void setDeliveryBillId(Integer deliveryBillId) {
		this.deliveryBillId = deliveryBillId;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getRunTime() {
		return runTime;
	}

	public void setRunTime(Date runTime) {
		this.runTime = runTime;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getStopTime() {
		return stopTime;
	}

	public void setStopTime(Date stopTime) {
		this.stopTime = stopTime;
	}
	
	public Integer getDriverId() {
		return driverId;
	}

	public void setDriverId(Integer driverId) {
		this.driverId = driverId;
	}
	
	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	public String getLadingBillNo() {
		return ladingBillNo;
	}

	public void setLadingBillNo(String ladingBillNo) {
		this.ladingBillNo = ladingBillNo;
	}
}