/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.driver.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.driver.entity.DriverFaultRecord;

/**
 * 司机故障记录DAO接口
 * @author wcf
 * @version 2018-01-23
 */
@MyBatisDao
public interface DriverFaultRecordDao extends CrudDao<DriverFaultRecord> {

    /**
     * 获取运单异常停车的次数
     * @param deliveryBillNo
     * @return
     */
    public int countRecordByDelivery(String deliveryBillNo);
}