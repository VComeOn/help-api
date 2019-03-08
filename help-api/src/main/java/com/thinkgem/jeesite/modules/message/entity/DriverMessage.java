/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.message.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.modules.sys.entity.User;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * 司机消息记录Entity
 * @author wcf
 * @version 2018-01-18
 */
public class DriverMessage extends DataEntity<DriverMessage> {
	
	private static final long serialVersionUID = 1L;
	private Integer type;		// 消息类型，0：提醒消息，1：系统消息
	private Integer driverId;		// 司机id
	private String title;		// 消息标题
	private String content;		// 消息内容
	private Integer adminId;		// 后台发送人id
	private String adminBatch;		// 后台发送批次
	private Date beginCreateDate;		// 开始 发送时间
	private Date endCreateDate;		// 结束 发送时间
	private Integer isRead;			//是否已读

	public static final int TYPE_REMIND = 0;
	public static final int TYPE_SYS = 1;
	
	public DriverMessage() {
		super();
	}

	public DriverMessage(Integer id){
		super(id);
	}

	@NotNull(message="消息类型，0：提醒消息，1：系统消息不能为空")
	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}
	
	public Integer getDriverId() {
		return driverId;
	}

	public void setDriverId(Integer driverId) {
		this.driverId = driverId;
	}
	
	@Length(min=0, max=20, message="消息标题长度必须介于 0 和 20 之间")
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	@Length(min=0, max=200, message="消息内容长度必须介于 0 和 200 之间")
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@JsonIgnore
	public Integer getAdminId() {
		return adminId;
	}

	public void setAdminId(Integer adminId) {
		this.adminId = adminId;
	}
	
	@Length(min=0, max=50, message="后台发送批次长度必须介于 0 和 50 之间")
	@JsonIgnore
	public String getAdminBatch() {
		return adminBatch;
	}

	public void setAdminBatch(String adminBatch) {
		this.adminBatch = adminBatch;
	}
	
	public Date getBeginCreateDate() {
		return beginCreateDate;
	}

	public void setBeginCreateDate(Date beginCreateDate) {
		this.beginCreateDate = beginCreateDate;
	}
	
	public Date getEndCreateDate() {
		return endCreateDate;
	}

	public void setEndCreateDate(Date endCreateDate) {
		this.endCreateDate = endCreateDate;
	}

	public Integer getIsRead() {
		return isRead;
	}

	public void setIsRead(Integer isRead) {
		this.isRead = isRead;
	}
}