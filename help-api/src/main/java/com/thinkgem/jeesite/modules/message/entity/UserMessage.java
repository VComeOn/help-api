/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.message.entity;

import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.modules.sys.entity.User;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

/**
 * 管理员消息记录Entity
 * @author wcf
 * @version 2018-03-01
 */
public class UserMessage extends DataEntity<UserMessage> {
	
	private static final long serialVersionUID = 1L;
	private Integer userId;		// 管理员id
	private String content;		// 消息内容
	private String deliveryBillNo;		// 运单编号
	private Integer driverId;		//司机id
	private Integer isRead;		// 是否已读

	public UserMessage() {
		super();
	}

	public UserMessage(Integer id){
		super(id);
	}

	@NotNull
	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	@Length(min=0, max=200, message="消息内容长度必须介于 0 和 200 之间")
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	@Length(min=0, max=50, message="运单编号长度必须介于 0 和 50 之间")
	public String getDeliveryBillNo() {
		return deliveryBillNo;
	}

	public void setDeliveryBillNo(String deliveryBillNo) {
		this.deliveryBillNo = deliveryBillNo;
	}
	
	public Integer getIsRead() {
		return isRead;
	}

	public void setIsRead(Integer isRead) {
		this.isRead = isRead;
	}

	public Integer getDriverId() {
		return driverId;
	}

	public void setDriverId(Integer driverId) {
		this.driverId = driverId;
	}
}