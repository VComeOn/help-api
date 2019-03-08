/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.openApi.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.thinkgem.jeesite.modules.annotation.Dict;
import com.thinkgem.jeesite.modules.annotation.Lat;
import com.thinkgem.jeesite.modules.annotation.Lng;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * 提单信息Entity
 * @author wcf
 * @version 2018-01-15
 */
public class BillLadingModel{

	@NotEmpty
	@Length(min=1, max=50, message="提单号长度必须介于 1 和 50 之间")
	private String tiDanHao;		// 提单号

	//@NotEmpty
	@Length(min=1, max=50, message="提单号长度必须介于 1 和 50 之间")
	private String ziTiDanHao;    //子提单号

	@NotEmpty
	@Length(min=1, max=50, message="运输委托单号长度必须介于 1 和 50 之间")
	private String ladingBillNo;		// 运输委托单号

	@NotEmpty
	@Length(min=1, max=50, message="提单密令长度必须介于 1 和 50 之间")
	private String billSecret;      //提单密令

	@Dict(type = "department")
	private String department;			//部门机构编码

	@NotEmpty
	@Length(min=0, max=50, message="所属公司名称长度必须介于 0 和 50 之间")
	private String organizationName;		// 所属公司名称

	@NotNull
	@Min(0)
	@Max(7)
	private Integer takeMode;		// 提货方式，0：代运，1：送货，2：自提，3：DAP，4：CFR，5：CIF，6：EXW，7：FOB

	@NotNull
	@JsonFormat(pattern = "yyyy-MM-dd")
	private String billDate;		// 日期

	@NotNull
	@Min(0)
	@Max(1)
	private Integer storehouseType;		// 仓库类型，0：内储库，1：外储库

	@NotEmpty
	@Length(min=0, max=50, message="运输公司机构代码长度必须介于 0 和 50 之间")
	private String companyCode;		// 运输公司机构代码

	//@NotEmpty
	@Length(min=0, max=50, message="客户名称长度必须介于 0 和 50 之间")
	private String customerName;		// 客户名称

	@Length(min=0, max = 20, message = "客户联系人长度必须介于 0 和 20 之间")
	private String customerContact;		//客户联系人

	@Length(min=0, max=50, message="客户联系电话长度必须介于 0 和 50 之间")
	private String customerPhone;		// 客户联系电话

	@Length(min=0, max=100, message="送货地址长度必须介于 0 和 100 之间")
	private String deliveryAddress;		// 送货地址

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private String latestLadingTime;		// 最迟到达装货点时间

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private String latestArriveTime;		// 最迟送到客户处时间

	@Length(min=0, max=500, message="备注长度必须介于 0 和 500 之间")
	private String remark;		// 备注

	@Length(min = 0, max = 50, message = "仓库名称长度必须介于0 和 50 之间")
	private String storageName;		//仓库名称

	@Length(min=0, max=200, message="储存地点长度必须介于 0 和 200 之间")
	private String storageAddress;		// 储存地点

	@NotEmpty
	@Length(min=0, max=100, message="物料名称长度必须介于 0 和 100 之间")
	private String materielName;		// 物料名称

	@NotNull
	@Digits(integer = 7, fraction = 3)
	@Min(0)
	private Double totalVolume;		// 运输委托单总量

	private String customerLossRate;		// 客户承担损耗率
	@NotNull
	@Digits(integer = 8, fraction = 2)
	@Min(0)
	private Double freightPrice;		// 委托运费单价

	@Lng
	@NotNull
	private Double deliveryLng;		// 发货地点经度
	@Lat
	@NotNull
	private Double deliveryLat;		// 发货地点纬度
	@Lng
	@NotNull
	private Double arriveLng;		// 送到地点经度
	@Lat
	@NotNull
	private Double arriveLat;		// 送到地点纬度


	public BillLadingModel() {
		super();
	}

	public String getLadingBillNo() {
		return ladingBillNo;
	}

