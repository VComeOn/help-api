/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.point.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.modules.base.entity.BaseCompany;
import com.thinkgem.jeesite.modules.base.entity.BaseDriver;
import com.thinkgem.jeesite.modules.sys.entity.User;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * 积分兑换记录Entity
 * @author wcf
 * @version 2018-01-07
 */
public class PointExchangeRecord extends DataEntity<PointExchangeRecord> {
	
	private static final long serialVersionUID = 1L;
	private Integer driverId;		// 司机id
	private Integer mallId;		// 商品Id
	private Date applyDate;		// 申请兑换时间
	private Integer status;		// 状态，0：已申请，1：已同意，2：已驳回
	private Date examineDate;		// 审核时间
	private Integer adminId;		// 审核人
	private String cardNo;			//充值卡号
	private String cardPwd;			//充值卡密码
	private String reason;			//驳回理由
	private String mallTitle;		//商品名称

	public static final int STATUS_APPLY = 0;
	public static final int STATUS_AGREE = 1;
	public static final int STATUS_REJECT = 2;

	public PointExchangeRecord() {
		super();
	}

	public PointExchangeRecord(Integer id){
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
	
	@NotNull(message="商品Id不能为空")
	public Integer getMallId() {
		return mallId;
	}

	public void setMallId(Integer mallId) {
		this.mallId = mallId;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@NotNull(message="申请兑换时间不能为空")
	public Date getApplyDate() {
		return applyDate;
	}

	public void setApplyDate(Date applyDate) {
		this.applyDate = applyDate;
	}
	
	@NotNull(message="状态，0：已申请，1：已同意，2：已驳回不能为空")
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonIgnore
	public Date getExamineDate() {
		return examineDate;
	}

	public void setExamineDate(Date examineDate) {
		this.examineDate = examineDate;
	}

	@JsonIgnore
	public Integer getAdminId() {
		return adminId;
	}

	public void setAdminId(Integer adminId) {
		this.adminId = adminId;
	}
	
	public String getMallTitle() {
		return mallTitle;
	}

	public void setMallTitle(String mallTitle) {
		this.mallTitle = mallTitle;
	}

	@JsonIgnore
	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	@JsonIgnore
	public String getCardPwd() {
		return cardPwd;
	}

	public void setCardPwd(String cardPwd) {
		this.cardPwd = cardPwd;
	}

	@JsonIgnore
	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}
}