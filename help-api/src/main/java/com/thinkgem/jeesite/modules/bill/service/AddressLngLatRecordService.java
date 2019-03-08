/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.bill.service;

import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.common.vo.Grid;
import com.thinkgem.jeesite.common.vo.GridParam;
import com.thinkgem.jeesite.modules.bill.dao.AddressLngLatRecordDao;
import com.thinkgem.jeesite.modules.bill.entity.AddressLngLatRecord;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * 地址历史记录Service
 * @author wcf
 * @version 2018-01-12
 */
@Service
@Transactional(readOnly = true)
public class AddressLngLatRecordService extends CrudService<AddressLngLatRecordDao, AddressLngLatRecord> {
	@Resource
	private AddressLngLatRecordDao dao;

	/**
	 * 通过地址查找
	 * @param address
	 * @return
	 */
	public AddressLngLatRecord getByAdress(String address){
		return dao.getByAdress(address);
	}
}