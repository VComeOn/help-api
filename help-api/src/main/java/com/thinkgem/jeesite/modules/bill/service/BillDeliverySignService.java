/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.bill.service;

import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.common.vo.Grid;
import com.thinkgem.jeesite.common.vo.GridParam;
import com.thinkgem.jeesite.modules.bill.dao.BillDeliverySignDao;
import com.thinkgem.jeesite.modules.bill.entity.BillDeliverySign;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * 运输任务签收记录Service
 * @author qj
 * @version 2018-10-06
 */
@Service
@Transactional(readOnly = true)
public class BillDeliverySignService extends CrudService<BillDeliverySignDao, BillDeliverySign> {
	@Resource
	private BillDeliverySignDao dao;
	
	public Grid list(BillDeliverySign billDeliverySign, GridParam param){
		Grid grid = new Grid(param);
		billDeliverySign.setGrid(grid);
		grid.setRows(dao.findList(billDeliverySign));
		return grid;
	}

	public List<BillDeliverySign> findListBybillDeliveryNoAndPlate(String deliveryBillNo, Integer driverId, String plateNumber){
        return dao.findListBybillDeliveryNoAndPlate(deliveryBillNo,driverId,plateNumber);
	}
}