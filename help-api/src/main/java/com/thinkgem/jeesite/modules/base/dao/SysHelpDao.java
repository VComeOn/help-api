/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.base.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.base.entity.SysHelp;

/**
 * 帮助中心DAO接口
 * @author wcf
 * @version 2018-01-02
 */
@MyBatisDao
public interface SysHelpDao extends CrudDao<SysHelp> {
	
}