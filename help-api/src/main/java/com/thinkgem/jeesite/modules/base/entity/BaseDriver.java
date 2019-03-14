/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.base.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.thinkgem.jeesite.common.persistence.DataEntity;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * 救援车辆信息Entity
 * @author qj
 * @version 2019-03-13
 */
public class BaseDriver extends DataEntity<BaseDriver> {
	
	private static final long serialVersionUID = 1L;
	private String headImg;		// 头像
	private String pwd;		// 密码
	private Integer isOnDuty;		// 是否在执勤
	private String plateNumber;		// 绑定车牌号

	public BaseDriver() {
		super();
	}

	public BaseDriver(Integer id){
		super(id);
	}

	@Length(min=0, max=200, message="头像长度必须介于 0 和 200 之间")
	@JsonInclude(JsonInclude.Include.ALWAYS)
	public String getHeadImg() {
		return headImg;
	}

	public void setHeadImg(String headImg) {
		this.headImg = headImg;
	}
	
	@Length(min=0, max=200, message="密码长度必须介于 0 和 200 之间")
	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	@Length(min=0, max=20, message="绑定车牌号长度必须介于 0 和 20 之间")
	@JsonInclude(JsonInclude.Include.ALWAYS)
	public String getPlateNumber() {
		return plateNumber;
	}

	public void setPlateNumber(String plateNumber) {
		this.plateNumber = plateNumber;
	}

	public Integer getIsOnDuty() {
		return isOnDuty;
	}

	public void setIsOnDuty(Integer isOnDuty) {
		this.isOnDuty = isOnDuty;
	}
}