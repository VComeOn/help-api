/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.bill.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.bill.entity.AddressLngLatRecord;

/**
 * 地址历史记录DAO接口
 * @author wcf
 * @version 2018-01-12
 */
@MyBatisDao
public interface AddressLngLatRecordDao extends CrudDao<AddressLngLatRecord> {

    /**
     * 通过地址查找
     * @param address
     * @return
     */
    public AddressLngLatRecord getByAdress(String address);
}