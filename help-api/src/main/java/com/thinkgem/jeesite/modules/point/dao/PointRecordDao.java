/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.point.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.point.entity.PointRecord;

/**
 * 积分记录DAO接口
 * @author wcf
 * @version 2018-01-04
 */
@MyBatisDao
public interface PointRecordDao extends CrudDao<PointRecord> {
	
}