/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.base.service;

import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.base.dao.AppVersionDao;
import com.thinkgem.jeesite.modules.base.entity.AppVersionEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *App版本更新Service
 * @author wcf
 * @version 2018-01-02
 */
@Service
@Transactional(readOnly = true)
public class AppVersionService extends CrudService<AppVersionDao, AppVersionEntity> {

}