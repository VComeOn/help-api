/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.approve.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.approve.entity.ApproveHistory;

/**
 * 审批历史DAO接口
 * @author qj
 * @version 2018-09-21
 */
@MyBatisDao
public interface ApproveHistoryDao extends CrudDao<ApproveHistory> {
	
}