/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.message.service;

import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.common.vo.Grid;
import com.thinkgem.jeesite.common.vo.GridParam;
import com.thinkgem.jeesite.modules.message.dao.UserMessageDao;
import com.thinkgem.jeesite.modules.message.entity.UserMessage;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * 管理员消息记录Service
 * @author wcf
 * @version 2018-03-01
 */
@Service
@Transactional(readOnly = true)
public class UserMessageService extends CrudService<UserMessageDao, UserMessage> {
	@Resource
	private UserMessageDao dao;
	
	public Grid list(UserMessage userMessage, GridParam param){
		Grid grid = new Grid(param);
		userMessage.setGrid(grid);
		grid.setRows(dao.findList(userMessage));
		return grid;
	}

	/**
	 * 批量插入
	 * @param list
	 * @return
	 */
	public int insertList(List<UserMessage> list){
		return dao.insertList(list);
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