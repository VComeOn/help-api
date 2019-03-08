/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.bill.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.bill.entity.BillDeliverySign;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 运输任务签收记录DAO接口
 * @author qj
 * @version 2018-10-06
 */
@MyBatisDao
public interface BillDeliverySignDao extends CrudDao<BillDeliverySign> {

    public List<BillDeliverySign> findListBybillDeliveryNoAndPlate(@Param("deliveryBillNo") String deliveryBillNo,@Param("driverId") Integer driverId, @Param("plateNumber") String plateNumber);
	
}