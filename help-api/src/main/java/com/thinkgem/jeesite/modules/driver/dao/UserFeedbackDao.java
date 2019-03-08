/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.driver.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.driver.entity.UserFeedback;

/**
 * 用户反馈DAO接口
 * @author wcf
 * @version 2018-01-02
 */
@MyBatisDao
public interface UserFeedbackDao extends CrudDao<UserFeedback> {
	
}