/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.bill.entity;

import com.thinkgem.jeesite.common.persistence.DataEntity;
import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;

/**
 * 地址历史记录Entity
 * @author wcf
 * @version 2018-01-12
 */
public class AddressLngLatRecord extends DataEntity<AddressLngLatRecord> {
	
	private static final long serialVersionUID = 1L;
	private String address;		// 地址名称
	private BigDecimal lng;		// 经度
	private BigDecimal lat;		// 纬度
	
	public AddressLngLatRecord() {
		super();
	}

	public AddressLngLatRecord(Integer id){
		super(id);
	}

	@Length(min=1, max=200, message="地址名称长度必须介于 1 和 200 之间")
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
	public BigDecimal getLng() {
		return lng;
	}

	public void setLng(BigDecimal lng) {
		this.lng = lng;
	}
	
	public BigDecimal getLat() {
		return lat;
	}

	public void setLat(BigDecimal lat) {
		this.lat = lat;
	}
	
}