/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.bill.entity;


import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 运单系统常量Entity
 * @author wcf
 * @version 2018-01-12
 */
public class BillConstant extends DataEntity<BillConstant> {
	
	private static final long serialVersionUID = 1L;
	private Integer notRefreshTime;		// 最大位置未刷新时长，单位：分钟
	private String deliveryDistance;		// 距离送货地点距离（用于提醒），单位：km
	private Integer errorStopTime;		// 司机异常停靠时长，单位：分钟
	private String errorUpper;		// 提单分配误差上限
	private String errorLower;		// 提单分配误差下限
	private String railRadius;		// 默认电子围栏半径，单位：km
	private Integer fatigueDrive;	//司机疲劳驾驶时长，单位：分钟
	
	public BillConstant() {
		super();
	}

	public BillConstant(Integer id){
		super(id);
	}

	public Integer getNotRefreshTime() {
		return notRefreshTime;
	}

	public void setNotRefreshTime(Integer notRefreshTime) {
		this.notRefreshTime = notRefreshTime;
	}
	
	public String getDeliveryDistance() {
		return deliveryDistance;
	}

	public void setDeliveryDistance(String deliveryDistance) {
		this.deliveryDistance = deliveryDistance;
	}
	
	public Integer getErrorStopTime() {
		return errorStopTime;
	}

	public void setErrorStopTime(Integer errorStopTime) {
		this.errorStopTime = errorStopTime;
	}
	
	public String getErrorUpper() {
		return errorUpper;
	}

	public void setErrorUpper(String errorUpper) {
		this.errorUpper = errorUpper;
	}
	
	public String getErrorLower() {
		return errorLower;
	}

	public void setErrorLower(String errorLower) {
		this.errorLower = errorLower;
	}
	
	public String getRailRadius() {
		return railRadius;
	}

	public void setRailRadius(String railRadius) {
		this.railRadius = railRadius;
	}

	public Integer getFatigueDrive() {
		return fatigueDrive;
	}

	public void setFatigueDrive(Integer fatigueDrive) {
		this.fatigueDrive = fatigueDrive;
	}
}