/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.base.entity;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 公司信息Entity
 * @author wcf
 * @version 2017-12-28
 */
public class BaseCompany extends DataEntity<BaseCompany> {
	
	private static final long serialVersionUID = 1L;
	private String name;		// 车队公司名称
	private String baseCompanyCode; //车队公司主数据代码
	private Integer role;		// 车队公司角色
	private String address;		// 车队公司地址
	private String leader;      //车队公司负责人
	private String phone;		// 车队公司负责人电话
	private String organizationCode;  //组织机构代码
	
	public BaseCompany() {
		super();
	}

	public BaseCompany(Integer id){
		super(id);
	}

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

	@Length(min=1, max=50, message="运输车队代码长度必须介于 1 和 50 之间")
	public String getBaseCompanyCode() {
		return baseCompanyCode;
	}

	public void setBaseCompanyCode(String baseCompanyCode) {
		this.baseCompanyCode = baseCompanyCode;
	}

	@Length(min=1, max=50, message="公司负责人长度必须介于 1 和 50 之间")
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