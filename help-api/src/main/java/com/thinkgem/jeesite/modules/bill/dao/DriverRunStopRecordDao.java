/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.bill.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.bill.entity.DriverRunStopRecord;

/**
 * 运输途中司机启停记录DAO接口
 * @author wcf
 * @version 2018-01-30
 */
@MyBatisDao
public interface DriverRunStopRecordDao extends CrudDao<DriverRunStopRecord> {

    /**
     * 获取该运单的最后一条启停记录
     * @param deliveryBillId
     * @return
     */
    public DriverRunStopRecord getLastRecordByDeliveryNo(Integer deliveryBillId);
}