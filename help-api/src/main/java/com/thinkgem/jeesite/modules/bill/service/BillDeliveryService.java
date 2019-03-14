/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.bill.service;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.vo.Grid;
import com.thinkgem.jeesite.common.vo.GridParam;
import com.thinkgem.jeesite.modules.approve.dao.ApproveHistoryDao;
import com.thinkgem.jeesite.modules.approve.entity.ApproveHistory;
import com.thinkgem.jeesite.modules.base.dao.BaseDriverDao;
import com.thinkgem.jeesite.modules.base.entity.BaseDriver;
import com.thinkgem.jeesite.modules.base.service.BaseDriverService;
import com.thinkgem.jeesite.modules.bill.dao.*;
import com.thinkgem.jeesite.modules.bill.entity.*;
import com.thinkgem.jeesite.modules.driver.dao.DriverFaultRecordDao;
import com.thinkgem.jeesite.modules.message.dao.DriverMessageDao;
import com.thinkgem.jeesite.modules.message.entity.DriverMessage;
import com.thinkgem.jeesite.modules.sys.dao.UserDao;
import com.thinkgem.jeesite.modules.sys.service.SystemService;
import com.thinkgem.jeesite.modules.utils.RedisUtils;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 运单信息Service
 * @author wcf
 * @version 2018-01-15
 */
@Service
@Transactional(readOnly = true)
public class BillDeliveryService extends CrudService<BillDeliveryDao, BillDelivery> {
	@Resource
	private BillDeliveryDao dao;
	@Resource
	private DriverRunStopRecordDao runStopRecordDao;
	@Resource
	private DriverFaultRecordDao faultRecordDao;
	@Resource
	private BillLadingDao ladingDao;
	@Resource
	private UserDao userDao;
	@Resource
	private BaseDriverDao driverDao;
	@Resource
	private RedisUtils redisUtils;
	@Resource
	private BaseDriverService driverService;
	@Resource
	private BillDeliveryCommentDao deliveryCommentDao;
	@Resource
	private BillDeliverySignDao billDeliverySignDao;
	@Resource
	private ApproveHistoryDao approveHistoryDao;
	@Resource
	private DriverMessageDao driverMessageDao;


	public Grid list(BillDelivery billDelivery, GridParam param){
		Grid grid = new Grid(param);
		billDelivery.setGrid(grid);
		grid.setRows(dao.findList(billDelivery));
		return grid;
	}

	public List<BillDelivery> billDeliveryList(BillDelivery billDelivery){

		return dao.findAllList(billDelivery);
	}

	/**
	 * 运单绑定司机
	 * @param delivery
	 */
	@Transactional(readOnly = false)
	public void saveDelivery(BillDelivery delivery){
		super.save(delivery);

		delivery.setDeliveryBillNo("CKXN-" + DateUtils.getDate("yyyyMM") + StringUtils.numberToString(delivery.getId(), 4));
		dao.updateNo(delivery);
	}

	/**
	 * @description  根据运单编号获取运单基础信息
	 * @param
	 * @author wcf
	 * @date 2018/1/26
	 * @return
	 */
	public BillDelivery getBaseInfoByDeliveryNo(String deliveryNo){
		return dao.getBaseInfoByDeliveryNo(deliveryNo);
	}

	/**
	 * @description  根据出库编号获取提单和出库单信息
	 * @param
	 * @author wcf
	 * @date 2018/1/26
	 * @return
	 */
	public BillLading getByOutboundNo(String outboundBillNo){
		return dao.getByOutboundNo(outboundBillNo);
	}

	/**
	 * @description 获取司机未完成的运单数
	 * @param
	 * @author wcf
	 * @date 2018/2/2
	 * @return
	 */
	public int countOnDeliveryBill(Integer driverId){
		return dao.countOnDeliveryBill(driverId);
	}

	/**
	 * 获取司机的运单列表
	 * @param param
	 * @param driverId
	 * @return
	 */
	public Grid getDeliveryBillList(GridParam param, Integer driverId){
		BillDelivery delivery = new BillDelivery();
		delivery.setDriverId(driverId);

		Grid grid = new Grid(param);
		delivery.setGrid(grid);
		grid.setRows(dao.getDeliveryBillByDeriverId(delivery));
		return grid;
	}


