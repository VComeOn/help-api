/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.openApi.model;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;

/**
 * 公司信息Entity
 * @author wcf
 * @version 2017-12-28
 */
public class BaseCompanyModel {

	@NotEmpty
	@Length(min=1, max=50, message="车队公司名称长度必须介于 1 和 50 之间")
	private String name;		// 车队公司名称

	@NotEmpty
	@Length(min=1, max=50, message="车队公司机构代码长度必须介于 1 和 50 之间")
	private String baseCompanyCode; //车队公司机构代码

	@NotNull
	private Integer role;		// 公司角色0：合作车队，1内部车队，2临时车队，3其他

	@Length(min=0, max=200, message="公司地址长度必须介于 0 和 200 之间")
	private String address;		// 公司地址


	@Length(min=1, max=50, message="车队公司负责人长度必须介于 1 和 50 之间")
	private String leader;      //车队公司负责人


	@Length(min=1, max=50, message="公司负责人电话必须介于 1 和 50 之间")
	private String phone;		// 公司负责人电话

	@NotEmpty
	@Length(min=1, max=100, message="车队公司机构代码长度必须介于 1 和 100之间")
	private String organizationCode;  //组织机构代码


	@Length(min=1, max=50, message="公司名称长度必须介于 1 和 50 之间")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public Integer getRole() {
		return role;
	}

	public void setRole(Integer role) {
		this.role = role;
	}
	
	@Length(min=0, max=200, message="公司地址长度必须介于 0 和 200 之间")
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
	@Length(min=0, max=50, message="公司电话长度必须介于 0 和 50 之间")
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	@Length(min=1, max=50, message="车队公司机构代码长度必须介于 1 和 50 之间")
	public String getBaseCompanyCode() {
		return baseCompanyCode;
	}

	public void setBaseCompanyCode(String baseCompanyCode) {
		this.baseCompanyCode = baseCompanyCode;
	}

	@Length(min=1, max=50, message="车队公司负责人长度必须介于 1 和 50 之间")
	public String getLeader() {
		return leader;
	}

	public void setLeader(String leader) {
		this.leader = leader;
	}

	public String getOrganizationCode() {
		return organizationCode;
	}

	public void setOrganizationCode(String organizationCode) {
		this.organizationCode = organizationCode;
	}
}