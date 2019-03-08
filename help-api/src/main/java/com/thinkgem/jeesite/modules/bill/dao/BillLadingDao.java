/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.bill.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.bill.entity.BillLading;

import java.util.List;

/**
 * 提单信息DAO接口
 * @author wcf
 * @version 2018-01-15
 */
@MyBatisDao
public interface BillLadingDao extends CrudDao<BillLading> {

    /**
     * 通过提单编号获取提单信息
     * @param ladingNo
     * @return
     */
    public BillLading getByLadingNo(String ladingNo);

    /**
     * 批量插入提单信息
     * @param list
     * @return
     */
    public int insertList(List<BillLading> list);

    /**
     * 通过提单密令获取提单信息
     * @param billSecret
     * @return
     */
    public BillLading getByBillSecret(String billSecret);
}