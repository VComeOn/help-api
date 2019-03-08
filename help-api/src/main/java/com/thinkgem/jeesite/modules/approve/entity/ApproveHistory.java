/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.approve.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.thinkgem.jeesite.common.persistence.DataEntity;
import org.hibernate.validator.constraints.Length;

import java.util.Date;

/**
 * 审批历史Entity
 * @author qj
 * @version 2018-09-21
 */
public class ApproveHistory extends DataEntity<ApproveHistory> {
	
	private static final long serialVersionUID = 1L;
	private Integer sysUserId;		// 管理人员id
	private Integer type;		// 审批类型 0：司机认证 1：车辆认证 2：任务认证
	private Integer status;		// 审核状态 0：未认证 1：已认证2：驳回
	private String remark;		// 备注：司机认证填入申请人姓名，车辆申请填入车牌号，任务审核填入运输任务单号
	private Date createTime;		// 创建时间
	
	public ApproveHistory() {
		super();
	}

	public ApproveHistory(Integer id){
		super(id);
	}

	public Integer getSysUserId() {
		return sysUserId;
	}

	public void setSysUserId(Integer sysUserId) {
		this.sysUserId = sysUserId;
	}
	
	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}
	
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	
	@Length(min=0, max=255, message="备注：司机认证填入申请人姓名，车辆申请填入车牌号，任务审核填入运输任务单号长度必须介于 0 和 255 之间")
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
}