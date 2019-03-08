/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.bill.service;

import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.common.vo.Grid;
import com.thinkgem.jeesite.common.vo.GridParam;
import com.thinkgem.jeesite.modules.bill.dao.BillConstantDao;
import com.thinkgem.jeesite.modules.bill.entity.BillConstant;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * 运单系统常量Service
 * @author wcf
 * @version 2018-01-12
 */
@Service
@Transactional(readOnly = true)
public class BillConstantService extends CrudService<BillConstantDao, BillConstant> {
	@Resource
	private BillConstantDao dao;

	public BillConstant get(){
		return dao.findAllList(new BillConstant()).get(0);
	}
}