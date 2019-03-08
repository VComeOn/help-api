package com.thinkgem.jeesite.modules.base.dao;


import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.base.entity.AppVersionEntity;


/**
 * app版本
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2018-09-12 15:27:08
 */
@MyBatisDao
public interface AppVersionDao extends CrudDao<AppVersionEntity> {
	
}
