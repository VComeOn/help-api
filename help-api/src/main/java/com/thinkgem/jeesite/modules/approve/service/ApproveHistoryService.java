/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.approve.service;

import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.common.vo.Grid;
import com.thinkgem.jeesite.common.vo.GridParam;
import com.thinkgem.jeesite.modules.approve.dao.ApproveHistoryDao;
import com.thinkgem.jeesite.modules.approve.entity.ApproveHistory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * 审批历史Service
 * @author qj
 * @version 2018-09-21
 */
@Service
@Transactional(readOnly = true)
public class ApproveHistoryService extends CrudService<ApproveHistoryDao, ApproveHistory> {
	@Resource
	private ApproveHistoryDao dao;
	
	public Grid list(ApproveHistory approveHistory, GridParam param){
		Grid grid = new Grid(param);
		grid.setOrder("desc");
		grid.setSort("a.create_time");
		approveHistory.setGrid(grid);
		grid.setRows(dao.findList(approveHistory));
		return grid;
	}
}