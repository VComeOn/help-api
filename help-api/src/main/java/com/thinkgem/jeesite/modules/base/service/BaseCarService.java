/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.base.service;

import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.common.vo.Grid;
import com.thinkgem.jeesite.common.vo.GridParam;
import com.thinkgem.jeesite.modules.approve.dao.ApproveHistoryDao;
import com.thinkgem.jeesite.modules.approve.entity.ApproveHistory;
import com.thinkgem.jeesite.modules.baiduApi.BaiduUtils;
import com.thinkgem.jeesite.modules.base.dao.BaseCarDao;
import com.thinkgem.jeesite.modules.base.dao.BaseCompanyDao;
import com.thinkgem.jeesite.modules.base.entity.BaseCar;
import com.thinkgem.jeesite.modules.base.entity.BaseCompany;
import com.thinkgem.jeesite.modules.thirdApi.terrace.HttpService4app;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Date;
import java.util.List;

/**
 * 车辆信息Service
 * @author wcf
 * @version 2017-12-28
 */
@Service
@Transactional(readOnly = true)
public class BaseCarService extends CrudService<BaseCarDao, BaseCar> {
	@Resource
	private BaseCarDao dao;
	@Resource
	private ApproveHistoryDao approveHistoryDao;
	@Resource
	private BaseCompanyDao baseCompanyDao;
	@Resource
	private BaiduUtils baiduUtils;
	 
	 /**    
	  * @description 获取不在使用中的车辆列表
	  * @param 
	  * @author wcf
	  * @date 2018/1/26 
	  * @return   
	  */  
	public Grid getNotUseCarList(BaseCar car, GridParam param){
		Grid grid = new Grid(param);
		car.setGrid(grid);
		grid.setRows(dao.getNotUseCarList(car));
		return grid;
	}

	public BaseCar getCarByPlateNumber(String plateNumber){
		return dao.getCarByPlateNumber(plateNumber);
	}


	/**
	 * @description 获取需要认证的车辆
	 * @param
	 * @author wcf
	 * @date 2018/1/26
	 * @return
	 */
	public Grid findCarExamine(BaseCar car, GridParam param){
		Grid grid = new Grid(param);
		car.setGrid(grid);
		grid.setRows(dao.findCarExamine(car));
		return grid;
	}

	/**
	 * 更改车辆认证驳回状态
	 * @param status
	 * @param id
	 * @return
	 */
	@Transactional(readOnly = false)
	public int updateStatus(Integer status,Integer id,Integer sysUserId) throws IOException {
		dao.updateStatus(status,id);
		BaseCar baseCar=dao.get(id);
		if(status == 1){
			BaseCompany baseCompany=baseCompanyDao.get(baseCar.getCompanyId());
			baseCar.setCompanyCode(baseCompany.getBaseCompanyCode());
			HttpService4app httpService4app=new HttpService4app();
			String result=httpService4app.insertTruck(baseCar,"insert");
			try {
				//如果是新增，则先将设备添加到百度鹰眼中
				if(baseCar.getId() == null){
					baiduUtils.addBaiduEntity(baseCar.getPlateNumber(), baseCompany.getName(),0);
				}else{
					//由于百度鹰眼提供的api无法修改设备的名称，只能先删除，再创建
					if(!baseCar.getPlateNumber().equals(baseCar.getPlateNumber())){
						baiduUtils.deleteBaiduEntity(baseCar.getPlateNumber(),0);
						baiduUtils.addBaiduEntity(baseCar.getPlateNumber(), baseCompany.getName(),0);
					}else{
						//更新公司名称
						if(!baseCar.getCompanyId().equals(baseCar.getCompanyId())){
							baiduUtils.updateBaiduEntity(baseCar.getPlateNumber(), baseCompany.getName(),0);
						}
					}
				}
			}catch (Exception e){
				e.printStackTrace();
			}
		}
		ApproveHistory approveHistory=new ApproveHistory();
		approveHistory.setSysUserId(sysUserId);
		approveHistory.setType(1);
		approveHistory.setStatus(status);
		approveHistory.setRemark(baseCar.getPlateNumber());
		approveHistory.setCreateTime(new Date());
		approveHistoryDao.insert(approveHistory);
		return 1;
	}


	/**
	 * @description 获取委托车辆列表
	 * @param
	 * @author wcf
	 * @date 2018/1/26
	 * @return
	 */
	public Grid getUseCarList(BaseCar car, GridParam param){
		Grid grid = new Grid(param);
		car.setGrid(grid);
		grid.setRows(dao.getUseCarList(car));
		return grid;
	}

	//   *************************************************** 二期接口 ************************************************
	/**
	 * @description 通过车牌号获取车辆信息
	 * @param
	 * @author qj
	 * @date 2018/10/08
	 * @return
	 */
	public BaseCar getByPlateNumber(Integer companyId, String plateNumber){
		return dao.getByPlateNumber(companyId, plateNumber);
	}

	/**
	 * @description 获取不在使用的车辆列表
	 * @param
	 * @author qj
	 * @date 2018/10/08
	 * @return
	 */
	public List<BaseCar> getNotUseCarList(BaseCar car){
		return dao.getNotUseCarList(car);
	}

	//   *************************************************** 管理端二期接口 ************************************************
	/**
	 * @description 获取可以委托车辆列表
	 * @param
	 * @author wcf
	 * @date 2018/1/26
	 * @return
	 */
	public List<BaseCar> selectCarList(BaseCar car){

		return dao.findList(car);
	}

}