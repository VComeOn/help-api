/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.bill.service;

import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.common.vo.Grid;
import com.thinkgem.jeesite.common.vo.GridParam;
import com.thinkgem.jeesite.modules.bill.dao.AddressLngLatRecordDao;
import com.thinkgem.jeesite.modules.bill.dao.BillLadingDao;
import com.thinkgem.jeesite.modules.bill.entity.AddressLngLatRecord;
import com.thinkgem.jeesite.modules.bill.entity.BillLading;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;

/**
 * 提单信息Service
 * @author wcf
 * @version 2018-01-15
 */
@Service
@Transactional(readOnly = true)
public class BillLadingService extends CrudService<BillLadingDao, BillLading> {
	@Resource
	private BillLadingDao dao;

	/**
	 * 批量插入提单信息
	 * @param list
	 * @return
	 */
	@Transactional(readOnly = false)
	public int insertList(List<BillLading> list){
		return dao.insertList(list);
	}

	//  ******************************************** 二期接口 ***************************************************


	/**
	 * 通过提单密令获取提单信息
	 * @param billSecret
	 * @return
	 */
	public BillLading getByBillSecret(String billSecret){
		return dao.getByBillSecret(billSecret);
	}

	//  ******************************************** 管理端二期接口 ***************************************************

	/**
	 * 获取运输委托单列表
	 * @param billLading
	 * @param param
	 * @return
	 */
	public Grid list(BillLading billLading, GridParam param){
		Grid  grid= new Grid(param);
		billLading.setGrid(grid);
		grid.setRows(dao.findList(billLading));
		return grid;
	}

	/**
	 * 通过提单编号获取提单信息
	 * @param ladingNo
	 * @return
	 */
	public BillLading getByLadingNo(String ladingNo){
		return dao.getByLadingNo(ladingNo);
	}
}