	/**
	 * 获取执勤签到记录
	 * @param driverId
	 * @param month
	 * @return
	 */
	public List<Map> getSignDays(Integer driverId, String month){
		return dao.getSignDays(driverId,month);
	}

	/**
	 * 获取城市列表
	 * @return
	 */
	public List<Map> getCityList(){
		return dao.getCityList();
	}

	/**
	 * 根据运单编号获取运单详细信息
	 * @param deliveryNo
	 * @return
	 */
	public BillDelivery getByDeliveryNo(String deliveryNo){
		return dao.getByDeliveryNo(deliveryNo);
	}

	/**
	 * 获取运单基础信息，以及最后一次开始运输的时间
	 * @param delivery
	 * @return
	 */
	public List<BillDelivery> getDeliveryAndLastRunTime(BillDelivery delivery){
		return dao.getDeliveryAndLastRunTime(delivery);
	}

	/**
	 * 通过提单号获取运单数量
	 * @param ladingBillNo 提单号
	 * @param isBalance 是否结算
	 * @return
	 */
	public int countByLading(String ladingBillNo, Integer isBalance){
		return dao.countByLading(ladingBillNo, isBalance);
	}

	/**
	 * 获取所有客户未评价的列表
	 * @return
	 */
	public List<BillDelivery> getNotCustomerCommentList(){
		return dao.getNotCustomerCommentList();
	}


	//*****************************二期接口******************************************

	/**
	 * 通过司机和车辆获取运输任务
	 * @param delivery
	 * @return
	 */
	public  List<BillDelivery> getDeliveryByDriverAndCar(BillDelivery delivery){
		return dao.getDeliveryByDriverAndCar(delivery);
	}

	/**
	 * 通过运输委托单号获取所有运单信息
	 * @param ladingBillNo
	 * @return
	 */
	public List<BillDelivery> getAllByLadingNo(String ladingBillNo){
		return dao.getAllByLadingNo(ladingBillNo);
	}

	/**
	 * 通过运输委托单号司机车牌获取运输委托单
	 * @param ladingBillNo
	 * @param driverId
	 * @param plateNumber
	 * @return
	 */
	public List<BillDelivery> getByLadingNoDriverCar(String ladingBillNo,Integer driverId, String plateNumber){
		return dao.getByLadingNoDriverCar(ladingBillNo,driverId,plateNumber);
	}

	/**
	 * 通过运输委托单号司机车牌获取已完成车辆任务
	 * @param ladingBillNo
	 * @param driverId
	 * @param plateNumber
	 * @return
	 */
	public List<BillDelivery> getFinishByLadingNoDriverCar(String ladingBillNo,Integer driverId, String plateNumber){
		return dao.getFinishByLadingNoDriverCar(ladingBillNo,driverId,plateNumber);
	}

	/**
	 * 通过运输委托单号司机车牌获取已派发车辆任务
	 * @param ladingBillNo
	 * @param driverId
	 * @param plateNumber
	 * @return
	 */
	public List<BillDelivery> getDriverHaveStatus(String ladingBillNo,Integer driverId, String plateNumber){
		return dao.getDriverHaveStatus(ladingBillNo,driverId,plateNumber);
	}

	/**
	 * 通过运输委托单号司机车牌获取已接单车辆任务
	 * @param ladingBillNo
	 * @param driverId
	 * @param plateNumber
	 * @return
	 */
	public List<BillDelivery> getDriverHaveStatus3(String ladingBillNo,Integer driverId, String plateNumber){
		return dao.getDriverHaveStatus3(ladingBillNo,driverId,plateNumber);
	}


