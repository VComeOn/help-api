/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.point.service;

import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.common.vo.Grid;
import com.thinkgem.jeesite.common.vo.GridParam;
import com.thinkgem.jeesite.modules.point.dao.PointSetDao;
import com.thinkgem.jeesite.modules.point.entity.PointSet;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * 积分设置Service
 * @author wcf
 * @version 2018-01-07
 */
@Service
@Transactional(readOnly = true)
public class PointSetService extends CrudService<PointSetDao, PointSet> {
	@Resource
	private PointSetDao dao;

	public PointSet get(){
		return dao.findAllList(new PointSet()).get(0);
	}
}