	public void setLadingBillNo(String ladingBillNo) {
		this.ladingBillNo = ladingBillNo;
	}
	
	public String getOrganizationName() {
		return organizationName;
	}

	public void setOrganizationName(String organizationName) {
		this.organizationName = organizationName;
	}
	
	public Integer getTakeMode() {
		return takeMode;
	}

	public void setTakeMode(Integer takeMode) {
		this.takeMode = takeMode;
	}
	
	public String getBillDate() {
		return billDate;
	}

	public void setBillDate(String billDate) {
		this.billDate = billDate;
	}

	public String getCompanyCode() {
		return companyCode;
	}

	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	
	public String getCustomerPhone() {
		return customerPhone;
	}

	public void setCustomerPhone(String customerPhone) {
		this.customerPhone = customerPhone;
	}
	
	public String getDeliveryAddress() {
		return deliveryAddress;
	}

	public void setDeliveryAddress(String deliveryAddress) {
		this.deliveryAddress = deliveryAddress;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	public String getStorageAddress() {
		return storageAddress;
	}

	public void setStorageAddress(String storageAddress) {
		this.storageAddress = storageAddress;
	}
	
	public String getMaterielName() {
		return materielName;
	}

	public void setMaterielName(String materielName) {
		this.materielName = materielName;
	}
	
	public Double getTotalVolume() {
		return totalVolume;
	}

	public void setTotalVolume(Double totalVolume) {
		this.totalVolume = totalVolume;
	}
	
	public String getCustomerLossRate() {
		return customerLossRate;
	}

	public void setCustomerLossRate(String customerLossRate) {
		this.customerLossRate = customerLossRate;
	}
	
	public Double getFreightPrice() {
		return freightPrice;
	}

	public void setFreightPrice(Double freightPrice) {
		this.freightPrice = freightPrice;
	}

	public Integer getStorehouseType() {
		return storehouseType;
	}

	public void setStorehouseType(Integer storehouseType) {
		this.storehouseType = storehouseType;
	}

	public String getCustomerContact() {
		return customerContact;
	}

	public void setCustomerContact(String customerContact) {
		this.customerContact = customerContact;
	}

	public Double getDeliveryLng() {
		return deliveryLng;
	}

	public void setDeliveryLng(Double deliveryLng) {
		this.deliveryLng = deliveryLng;
	}

	public Double getDeliveryLat() {
		return deliveryLat;
	}

	public void setDeliveryLat(Double deliveryLat) {
		this.deliveryLat = deliveryLat;
	}

	public Double getArriveLng() {
		return arriveLng;
	}

	public void setArriveLng(Double arriveLng) {
		this.arriveLng = arriveLng;
	}

	public Double getArriveLat() {
		return arriveLat;
	}

	public void setArriveLat(Double arriveLat) {
		this.arriveLat = arriveLat;
	}

	public String getStorageName() {
		return storageName;
	}

	public void setStorageName(String storageName) {
		this.storageName = storageName;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getBillSecret() {
		return billSecret;
	}

	public void setBillSecret(String billSecret) {
		this.billSecret = billSecret;
	}

	public void setLatestLadingTime(String latestLadingTime) {
		this.latestLadingTime = latestLadingTime;
	}

	public void setLatestArriveTime(String latestArriveTime) {
		this.latestArriveTime = latestArriveTime;
	}

	public String getLatestLadingTime() {
		return latestLadingTime;
	}

	public String getLatestArriveTime() {
		return latestArriveTime;
	}

	public String getTiDanHao() {
		return tiDanHao;
	}

	public void setTiDanHao(String tiDanHao) {
		this.tiDanHao = tiDanHao;
	}

	public String getZiTiDanHao() {
		return ziTiDanHao;
	}

	public void setZiTiDanHao(String ziTiDanHao) {
		this.ziTiDanHao = ziTiDanHao;
	}
}