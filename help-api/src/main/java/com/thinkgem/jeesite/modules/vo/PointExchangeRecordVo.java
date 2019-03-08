/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.thinkgem.jeesite.common.persistence.DataEntity;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * 积分兑换记录Entity
 * @author wcf
 * @version 2018-01-07
 */
public class PointExchangeRecordVo extends DataEntity<PointExchangeRecordVo> {

	private static final long serialVersionUID = 1L;
	private Integer driverId;	//司机id
	private Integer mallId;		// 商品Id
	private Date applyDate;		// 申请兑换时间
	private String cardNo;			//充值卡号
	private String cardPwd;			//充值卡密码
	private String reason;			//驳回理由

	private PointMallVo vo;		//充值卡详情

	public PointExchangeRecordVo() {
		super();
	}

	public PointExchangeRecordVo(Integer id){
		super(id);
	}

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
	
	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public String getCardPwd() {
		return cardPwd;
	}

	public void setCardPwd(String cardPwd) {
		this.cardPwd = cardPwd;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public PointMallVo getVo() {
		return vo;
	}

	public void setVo(PointMallVo vo) {
		this.vo = vo;
	}
}