/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.point.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.point.entity.PointExchangeRecord;
import com.thinkgem.jeesite.modules.vo.PointExchangeRecordVo;

/**
 * 积分兑换记录DAO接口
 * @author wcf
 * @version 2018-01-07
 */
@MyBatisDao
public interface PointExchangeRecordDao extends CrudDao<PointExchangeRecord> {

    /**
     * 获取积分兑换成功后的详情
     * @param id
     * @return
     */
    public PointExchangeRecordVo getDetail(Integer id);
}