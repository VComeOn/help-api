/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.point.service;

import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.common.vo.Grid;
import com.thinkgem.jeesite.common.vo.GridParam;
import com.thinkgem.jeesite.modules.point.dao.PointLevelRewardDao;
import com.thinkgem.jeesite.modules.point.entity.PointLevelReward;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * 积分等级和奖励Service
 * @author wcf
 * @version 2018-01-07
 */
@Service
@Transactional(readOnly = true)
public class PointLevelRewardService extends CrudService<PointLevelRewardDao, PointLevelReward> {
	@Resource
	private PointLevelRewardDao dao;

	/**
	 * 通过等级获取数据
	 * @param level
	 * @return
	 */
	public PointLevelReward getByLevel(Integer level){
		return dao.getByLevel(level);
	}
}