/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.point.service;

import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.common.vo.Grid;
import com.thinkgem.jeesite.common.vo.GridParam;
import com.thinkgem.jeesite.modules.base.dao.BaseDriverDao;
import com.thinkgem.jeesite.modules.base.entity.BaseDriver;
import com.thinkgem.jeesite.modules.point.dao.PointRecordDao;
import com.thinkgem.jeesite.modules.point.entity.PointRecord;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * 积分记录Service
 * @author wcf
 * @version 2018-01-04
 */
@Service
@Transactional(readOnly = true)
public class PointRecordService extends CrudService<PointRecordDao, PointRecord> {
	@Resource
	private PointRecordDao dao;

	public Grid list(PointRecord pointRecord, GridParam param){
		Grid grid = new Grid(param);
		pointRecord.setGrid(grid);
		grid.setRows(dao.findList(pointRecord));
		return grid;
	}
}