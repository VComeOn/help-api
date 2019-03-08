/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.base.service;

import javax.annotation.Resource;

import com.thinkgem.jeesite.modules.base.entity.BaseCar;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.common.vo.Grid;
import com.thinkgem.jeesite.common.vo.GridParam;
import com.thinkgem.jeesite.modules.base.entity.BaseCompany;
import com.thinkgem.jeesite.modules.base.dao.BaseCompanyDao;

/**
 * 公司信息Service
 * @author wcf
 * @version 2017-12-28
 */
@Service
@Transactional(readOnly = true)
public class BaseCompanyService extends CrudService<BaseCompanyDao, BaseCompany> {
	@Resource
	private BaseCompanyDao dao;

	/**
	 * 通过公司名查找公司
	 * @param name
	 * @return
	 */
	public BaseCompany getByName(String name){
		return dao.getByName(name);
	}

	/**
	 * 通过车辆公司机构代码查找公司
	 * @param baseCompanyCode
	 * @return
	 */
	public BaseCompany getByCode(String baseCompanyCode){
		return dao.getByCode(baseCompanyCode);
	}

	/**
	 * 通过车队公司组织机构代码查找公司
	 * @param organizationCode
	 * @return
	 */
	public BaseCompany getByOrganizationCode(String organizationCode){
		return dao.getByOrganizationCode(organizationCode);
	}

	/**
	 * @description 获取可以选择的运输公司
	 * @param
	 * @author wcf
	 * @date 2018/1/26
	 * @return
	 */
	public Grid getCompanyList(BaseCompany baseCompany, GridParam param){
		Grid grid = new Grid(param);
		baseCompany.setGrid(grid);
		grid.setRows(dao.findList(baseCompany));
		return grid;
	}
}