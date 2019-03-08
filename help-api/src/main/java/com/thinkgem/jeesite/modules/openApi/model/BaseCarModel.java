/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.openApi.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.modules.base.entity.BaseCompany;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import java.util.Date;

/**
 * 车辆信息Entity
 * @author wcf
 * @version 2017-12-28
 */
public class BaseCarModel extends DataEntity<BaseCarModel> {


	private static final long serialVersionUID = 1L;
	@NotEmpty
	@Length(min=1, max=20, message="车牌号长度必须介于 1 和 20 之间")
	private String plateNumber;		// 车牌号
	@NotEmpty
	@Length(min=0, max=50, message="荷载量长度必须介于 0 和 50 之间")
	private String capacity;		// 荷载量
	private String baseCompanyCode;		//所属运输车队公司机构代码

	public BaseCarModel() {
		super();
	}

	public BaseCarModel(Integer id){
		super(id);
	}


	public String getPlateNumber() {
		return plateNumber;
	}

	public void setPlateNumber(String plateNumber) {
		this.plateNumber = plateNumber;
	}

	public String getCapacity() {
		return capacity;
	}

	public void setCapacity(String capacity) {
		this.capacity = capacity;
	}

	public String getBaseCompanyCode() {
		return baseCompanyCode;
	}

	public void setBaseCompanyCode(String baseCompanyCode) {
		this.baseCompanyCode = baseCompanyCode;
	}


}