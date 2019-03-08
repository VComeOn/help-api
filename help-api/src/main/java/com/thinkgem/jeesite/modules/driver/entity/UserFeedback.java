/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.driver.entity;

import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.modules.base.entity.BaseDriver;
import org.hibernate.validator.constraints.Length;

import java.util.Date;

/**
 * 用户反馈Entity
 * @author wcf
 * @version 2018-01-02
 */
public class UserFeedback extends DataEntity<UserFeedback> {
	
	private static final long serialVersionUID = 1L;
	private Integer driverId;		// 司机id
	private String content;		// 反馈内容
	private Date beginCreateDate;		// 开始 反馈时间
	private Date endCreateDate;		// 结束 反馈时间

	private BaseDriver driver;
	
	public UserFeedback() {
		super();
	}

	public UserFeedback(Integer id){
		super(id);
	}

	public Integer getDriverId() {
		return driverId;
	}

	public void setDriverId(Integer driverId) {
		this.driverId = driverId;
	}
	
	@Length(min=1, max=200, message="反馈内容长度必须介于 1 和 200 之间")
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
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

	public BaseDriver getDriver() {
		return driver;
	}

	public void setDriver(BaseDriver driver) {
		this.driver = driver;
	}
}