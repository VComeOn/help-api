/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.point.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.thinkgem.jeesite.common.persistence.DataEntity;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

/**
 * 积分记录Entity
 * @author wcf
 * @version 2018-01-04
 */
public class PointRecord extends DataEntity<PointRecord> {
	
	private static final long serialVersionUID = 1L;
	private Integer driverId;		// 司机id
	private Integer companyId;		// 公司id
	private Integer pointBefore;		// 变化前数量
	private Integer pointAfter;		// 变化后数量
	private Integer pointChange;		// 变化数量
	private Integer operationType;		// 操作方式，0：添加，1：减少
	private String remark;		// 积分说明
	private Integer orderId;		// 订单id
	private Integer exchangeId;		// 兑换id

	public static final int OPERATE_TYPE_PLUS = 0;
	public static final int OPERATE_TYPE_MINUS = 1;
	
	public PointRecord() {
		super();
	}

	public PointRecord(Integer id){
		super(id);
	}

	@NotNull(message="司机id不能为空")
	@JsonIgnore
	public Integer getDriverId() {
		return driverId;
	}

	public void setDriverId(Integer driverId) {
		this.driverId = driverId;
	}
	
	@NotNull(message="公司id不能为空")
	@JsonIgnore
	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	@JsonIgnore
	public Integer getPointBefore() {
		return pointBefore;
	}

	public void setPointBefore(Integer pointBefore) {
		this.pointBefore = pointBefore;
	}

	@JsonIgnore
	public Integer getPointAfter() {
		return pointAfter;
	}

	public void setPointAfter(Integer pointAfter) {
		this.pointAfter = pointAfter;
	}
	
	public Integer getPointChange() {
		return pointChange;
	}

	public void setPointChange(Integer pointChange) {
		this.pointChange = pointChange;
	}
	
	public Integer getOperationType() {
		return operationType;
	}

	public void setOperationType(Integer operationType) {
		this.operationType = operationType;
	}
	
	@Length(min=0, max=50, message="积分说明长度必须介于 0 和 50 之间")
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}
	
	public Integer getExchangeId() {
		return exchangeId;
	}

	public void setExchangeId(Integer exchangeId) {
		this.exchangeId = exchangeId;
	}
	
}