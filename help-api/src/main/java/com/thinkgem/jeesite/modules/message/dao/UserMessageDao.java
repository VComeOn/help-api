/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.message.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.message.entity.UserMessage;

import java.util.List;

/**
 * 管理员消息记录DAO接口
 * @author wcf
 * @version 2018-03-01
 */
@MyBatisDao
public interface UserMessageDao extends CrudDao<UserMessage> {
    /**
     * 批量插入
     * @param list
     * @return
     */
    public int insertList(List<UserMessage> list);
    /**
     * 设置已读
     * @param id
     * @return
     */
    public int setRead(Integer id);
}