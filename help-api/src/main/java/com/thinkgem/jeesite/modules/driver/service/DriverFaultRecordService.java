/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.driver.service;

import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.common.vo.Grid;
import com.thinkgem.jeesite.common.vo.GridParam;
import com.thinkgem.jeesite.modules.driver.dao.DriverFaultRecordDao;
import com.thinkgem.jeesite.modules.driver.entity.DriverFaultRecord;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * 司机故障记录Service
 * @author wcf
 * @version 2018-01-23
 */
@Service
@Transactional(readOnly = true)
public class DriverFaultRecordService extends CrudService<DriverFaultRecordDao, DriverFaultRecord> {
	@Resource
	private DriverFaultRecordDao dao;
	
	public Grid list(DriverFaultRecord driverFaultRecord, GridParam param){
		Grid grid = new Grid(param);
		driverFaultRecord.setGrid(grid);
		grid.setRows(dao.findList(driverFaultRecord));
		return grid;
	}
}