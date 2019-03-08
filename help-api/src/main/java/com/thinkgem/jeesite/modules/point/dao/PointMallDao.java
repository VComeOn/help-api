/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.point.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.point.entity.PointMall;

/**
 * 积分商城DAO接口
 * @author wcf
 * @version 2018-01-07
 */
@MyBatisDao
public interface PointMallDao extends CrudDao<PointMall> {
	
}