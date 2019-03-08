/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.point.entity;

import com.thinkgem.jeesite.common.persistence.DataEntity;

import javax.validation.constraints.NotNull;

/**
 * 积分等级和奖励Entity
 * @author wcf
 * @version 2018-01-07
 */
public class PointLevelReward extends DataEntity<PointLevelReward> {
	
	private static final long serialVersionUID = 1L;
	private Integer level;		// 等级
	private Integer needPoint;		// 所需要积分
	private Integer rewardPoint;		// 订单完成奖励积分
	
	public PointLevelReward() {
		super();
	}

	public PointLevelReward(Integer id){
		super(id);
	}

	@NotNull(message="等级不能为空")
	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}
	
	@NotNull(message="所需要积分不能为空")
	public Integer getNeedPoint() {
		return needPoint;
	}

	public void setNeedPoint(Integer needPoint) {
		this.needPoint = needPoint;
	}
	
	@NotNull(message="订单完成奖励积分不能为空")
	public Integer getRewardPoint() {
		return rewardPoint;
	}

	public void setRewardPoint(Integer rewardPoint) {
		this.rewardPoint = rewardPoint;
	}
	
}