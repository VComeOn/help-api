/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.driver.service;

import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.common.vo.Grid;
import com.thinkgem.jeesite.common.vo.GridParam;
import com.thinkgem.jeesite.modules.driver.dao.UserFeedbackDao;
import com.thinkgem.jeesite.modules.driver.entity.UserFeedback;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * 用户反馈Service
 * @author wcf
 * @version 2018-01-02
 */
@Service
@Transactional(readOnly = true)
public class UserFeedbackService extends CrudService<UserFeedbackDao, UserFeedback> {
	@Resource
	private UserFeedbackDao dao;
	
}