	/**
	 * 运单开始运输
	 * @param delivery
	 */
	@Transactional(readOnly = false)
	public void startDelivery(BillDelivery delivery){
		delivery.setStatus(BillDelivery.STATUS_TRANSIT);//运输中
		if(delivery.getStartDeliveryDate() == null){
			delivery.setStartDeliveryDate(new Date());
		}

		super.save(delivery);

		DriverRunStopRecord record = new DriverRunStopRecord();
		record.setDeliveryBillId(delivery.getId());
		record.setRunTime(new Date());
		record.setDriverId(delivery.getDriverId());
		record.setCompanyId(delivery.getCompanyId());
		record.setLadingBillNo(delivery.getLadingBillNo());
		runStopRecordDao.insert(record);

		if(delivery.getStartDeliveryDate() == null){
			//将车辆添加至队列
			redisUtils.setList(RedisUtils.WAIT_DELIVERY_TRUCK, delivery.getPlateNumber() + "," + delivery.getDeliveryBillNo());
		}
	}

	/**
	 * 运单中途停车
	 * @param delivery
	 */
	@Transactional(readOnly = false)
	public void stopDelivery(BillDelivery delivery){
		delivery.setStatus(BillDelivery.STATUS_STOP);//中途停车
		super.save(delivery);

		DriverRunStopRecord record = runStopRecordDao.getLastRecordByDeliveryNo(delivery.getId());
		record.setStopTime(new Date());
		runStopRecordDao.update(record);
	}

	/**
	 * 结束运输
	 * @param delivery
	 */
	@Transactional(readOnly = false)
	public void endDelivery(BillDelivery delivery){
		delivery.setStatus(BillDelivery.STATUS_ARRIVE);
		super.save(delivery);

		DriverRunStopRecord record = runStopRecordDao.getLastRecordByDeliveryNo(delivery.getId());
		record.setStopTime(new Date());
		runStopRecordDao.update(record);
	}


	/**
	 * @description 首页获取司机所有运单任务
	 * @param
	 * @author wcf
	 * @date 2018/1/25
	 * @return
	 */
	public Grid getAllocatOrTransitList(BillDelivery billDelivery, GridParam param){
		Grid grid = new Grid(param);
		billDelivery.setGrid(grid);
		grid.setRows(dao.getAllocatOrTransitList(billDelivery));
		return grid;
	}

	/**
	 * @description 首页获取司机未完成运单任务
	 * @param
	 * @author wcf
	 * @date 2018/1/25
	 * @return
	 */
	public Grid getHomeTransitList(BillDelivery billDelivery, GridParam param){
		Grid grid = new Grid(param);
		billDelivery.setGrid(grid);
		grid.setRows(dao.getHomeTransitList(billDelivery));
		return grid;
	}

	/**
	 * @description 首页获取司机未完成运单任务不分页
	 * @param
	 * @author wcf
	 * @date 2018/1/25
	 * @return
	 */
	public List<BillDelivery> getHomeTransitList(BillDelivery billDelivery){
		dao.getHomeTransitList(billDelivery);
		return dao.getHomeTransitList(billDelivery);
	}

	/**
	 *通过车牌号、司机、运输委托单号、状态判断是否可以绑定运输委托单
	 * @param billDelivery
	 * @return
	 */
	public List<BillDelivery> getDeliveryByDriverAndCarAndLadingNo(BillDelivery billDelivery){
		return dao.getDeliveryByDriverAndCarAndLadingNo(billDelivery);
	}

	/**
	 * 获取运单详情以及运输委托单任务、评论
	 * @param id
	 * @return
	 */
	public BillDelivery getDetailById(Integer id){
		return dao.getDetailById(id);
	}


	//*****************************   管理端二期接口   *******************************************************************

	/**
	 * 获取累计司机提货量
	 * @param ladingBillNo
	 * @return
	 */
	public double getTotalLadingQuantity(String ladingBillNo){
		return dao.getTotalLadingQuantity(ladingBillNo);
	}

	/**
	 * 获取需要审核的任务列表
	 * @param billDelivery
	 * @param param
	 * @return
	 */
	public Grid findBillDeliveryExamine(BillDelivery billDelivery, GridParam param){
		Grid grid = new Grid(param);
		billDelivery.setGrid(grid);
		grid.setRows(dao.findBillDeliveryExamine(billDelivery));
		return grid;
	}

}