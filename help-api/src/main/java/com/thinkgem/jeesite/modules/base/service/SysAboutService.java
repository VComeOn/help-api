/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.base.service;

import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.common.vo.Grid;
import com.thinkgem.jeesite.common.vo.GridParam;
import com.thinkgem.jeesite.modules.base.dao.SysAboutDao;
import com.thinkgem.jeesite.modules.base.entity.SysAbout;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * 关于设置Service
 * @author wcf
 * @version 2018-01-03
 */
@Service
@Transactional(readOnly = true)
public class SysAboutService extends CrudService<SysAboutDao, SysAbout> {
	@Resource
	private SysAboutDao dao;
	
}