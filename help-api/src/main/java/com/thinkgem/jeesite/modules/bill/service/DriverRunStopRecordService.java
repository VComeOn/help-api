/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.bill.service;

import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.common.vo.Grid;
import com.thinkgem.jeesite.common.vo.GridParam;
import com.thinkgem.jeesite.modules.bill.dao.DriverRunStopRecordDao;
import com.thinkgem.jeesite.modules.bill.entity.DriverRunStopRecord;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * 运输途中司机启停记录Service
 * @author wcf
 * @version 2018-01-30
 */
@Service
@Transactional(readOnly = true)
public class DriverRunStopRecordService extends CrudService<DriverRunStopRecordDao, DriverRunStopRecord> {
	@Resource
	private DriverRunStopRecordDao dao;
	

}