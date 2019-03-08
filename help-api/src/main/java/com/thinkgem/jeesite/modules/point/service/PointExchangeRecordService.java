/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.point.service;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.oauth.entity.Result;
import com.thinkgem.jeesite.common.oauth.entity.ResultStatusCode;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.common.vo.Grid;
import com.thinkgem.jeesite.common.vo.GridParam;
import com.thinkgem.jeesite.modules.base.dao.BaseDriverDao;
import com.thinkgem.jeesite.modules.base.entity.BaseDriver;
import com.thinkgem.jeesite.modules.base.service.BaseDriverService;
import com.thinkgem.jeesite.modules.point.dao.PointExchangeRecordDao;
import com.thinkgem.jeesite.modules.point.dao.PointMallDao;
import com.thinkgem.jeesite.modules.point.entity.PointExchangeRecord;
import com.thinkgem.jeesite.modules.point.entity.PointMall;
import com.thinkgem.jeesite.modules.vo.PointExchangeRecordVo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;

/**
 * 积分兑换记录Service
 * @author wcf
 * @version 2018-01-07
 */
@Service
@Transactional(readOnly = true)
public class PointExchangeRecordService extends CrudService<PointExchangeRecordDao, PointExchangeRecord> {
	@Resource
	private PointExchangeRecordDao dao;
	@Resource
	private BaseDriverService driverService;
	@Resource
	private PointMallDao mallDao;
	
	public Grid list(PointExchangeRecord pointExchangeRecord, GridParam param){
		Grid grid = new Grid(param);
		pointExchangeRecord.setGrid(grid);
		grid.setRows(dao.findList(pointExchangeRecord));
		return grid;
	}

	/**
	 * 司机申请积分兑换充值卡
	 * @param mallId
	 * @param driverId
	 */
	@Transactional(readOnly = false)
	public Result exchange(Integer mallId, Integer driverId){
		BaseDriver driver = driverService.get(driverId);
		PointMall mall = mallDao.get(mallId);

		if(driver.getPoint() < mall.getPointNum()){
			return new Result(Global.ERROR_CODE, "您的积分不足");
		}
		PointExchangeRecord record = new PointExchangeRecord();
		record.setStatus(PointExchangeRecord.STATUS_APPLY);
		record.setDriverId(driverId);
		record.setApplyDate(new Date());
		record.setMallId(mallId);
		record.setMallTitle(mall.getTitle());
		dao.insert(record);
		//更新用户积分
		driverService.updateDriverPoint(driverId, -mall.getPointNum(), "兑换商品 " + mall.getTitle(), null, record.getId());

		return new Result(ResultStatusCode.OK);
	}

	/**
	 * 获取积分兑换成功后的详情
	 * @param id
	 * @return
	 */
	public PointExchangeRecordVo getDetail(Integer id){
		return dao.getDetail(id);
	}
}