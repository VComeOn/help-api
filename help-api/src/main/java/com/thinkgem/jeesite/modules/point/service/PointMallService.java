/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.point.service;

import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.common.vo.Grid;
import com.thinkgem.jeesite.common.vo.GridParam;
import com.thinkgem.jeesite.modules.point.dao.PointMallDao;
import com.thinkgem.jeesite.modules.point.entity.PointMall;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * 积分商城Service
 * @author wcf
 * @version 2018-01-07
 */
@Service
@Transactional(readOnly = true)
public class PointMallService extends CrudService<PointMallDao, PointMall> {
	@Resource
	private PointMallDao dao;
	
	public Grid list(PointMall pointMall, GridParam param){
		Grid grid = new Grid(param);
		pointMall.setGrid(grid);
		grid.setRows(dao.findList(pointMall));
		return grid;
	}
}