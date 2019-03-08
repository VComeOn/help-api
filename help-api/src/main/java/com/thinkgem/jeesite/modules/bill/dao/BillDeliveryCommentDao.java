/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.bill.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.bill.entity.BillDeliveryComment;
import com.thinkgem.jeesite.modules.thirdApi.customer.demo.Interface4app;

/**
 * 运单评价DAO接口
 * @author wcf
 * @version 2018-02-06
 */
@MyBatisDao
public interface BillDeliveryCommentDao extends CrudDao<BillDeliveryComment> {
    /**
     * 获取所有客户未评价的列表
     * @param deliveryId
     * @return
     */
	public BillDeliveryComment getByDeliveryId(Integer deliveryId);
}