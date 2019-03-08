/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.bill.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.bill.entity.BillConstant;

/**
 * 运单系统常量DAO接口
 * @author wcf
 * @version 2018-01-12
 */
@MyBatisDao
public interface BillConstantDao extends CrudDao<BillConstant> {
	
}