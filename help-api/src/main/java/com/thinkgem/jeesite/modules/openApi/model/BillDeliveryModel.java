/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.openApi.model;

import com.thinkgem.jeesite.common.persistence.DataEntity;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * 运单信息Entity
 * @author wcf
 * @version 2018-01-15
 */
public class BillDeliveryModel extends DataEntity<BillDeliveryModel> {

	private static final long serialVersionUID = 1L;

	@NotEmpty
	@Length(min=1, max=50, message="运单号长度必须介于 1 和 50 之间")
	private String deliveryBillNo;		// 运单号

	@NotEmpty
	@Length(min=1, max=50, message="提货单号长度必须介于 1 和 50 之间")
	private String ladingBillNo;		// 提货单号

	@NotEmpty
	@Length(min=1, max=11, message="司机手机号长度不得超过11")
	private String driverPhone;      //司机手机号

	@NotEmpty
	@Length(min=1, max=20, message="司机姓名长度不得超过20")
	private String driverName;      //司机姓名

	@NotEmpty
	@Length(min=1, max=100, message="司机身份证号长度不得超过100")
	private String idcardDriver;      //司机身份证号

	@Length(min=0,max=100, message="司机驾驶证号长度不得超过100")
	private String licenceId;      //司机驾驶证号

	@Length(min=0,max=100, message="从业资格证号长度不得超过100")
	private String certificateId;      //从业资格证号

	@NotEmpty
	@Length(min=0, max=50, message="荷载量长度不得超过50")
	private String capacity;		// 荷载量

	@Length(min=1, max=50, message="车牌号长度必须介于 1 和 20 之间")
	private String plateNumber;		// 车牌号

	@NotEmpty
	@Length(min=1, max=50, message="运输车队公司机构代码必须介于 1 和 50 之间")
	private String  companyCode;   //运输车队公司机构代码

	private Double willQuantity;		//应发数

	@NotEmpty
	@Length(min=1, max=50, message="货主名称长度必须介于 1 和 50 之间")
	private String shipperName;			//货主名称


	public BillDeliveryModel() {
		super();
	}

	public BillDeliveryModel(Integer id){
		super(id);
	}

	public String getDeliveryBillNo() {
		return deliveryBillNo;
	}

	public void setDeliveryBillNo(String deliveryBillNo) {
		this.deliveryBillNo = deliveryBillNo;
	}

	public String getLadingBillNo() {
		return ladingBillNo;
	}

	public void setLadingBillNo(String ladingBillNo) {
		this.ladingBillNo = ladingBillNo;
	}

	public String getPlateNumber() {
		return plateNumber;
	}

	public void setPlateNumber(String plateNumber) {
		this.plateNumber = plateNumber;
	}

	public Double getWillQuantity() {
		return willQuantity;
	}

	public void setWillQuantity(Double willQuantity) {
		this.willQuantity = willQuantity;
	}

	public String getShipperName() {
		return shipperName;
	}

	public void setShipperName(String shipperName) {
		this.shipperName = shipperName;
	}



	public String getDriverPhone() {
		return driverPhone;
	}

	public void setDriverPhone(String driverPhone) {
		this.driverPhone = driverPhone;
	}

	public String getCompanyCode() {
		return companyCode;
	}

	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	public String getDriverName() {
		return driverName;
	}

	public void setDriverName(String driverName) {
		this.driverName = driverName;
	}

	public String getIdcardDriver() {
		return idcardDriver;
	}

	public void setIdcardDriver(String idcardDriver) {
		this.idcardDriver = idcardDriver;
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

	public String getCapacity() {
		return capacity;
	}

	public void setCapacity(String capacity) {
		this.capacity = capacity;
	}
}