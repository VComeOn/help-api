/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.bill.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.thinkgem.jeesite.common.persistence.DataEntity;
import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 运输委托单信息Entity
 * @author wcf
 * @version 2018-01-15
 */
public class BillLading extends DataEntity<BillLading> {
	
	private static final long serialVersionUID = 1L;
	private String tiDanHao;		// 提单号
	private String ziTiDanHao;    //子提单号
	private String ladingBillNo;		// 运输委托单
	private String billSecret;      //提单密令
	private String department;			//部门
	private String organizationName;		// 所属公司
	private Integer status;		// 提单状态，0：未完成，1：已完成
	private Integer takeMode;		// 提货方式，0：代运，1：送货，2：自提
	private Date billDate;		// 日期
	private Integer storehouseType;		// 仓库类型，0：内储库，1：外储库
	private Integer companyId;		//运输公司id
	private String companyCode;		//所属运输车队公司机构代码
	private String companyName;		// 运输车队公司名称
	private String customerName;		// 客户名称
	private String customerContact;		//客户联系人
	private String customerPhone;		// 客户联系电话
	private String deliveryAddress;		// 送货地址
	private Date latestLadingTime;		// 最迟到达装货点时间
	private Date latestArriveTime;		// 最迟送到客户处时间
	private String remark;		// 备注
	private String storageName;			//仓库名称
	private String storageAddress;		// 储存地点
	private String materielName;		// 物料名称
	private Double totalVolume;		    // 委托单总量
	private String customerLossRate;		// 客户承担损耗率
	private Double freightPrice;		// 委托运费单价
	private BigDecimal deliveryLng;		// 发货地点经度
	private BigDecimal deliveryLat;		// 发货地点纬度
	private BigDecimal arriveLng;		// 送到地点经度
	private BigDecimal arriveLat;		// 送到地点纬度
	private Double startRailRadius;		// 起点电子围栏半径，单位：km
	private Double endRailRadius;		//终点电子围栏半径，单位：km
	private Date beginBillDate;		// 开始 日期
	private Date endBillDate;		// 结束 日期


	private BillDelivery delivery;

	public static final int STORE_TYPE_INSIDE = 0;	//内储库
	public static final int STORE_TYPE_OUTSIDE = 1;	//外储库

	public static final int STATUS_UNFINISH = 0;	//未完成
	public static final int STATUS_FINISH = 1;		//已完成
	
	public BillLading() {
		super();
	}

	public BillLading(Integer id){
		super(id);
	}

	@Length(min=1, max=50, message="提货单号长度必须介于 1 和 50 之间")
	public String getLadingBillNo() {
		return ladingBillNo;
	}

	public void setLadingBillNo(String ladingBillNo) {
		this.ladingBillNo = ladingBillNo;
	}
	
	@Length(min=0, max=50, message="组织名称长度必须介于 0 和 50 之间")
	public String getOrganizationName() {
		return organizationName;
	}

	public void setOrganizationName(String organizationName) {
		this.organizationName = organizationName;
	}
	
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	
	@Length(min=0, max=50, message="提货方式长度必须介于 0 和 50 之间")
	public Integer getTakeMode() {
		return takeMode;
	}

	public void setTakeMode(Integer takeMode) {
		this.takeMode = takeMode;
	}

	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getBillDate() {
		return billDate;
	}

	public void setBillDate(Date billDate) {
		this.billDate = billDate;
	}
	
	@Length(min=0, max=50, message="运输公司长度必须介于 0 和 50 之间")
	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	
	@Length(min=0, max=50, message="客户名称长度必须介于 0 和 50 之间")
	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	
	@Length(min=0, max=50, message="客户联系电话长度必须介于 0 和 50 之间")
	public String getCustomerPhone() {
		return customerPhone;
	}

	public void setCustomerPhone(String customerPhone) {
		this.customerPhone = customerPhone;
	}
	
	@Length(min=0, max=100, message="送货地址长度必须介于 0 和 100 之间")
	public String getDeliveryAddress() {
		return deliveryAddress;
	}

	public void setDeliveryAddress(String deliveryAddress) {
		this.deliveryAddress = deliveryAddress;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getLatestLadingTime() {
		return latestLadingTime;
	}

	public void setLatestLadingTime(Date latestLadingTime) {
		this.latestLadingTime = latestLadingTime;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getLatestArriveTime() {
		return latestArriveTime;
	}

	public void setLatestArriveTime(Date latestArriveTime) {
		this.latestArriveTime = latestArriveTime;
	}
	
	@Length(min=0, max=500, message="备注长度必须介于 0 和 500 之间")
	@JsonInclude(JsonInclude.Include.ALWAYS)
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	@Length(min=0, max=200, message="储存地点长度必须介于 0 和 200 之间")
	public String getStorageAddress() {
		return storageAddress;
	}

	public void setStorageAddress(String storageAddress) {
		this.storageAddress = storageAddress;
	}
	
	@Length(min=0, max=100, message="物料名称长度必须介于 0 和 100 之间")
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
	
	@Length(min=0, max=50, message="委托运费单价长度必须介于 0 和 50 之间")
	public Double getFreightPrice() {
		return freightPrice;
	}

	public void setFreightPrice(Double freightPrice) {
		this.freightPrice = freightPrice;
	}
	
	public BigDecimal getDeliveryLng() {
		return deliveryLng;
	}

	public void setDeliveryLng(BigDecimal deliveryLng) {
		this.deliveryLng = deliveryLng;
	}
	
	public BigDecimal getDeliveryLat() {
		return deliveryLat;
	}

	public void setDeliveryLat(BigDecimal deliveryLat) {
		this.deliveryLat = deliveryLat;
	}
	
	public BigDecimal getArriveLng() {
		return arriveLng;
	}

	public void setArriveLng(BigDecimal arriveLng) {
		this.arriveLng = arriveLng;
	}
	
	public BigDecimal getArriveLat() {
		return arriveLat;
	}

	public void setArriveLat(BigDecimal arriveLat) {
		this.arriveLat = arriveLat;
	}

	public Double getStartRailRadius() {
		return startRailRadius;
	}

	public void setStartRailRadius(Double startRailRadius) {
		this.startRailRadius = startRailRadius;
	}

	public Double getEndRailRadius() {
		return endRailRadius;
	}

	public void setEndRailRadius(Double endRailRadius) {
		this.endRailRadius = endRailRadius;
	}

	public Date getBeginBillDate() {
		return beginBillDate;
	}

	public void setBeginBillDate(Date beginBillDate) {
		this.beginBillDate = beginBillDate;
	}
	
	public Date getEndBillDate() {
		return endBillDate;
	}

	public void setEndBillDate(Date endBillDate) {
		this.endBillDate = endBillDate;
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	public Integer getStorehouseType() {
		return storehouseType;
	}

	public void setStorehouseType(Integer storehouseType) {
		this.storehouseType = storehouseType;
	}

	public BillDelivery getDelivery() {
		return delivery;
	}

	public void setDelivery(BillDelivery delivery) {
		this.delivery = delivery;
	}

	public String getCustomerContact() {
		return customerContact;
	}

	public void setCustomerContact(String customerContact) {
		this.customerContact = customerContact;
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

	public String getCompanyCode() {
		return companyCode;
	}

	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
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