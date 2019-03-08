/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.message.service;

import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.common.vo.Grid;
import com.thinkgem.jeesite.common.vo.GridParam;
import com.thinkgem.jeesite.modules.base.dao.BaseDriverDao;
import com.thinkgem.jeesite.modules.message.dao.DriverMessageDao;
import com.thinkgem.jeesite.modules.message.entity.DriverMessage;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * 司机消息记录Service
 * @author wcf
 * @version 2018-01-18
 */
@Service
@Transactional(readOnly = true)
public class DriverMessageService extends CrudService<DriverMessageDao, DriverMessage> {
	@Resource
	private DriverMessageDao dao;
	@Resource
	private BaseDriverDao driverDao;
	
	public Grid list(DriverMessage driverMessage, GridParam param){
		Grid grid = new Grid(param);
		driverMessage.setGrid(grid);
		grid.setRows(dao.findList(driverMessage));
		return grid;
	}

	/**
	 * 设置已读
	 * @param id
	 * @return
	 */
	@Transactional(readOnly = false)
	public int setRead(Integer id){
		return dao.setRead(id);
	}
}