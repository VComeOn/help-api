/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.point.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.point.entity.PointLevelReward;

/**
 * 积分等级和奖励DAO接口
 * @author wcf
 * @version 2018-01-07
 */
@MyBatisDao
public interface PointLevelRewardDao extends CrudDao<PointLevelReward> {
    /**
     * 通过等级获取数据
     * @param level
     * @return
     */
    public PointLevelReward getByLevel(Integer level);

    /**
     * 通过积分获取最大等级信息
     * @param needPoint
     * @return
     */
    public PointLevelReward getMaxLevelByPoint(Integer needPoint);
}