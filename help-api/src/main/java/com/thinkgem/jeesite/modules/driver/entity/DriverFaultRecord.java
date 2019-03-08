/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.driver.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.thinkgem.jeesite.common.persistence.DataEntity;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;

/**
 * 司机故障记录Entity
 * @author wcf
 * @version 2018-01-23
 */
public class DriverFaultRecord extends DataEntity<DriverFaultRecord> {
	
	private static final long serialVersionUID = 1L;
	private Integer type;		// 故障类型，0：车辆故障，1：违章处罚，2：交通拥堵
	private String content;		// 故障说明
	private String imgUrls;		// 照片记录，多个用,分开
	private Integer driverId;		// 司机id
	private String deliveryBillNo;		// 出货单编号
	private String ladingBillNo;		// 提货单编号
	private Integer companyId;		// 公司id
	private String address;		// 故障地点
	
	public DriverFaultRecord() {
		super();
	}

	public DriverFaultRecord(Integer id){
		super(id);
	}

	@NotNull(message="故障类型，0：车辆故障，1：违章处罚，2：交通拥堵不能为空")
	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}
	
	@Length(min=0, max=500, message="故障说明长度必须介于 0 和 500 之间")
	@JsonInclude(JsonInclude.Include.ALWAYS)
	@NotEmpty
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	@Length(min=0, max=1000, message="照片记录，多个用,分开长度必须介于 0 和 1000 之间")
	public String getImgUrls() {
		return imgUrls;
	}

	public void setImgUrls(String imgUrls) {
		this.imgUrls = imgUrls;
	}
	
	public Integer getDriverId() {
		return driverId;
	}

	public void setDriverId(Integer driverId) {
		this.driverId = driverId;
	}

	@NotEmpty
	public String getdeliveryBillNo() {
		return deliveryBillNo;
	}

	public void setdeliveryBillNo(String deliveryBillNo) {
		this.deliveryBillNo = deliveryBillNo;
	}

	public String getladingBillNo() {
		return ladingBillNo;
	}

	public void setladingBillNo(String ladingBillNo) {
		this.ladingBillNo = ladingBillNo;
	}
	
	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}
	
	@Length(min=0, max=500, message="故障地点长度必须介于 0 和 500 之间")
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
}