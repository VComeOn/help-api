/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.bill.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.thinkgem.jeesite.common.persistence.DataEntity;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * 运单评价Entity
 * @author wcf
 * @version 2018-02-06
 */
public class BillDeliveryComment extends DataEntity<BillDeliveryComment> {
	
	private static final long serialVersionUID = 1L;
	private Integer deliveryId;		// 运单id
	private Integer satisfactionScore;		// 满意度评价-打分
	private Integer safeScore;		// 安全评价-打分
	private Integer customerScore;		// 客户评价-打分
	private String satisfactionComment;		// 满意度评价-内容
	private String safeComment;		// 安全度评价-内容
	private String customerComment;		// 客户评价-内容
	private Date satisfactionTime;		// 满意度评价时间
	private Date safeTime;		// 安全评价时间
	private Date customerTime;		// 客户评价时间
	
	public BillDeliveryComment() {
		super();
	}

	public BillDeliveryComment(Integer id){
		super(id);
	}

	@NotNull(message="运单id不能为空")
	public Integer getDeliveryId() {
		return deliveryId;
	}

	public void setDeliveryId(Integer deliveryId) {
		this.deliveryId = deliveryId;
	}

	@JsonInclude(JsonInclude.Include.ALWAYS)
	public Integer getSatisfactionScore() {
		return satisfactionScore;
	}

	public void setSatisfactionScore(Integer satisfactionScore) {
		this.satisfactionScore = satisfactionScore;
	}

	@JsonInclude(JsonInclude.Include.ALWAYS)
	public Integer getSafeScore() {
		return safeScore;
	}

	public void setSafeScore(Integer safeScore) {
		this.safeScore = safeScore;
	}

	@JsonInclude(JsonInclude.Include.ALWAYS)
	public Integer getCustomerScore() {
		return customerScore;
	}

	public void setCustomerScore(Integer customerScore) {
		this.customerScore = customerScore;
	}
	
	@Length(min=0, max=200, message="满意度评价-内容长度必须介于 0 和 200 之间")
	@JsonInclude(JsonInclude.Include.ALWAYS)
	public String getSatisfactionComment() {
		return satisfactionComment;
	}

	public void setSatisfactionComment(String satisfactionComment) {
		this.satisfactionComment = satisfactionComment;
	}
	
	@Length(min=0, max=200, message="安全度评价-内容长度必须介于 0 和 200 之间")
	@JsonInclude(JsonInclude.Include.ALWAYS)
	public String getSafeComment() {
		return safeComment;
	}

	public void setSafeComment(String safeComment) {
		this.safeComment = safeComment;
	}
	
	@Length(min=0, max=200, message="客户评价-内容长度必须介于 0 和 200 之间")
	@JsonInclude(JsonInclude.Include.ALWAYS)
	public String getCustomerComment() {
		return customerComment;
	}

	public void setCustomerComment(String customerComment) {
		this.customerComment = customerComment;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonInclude(JsonInclude.Include.ALWAYS)
	public Date getSatisfactionTime() {
		return satisfactionTime;
	}

	public void setSatisfactionTime(Date satisfactionTime) {
		this.satisfactionTime = satisfactionTime;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonInclude(JsonInclude.Include.ALWAYS)
	public Date getSafeTime() {
		return safeTime;
	}

	public void setSafeTime(Date safeTime) {
		this.safeTime = safeTime;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonInclude(JsonInclude.Include.ALWAYS)
	public Date getCustomerTime() {
		return customerTime;
	}

	public void setCustomerTime(Date customerTime) {
		this.customerTime = customerTime;
	}
	
}