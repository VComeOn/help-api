/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.message.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.message.entity.DriverMessage;

import java.util.List;

/**
 * 司机消息记录DAO接口
 * @author wcf
 * @version 2018-01-18
 */
@MyBatisDao
public interface DriverMessageDao extends CrudDao<DriverMessage> {

    /**
     * 批量新增消息
     * @param list
     * @return
     */
    public int insertList(List<DriverMessage> list);

    /**
     * 设置已读
     * @param id
     * @return
     */
    public int setRead(Integer id);
}