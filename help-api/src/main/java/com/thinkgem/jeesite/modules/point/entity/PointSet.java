/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.point.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.thinkgem.jeesite.common.persistence.DataEntity;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * 积分设置Entity
 * @author wcf
 * @version 2018-01-07
 */
public class PointSet extends DataEntity<PointSet> {
	
	private static final long serialVersionUID = 1L;
	private Integer starOne;		// 一星
	private Integer starTwo;		// 二星
	private Integer starThree;		// 三星
	private Integer starFour;		// 四星
	private Integer starFive;		// 五星
	private Integer errorStop;		// 异常停车
	private Integer noErrorStop;		// 无异常停车
	private Integer arrive;		// 准时到达
	private Integer late;		// 未准时到达
	private Integer closeApp;		// 关闭app
	private Integer openApp;		// 开启app
	private Integer sendPoint;		// 运输积分/h
	private Date updateTime;		// 修改时间
	
	public PointSet() {
		super();
	}

	public PointSet(Integer id){
		super(id);
	}

	@NotNull(message="一星不能为空")
	public Integer getStarOne() {
		return starOne == null ? 0 : starOne;
	}

	public void setStarOne(Integer starOne) {
		this.starOne = starOne;
	}
	
	public Integer getStarTwo() {
		return starTwo == null ? 0 : starTwo;
	}

	public void setStarTwo(Integer starTwo) {
		this.starTwo = starTwo;
	}
	
	public Integer getStarThree() {
		return starThree == null ? 0 : starThree;
	}

	public void setStarThree(Integer starThree) {
		this.starThree = starThree;
	}
	
	public Integer getStarFour() {
		return starFour == null ? 0 : starFour;
	}

	public void setStarFour(Integer starFour) {
		this.starFour = starFour;
	}
	
	public Integer getStarFive() {
		return starFive == null ? 0 : starFive;
	}

	public void setStarFive(Integer starFive) {
		this.starFive = starFive;
	}
	
	public Integer getErrorStop() {
		return errorStop == null ? 0 : errorStop;
	}

	public void setErrorStop(Integer errorStop) {
		this.errorStop = errorStop;
	}
	
	public Integer getNoErrorStop() {
		return noErrorStop == null ? 0 : noErrorStop;
	}

	public void setNoErrorStop(Integer noErrorStop) {
		this.noErrorStop = noErrorStop;
	}
	
	public Integer getArrive() {
		return arrive == null ? 0 : arrive;
	}

	public void setArrive(Integer arrive) {
		this.arrive = arrive;
	}
	
	public Integer getLate() {
		return late == null ? 0 : late;
	}

	public void setLate(Integer late) {
		this.late = late;
	}
	
	public Integer getCloseApp() {
		return closeApp == null ? 0 : closeApp;
	}

	public void setCloseApp(Integer closeApp) {
		this.closeApp = closeApp;
	}
	
	public Integer getOpenApp() {
		return openApp == null ? 0 : openApp;
	}

	public void setOpenApp(Integer openApp) {
		this.openApp = openApp;
	}
	
	public Integer getSendPoint() {
		return sendPoint == null ? 0 : sendPoint;
	}

	public void setSendPoint(Integer sendPoint) {
		this.sendPoint = sendPoint;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	
}