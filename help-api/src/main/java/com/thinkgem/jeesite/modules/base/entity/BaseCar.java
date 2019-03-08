/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.base.entity;

import com.thinkgem.jeesite.common.persistence.DataEntity;
import org.hibernate.validator.constraints.Length;

/**
 * 车辆信息Entity
 * @author wcf
 * @version 2017-12-28
 */
public class BaseCar extends DataEntity<BaseCar> {
	
	private static final long serialVersionUID = 1L;
	private String plateNumber;		// 车牌号
	private String capacity;		// 荷载量
	private Integer companyId;		// 所属运输公司公司id
	private String companyName;     // 所属运输公司公司id
	private BaseCompany company;

	private Integer status;      //审核状态
	private String companyCode;  //所属运输公司机构代码

	public BaseCar() {
		super();
	}

	public BaseCar(Integer id){
		super(id);
	}

	@Length(min=1, max=20, message="车牌号长度必须介于 1 和 20 之间")
	public String getPlateNumber() {
		return plateNumber;
	}

	public void setPlateNumber(String plateNumber) {
		this.plateNumber = plateNumber;
	}
	
	@Length(min=0, max=50, message="罐体容量长度必须介于 0 和 50 之间")
	public String getCapacity() {
		return capacity;
	}

	public void setCapacity(String capacity) {
		this.capacity = capacity;
	}
	
	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	public BaseCompany getCompany() {
		return company;
	}

	public void setCompany(BaseCompany company) {
		this.company = company;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getCompanyCode() {
		return companyCode;
	}

	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}
}