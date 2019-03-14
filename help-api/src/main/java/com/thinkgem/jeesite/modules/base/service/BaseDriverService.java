/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.base.service;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.vo.Grid;
import com.thinkgem.jeesite.common.vo.GridParam;
import com.thinkgem.jeesite.modules.adminApi.Vo.DriverDetailVo;
import com.thinkgem.jeesite.modules.adminApi.Vo.DriverVo;
import com.thinkgem.jeesite.modules.approve.dao.ApproveHistoryDao;
import com.thinkgem.jeesite.modules.approve.entity.ApproveHistory;
import com.thinkgem.jeesite.modules.base.dao.BaseDriverDao;
import com.thinkgem.jeesite.modules.base.entity.BaseDriver;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 司机信息Service
 * @author wcf
 * @version 2018-01-02
 */
@Service
@Transactional(readOnly = true)
public class BaseDriverService extends CrudService<BaseDriverDao, BaseDriver> {
	@Resource
	private BaseDriverDao dao;


	/**
	 * app管理端，获取司机状态列表
	 * @param vo
	 * @param param
	 * @return
	 */
/*	public Grid getDriverStatusList(DriverVo vo, GridParam param){
		Grid grid = new Grid(param);
		vo.setGrid(grid);
		List<DriverVo> driverVos=dao.getDriverStatusList(vo);
		List<DriverVo> driverVoShow=new ArrayList<DriverVo>();
		if(!CollectionUtils.isEmpty(driverVos)){
			for (DriverVo driverVo:driverVos){
				BillDelivery billDelivery=billDeliveryDao.getLastDelivery(driverVo.getId());
				if(billDelivery !=null){
					driverVo.setDeliveryBillNo(billDelivery.getDeliveryBillNo());
					driverVo.setDeliveryStatus(billDelivery.getStatus());
				}else{
					driverVo.setDeliveryBillNo(null);
					driverVo.setDeliveryStatus(-1);
				}
				driverVoShow.add(driverVo);
			}

		}
		grid.setRows(driverVoShow);
		return grid;
	}*/

	/**
	 * app管理端，搜索司机状态列表
	 * @param vo
	 * @return
	 */
/*	public List<DriverVo> searchDriverStatusList(DriverVo vo){
		List<DriverVo> driverVos=dao.getDriverStatusList(vo);
		List<DriverVo> driverVoShow=new ArrayList<DriverVo>();
		if(!CollectionUtils.isEmpty(driverVos)){
			for (DriverVo driverVo:driverVos){
				BillDelivery billDelivery=billDeliveryDao.getLastDelivery(driverVo.getId());
				if(billDelivery !=null){
					driverVo.setDeliveryBillNo(billDelivery.getDeliveryBillNo());
					driverVo.setDeliveryStatus(billDelivery.getStatus());
				}else{
					driverVo.setDeliveryBillNo(null);
					driverVo.setDeliveryStatus(-1);
				}
				driverVoShow.add(driverVo);
			}

		}
		return driverVoShow;
	}*/


	/**
	 * 获取可供选择的司机列表
	 * @param baseDriver
	 * @return
	 */
	public List<BaseDriver> driverSelectList(BaseDriver baseDriver){

		return dao.findList(baseDriver);
	}

	/**
	 * 通过车牌查找救援车辆
	 * @param plateNumber
	 * @return
	 */
	public BaseDriver getBaseDriverByPlateNumber(String plateNumber){

		return dao.getBaseDriverByPlateNumber(plateNumber);
	}
}