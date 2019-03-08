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
import com.thinkgem.jeesite.modules.base.dao.BaseCarDao;
import com.thinkgem.jeesite.modules.base.dao.BaseCompanyDao;
import com.thinkgem.jeesite.modules.base.dao.BaseDriverDao;
import com.thinkgem.jeesite.modules.base.entity.BaseCar;
import com.thinkgem.jeesite.modules.base.entity.BaseCompany;
import com.thinkgem.jeesite.modules.base.entity.BaseDriver;
import com.thinkgem.jeesite.modules.base.service.BaseDriverService;
import com.thinkgem.jeesite.modules.bill.dao.*;
import com.thinkgem.jeesite.modules.bill.entity.*;
import com.thinkgem.jeesite.modules.driver.dao.DriverFaultRecordDao;
import com.thinkgem.jeesite.modules.message.dao.DriverMessageDao;
import com.thinkgem.jeesite.modules.message.entity.DriverMessage;
import com.thinkgem.jeesite.modules.point.dao.PointLevelRewardDao;
import com.thinkgem.jeesite.modules.point.dao.PointSetDao;
import com.thinkgem.jeesite.modules.point.entity.PointLevelReward;
import com.thinkgem.jeesite.modules.point.entity.PointSet;
import com.thinkgem.jeesite.modules.point.service.PointSetService;
import com.thinkgem.jeesite.modules.sys.dao.UserDao;
import com.thinkgem.jeesite.modules.sys.service.SystemService;
import com.thinkgem.jeesite.modules.thirdApi.customer.demo.Interface4app;
import com.thinkgem.jeesite.modules.thirdApi.customer.service.AppTransport;
import com.thinkgem.jeesite.modules.thirdApi.terrace.HttpService4app;
import com.thinkgem.jeesite.modules.utils.JPushUtil;
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
	private PointSetDao pointSetDao;
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
	private PointSetService pointSetService;
	@Resource
	private PointLevelRewardDao pointLevelRewardDao;
	@Resource
	private BillDeliveryCommentDao deliveryCommentDao;
	@Resource
	private BillDeliverySignDao billDeliverySignDao;
	@Resource
	private ApproveHistoryDao approveHistoryDao;
	@Resource
	private BaseCarDao baseCarDao;
	@Resource
	private BaseCompanyDao baseCompanyDao;
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
	 * 运单司机签收
	 * @param delivery
	 * @param driver
	 */
	@Transactional(readOnly = false)
	public void deliveryDriverSign(BillDelivery delivery, BaseDriver driver, BillLading lading){
		if(delivery.getId() == null){
			super.save(delivery);

			delivery.setDeliveryBillNo("CKXN-" + DateUtils.getDate("yyyyMM") + StringUtils.numberToString(delivery.getId(), 4));
			dao.updateNo(delivery);
		}else {
			super.save(delivery);
		}

		//绑定提单，发送短信和推送消息
		driverService.pushOrSms(delivery.getCompanyId(), lading.getDepartment(), BaseDriverService.SMS_TYPE_BIND_LADING, delivery.getDeliveryBillNo(), lading.getCustomerName(), driver.getId(),delivery.getLadingBillNo(), driver.getName(), driver.getPhone(), delivery.getSignQuantity() + "");

		//化轻公司客服平台获取物流APP通知信息
		Interface4app.setMessage(delivery.getDeliveryBillNo(),
				delivery.getLadingBillNo(), "提单" + delivery.getLadingBillNo() + "，司机" + driver.getName() + "手机" + driver.getPhone() + "已提货" + delivery.getSignQuantity() + "吨！",
				"绑定提单", driver.getName(), DateUtils.getDateTime(), driver.getPhone(), lading.getCustomerName(), delivery.getPlateNumber());

		//化轻公司客服平台获取物流APP出库信息
		Interface4app.setTransport(delivery.getDeliveryBillNo(),
				delivery.getLadingBillNo(), driver.getName(), driver.getPhone(), delivery.getPlateNumber(), delivery.getSignQuantity() + "", DateUtils.formatDate(delivery.getSignDate()),
				null, null, "已提货", "");
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

	/**
	 * 客户评论运单
	 * @param result
	 * @param delivery
	 */
	@Transactional(readOnly = false)
	public void customerComment(AppTransport result, BillDelivery delivery){
		try {
			int star;//客户评星
			if(delivery.getIsAdminComment().equals(1)){
				//后台已评价过，直接获取
				BillDeliveryComment comment = deliveryCommentDao.getByDeliveryId(delivery.getId());
				comment.setCustomerScore(Integer.parseInt(result.getEvaluate1()));
				comment.setCustomerComment(result.getEvaluate2());
				comment.setCustomerTime(DateUtils.parseDate(result.getEvaluate3(), "yyyy-MM-dd HH:mm:ss"));

				deliveryCommentDao.update(comment);
				star = comment.getCustomerScore();
			}else {
				//后台未评价过，直接创建
				BillDeliveryComment comment = new BillDeliveryComment();
				comment.setDeliveryId(delivery.getId());
				comment.setCustomerScore(Integer.parseInt(result.getEvaluate1()));
				comment.setCustomerComment(result.getEvaluate2());
				comment.setCustomerTime(DateUtils.parseDate(result.getEvaluate3(), "yyyy-MM-dd HH:mm:ss"));

				deliveryCommentDao.insert(comment);
				star = comment.getCustomerScore();
			}
			delivery.setIsCustomerComment(Global.BOOLEAN_YES);
			dao.update(delivery);

			//客户评价积分奖励
			PointSet set = pointSetService.get();
			if(set != null){
				int point = 0;
				switch (star){
					case 1:
						if(set.getStarOne() != null){
							point = set.getStarOne();
						}
						break;
					case 2:
						if(set.getStarTwo() != null){
							point = set.getStarTwo();
						}
						break;
					case 3:
						if(set.getStarThree() != null){
							point = set.getStarThree();
						}
						break;
					case 4:
						if(set.getStarFour() != null){
							point = set.getStarFour();
						}
						break;
					case 5:
						if(set.getStarFive() != null){
							point = set.getStarFive();
						}
						break;
				}
				if(point > 0){
					driverService.updateDriverPoint(delivery.getDriverId(), point, "客户评价积分奖励", delivery.getId(), null);
				}
			}
		}catch (Exception e){
			e.printStackTrace();
		}
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
	 * 插入运输任务
	 * @param delivery
	 */
	@Transactional(readOnly = false)
	public void insertDelivery(BillDelivery delivery){
		BaseDriver baseDriver =new BaseDriver();
		if(delivery.getDriverId() == null){
			baseDriver= driverDao.getByPhone(delivery.getDriverPhone());
			if(baseDriver !=null){
				delivery.setDriverId(baseDriver.getId());
			}else {
				baseDriver=new BaseDriver();
				baseDriver.setPhone(delivery.getDriverPhone());
				baseDriver.setName(delivery.getDriverName());
				baseDriver.setPwd(SystemService.entryptPassword("123456Dr"));
				baseDriver.setCompanyId(delivery.getCompanyId());
				baseDriver.setCardId(delivery.getIdcardDriver());
				baseDriver.setCertificateId(delivery.getCertificateId());
				baseDriver.setLicenceId(delivery.getLicenceId());
				baseDriver.setPoint(0);
				baseDriver.setPointTotal(0);
				baseDriver.setLevel(0);
				baseDriver.setIsOnDuty(0);//不在执勤
				baseDriver.setStatus(1);
				driverDao.insert(baseDriver);
				delivery.setDriverId(baseDriver.getId());
			}
		}else{
			baseDriver=driverDao.get(delivery.getDriverId());
		}

		BaseCar baseCar=baseCarDao.getCarByPlateNumber(delivery.getPlateNumber());
		if(baseCar ==null){
			baseCar=new BaseCar();
			baseCar.setPlateNumber(delivery.getPlateNumber());
			baseCar.setCapacity(delivery.getCapacity());
			baseCar.setCompanyId(delivery.getCompanyId());
			baseCar.setCreateDate(new Date());
			baseCar.setStatus(1);
			baseCarDao.insert(baseCar);
		}
			//拆分运输委托单
			super.save(delivery);
			//访问平台接口获取运输任务单号
		if(delivery.getDeliveryBillNo() == null){
			System.out.println("访问平台接口获取运输任务单号");
			HttpService4app httpService4app=new HttpService4app();
			try {
				delivery.setIdcardDriver(baseDriver.getCardId());
				delivery.setCapacity(baseCar.getCapacity());
				delivery.setDriverName(baseDriver.getName());
				delivery.setDriverPhone(baseDriver.getPhone());
				System.out.println("APP运输任务状态<<<<<<<<<<<<<<<<<"+delivery.getStatus());
				String result=httpService4app.insertDelivery(delivery,"insert");
				//解析json 获取运输任务号
				JSONObject jsonobject = JSONObject.fromObject(result);
				String resultStr = (String) jsonobject.getString("result");
				JSONObject result2 = JSONObject.fromObject(resultStr);
				String dataStr = (String) result2.getString("data");
				JSONObject result3 = JSONObject.fromObject(dataStr);
				String missioncode = (String)result3 .getString("missioncode");
				String approvestatus = (String)result3 .getString("approvestatus");
				System.out.println("提单平台返回的车辆任务状态<<<<<<<<<<<<<<<<<"+approvestatus);
				if(approvestatus != null){
					if(Integer.parseInt(approvestatus) == 2){
						delivery.setStatus(3);
					}
				}
				System.out.println("更新后的车辆任务状态===================="+delivery.getStatus());
				delivery.setDeliveryBillNo(missioncode);
				dao.updateNo(delivery);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		if(delivery.getStatus() == 2){
			BaseDriver driver=driverDao.get(delivery.getDriverId());
			if(driver.getIsOnDuty() == 0 && driver.getPlateNumber() ==null){
				driver.setIsOnDuty(1);
				driver.setPlateNumber(delivery.getPlateNumber());
				driver.setLastSignDate(new Date());
				driverDao.updateDuty(driver);
			}
		}
		BillDelivery billDelivery=dao.getInfoByDeliveryNo(delivery.getDeliveryBillNo());
		String content="【新的运输任务】，提单号："+billDelivery.getBillLading().getTiDanHao()+"，车牌号："+billDelivery.getPlateNumber()+"提单密令："+billDelivery.getBillLading().getBillSecret();

		//司机消息推送
		JPushUtil.sendNotifyToDriver(delivery.getDriverId(), "您有新的一个运输任务", content);

		//插入司机消息
		DriverMessage message = new DriverMessage();
		message.setDriverId(delivery.getDriverId());
		message.setType(DriverMessage.TYPE_REMIND);
		message.setTitle("新的运输任务");
		message.setContent(content);
		message.setIsRead(Global.BOOLEAN_NO);
		message.setCreateDate(new Date());
		driverMessageDao.insert(message);


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
	 * 客户签收
	 * @param delivery
	 * @param lading
	 */
	@Transactional(readOnly = false)
	public void customerSign(BillDelivery delivery, BillLading lading,BillDeliverySign billDeliverySign){
		startDelivery(delivery);
		billDeliverySignDao.insert(billDeliverySign);

		HttpService4app httpService4app=new HttpService4app();
		try {
			String result=httpService4app.insertDeliverySign(billDeliverySign,"insert");
		} catch (IOException e) {
			e.printStackTrace();
		}

		//送到签收，发送短信和推送消息
		driverService.pushOrSms(delivery.getCompanyId(), lading.getDepartment(), BaseDriverService.SMS_TYPE_ARRIVE_SIGN, delivery.getDeliveryBillNo(), lading.getCustomerName(), delivery.getDriverId(),lading.getTiDanHao(), delivery.getDeliveryBillNo(), billDeliverySign.getArriveSignQuantity() + "");

		//等级额外奖级积分
		BaseDriver driver = driverService.get(delivery.getDriverId());

		//化轻公司客服平台获取物流APP通知信息
		/*Interface4app.setMessage(delivery.getDeliveryBillNo(),
				delivery.getLadingBillNo(), "提单" + delivery.getLadingBillNo() + "对应的运单" + delivery.getDeliveryBillNo() + "已签收，签收数为" + billDeliverySign.getArriveSignQuantity() + "吨！",
				"送货签收", driver.getName(), DateUtils.getDateTime(), driver.getPhone(), delivery.getBillLading().getCustomerName(), delivery.getPlateNumber());*/

		//化轻公司客服平台获取物流APP出库信息
		/*Interface4app.setTransport(delivery.getDeliveryBillNo(),
				delivery.getLadingBillNo(), null, null, null, null, null,
				billDeliverySign.getArriveSignQuantity() + "", DateUtils.formatDate(billDeliverySign.getArriveSignDate()), "已送达", "");*/
	}

	/**
	 * 结束运输任务
	 * @param delivery
	 * @param lading
	 */
	@Transactional(readOnly = false)
	public int finisfDelivery(BillDelivery delivery, BillLading lading){
		super.save(delivery);
		BaseDriver driver = driverService.get(delivery.getDriverId());
		int point = 0;//记录获得的积分数
        if(delivery.getStatus() == 4 || delivery.getStatus() == 5 || delivery.getStatus() == 6 || delivery.getStatus() == 7 || delivery.getStatus() == 8 || delivery.getStatus() == 9){
			PointSet set = pointSetService.get();


			//有无异常停车
			if(delivery.getIsErrorStop().equals(Global.BOOLEAN_YES)){
				point += set.getErrorStop();
			}else{
				point += set.getNoErrorStop();
			}

			//有无关闭app
			if(delivery.getIsCloseApp().equals(Global.BOOLEAN_YES)){
				point += set.getCloseApp();
			}else{
				point += set.getOpenApp();
			}

			//是否准时到达
			if(lading.getLatestArriveTime() != null){
				if(lading.getLatestArriveTime().getTime() >= System.currentTimeMillis()){
					point += set.getArrive();
				}else{
					point += set.getLate();
				}
			}

			//运输应获得积分
			point += Math.ceil(((double)((delivery.getEndDeliveryDate().getTime() - delivery.getStartDeliveryDate().getTime()) / 1000) / 3600) * set.getSendPoint());

			//等级额外奖级积分
			if(driver.getLevel() != null){
				PointLevelReward level = pointLevelRewardDao.getByLevel(driver.getLevel());
				if(level != null){
					point += level.getRewardPoint();
				}
			}
			//更新司机积分
			driverService.updateDriverPoint(delivery.getDriverId(), point, "完成运单号" + delivery.getDeliveryBillNo(), delivery.getId(), null);
		}

		//解绑车辆
		driverDao.unbindCar(driver.getId());
		BillDelivery billDelivery=new BillDelivery();
		billDelivery.setDriverId(driver.getId());
		//billDelivery.setStatus(2);
		List<BillDelivery> billDeliveries=dao.getHomeTransitList(billDelivery);
		if(billDeliveries !=null && billDeliveries.size()>0){
			BaseDriver driver1=new BaseDriver();
			driver1.setId(driver.getId());
			driver1.setIsOnDuty(1);
			driver1.setLastSignDate(new Date());
			driver1.setPlateNumber(billDeliveries.get(0).getPlateNumber());
			driverDao.updateDuty(driver1);
		}

		return point;
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

	/**
	 * 更改任务认证驳回状态
	 * @param status
	 * @param id
	 * @return
	 */
	@Transactional(readOnly = false)
	public int updateStatus(Integer status,Integer id,Integer sysUserId){
		dao.updateStatus(status,id);
		BillDelivery billDelivery=dao.get(id);
		ApproveHistory approveHistory=new ApproveHistory();
		approveHistory.setSysUserId(sysUserId);
		approveHistory.setType(2);
		String content = null;
		if(status == BillDelivery.STATUS_RBUT){
			approveHistory.setStatus(2);
			content="【运输任务审核】，提单号："+billDelivery.getBillLading().getTiDanHao()+"，车牌号："+billDelivery.getPlateNumber()+"的车辆任务被驳回。";
		}else{
			approveHistory.setStatus(1);
			BaseCar baseCar=baseCarDao.getCarByPlateNumber(billDelivery.getPlateNumber());
			if(baseCar != null){
				baseCar.setCompanyId(billDelivery.getCompanyId());
				baseCarDao.update(baseCar);
				BaseCompany baseCompany=baseCompanyDao.get(billDelivery.getCompanyId());
				baseCar.setCompanyCode(baseCompany.getBaseCompanyCode());
				content="【运输任务审核】，提单号："+billDelivery.getBillLading().getTiDanHao()+"，车牌号："+billDelivery.getPlateNumber()+"的车辆任务已通过。";
				BaseDriver driver=driverDao.get(billDelivery.getDriverId());
				/*HttpService4app httpService4app=new HttpService4app();
				try {
					billDelivery.setIdcardDriver(driver.getCardId());
					billDelivery.setCapacity(baseCar.getCapacity());
					billDelivery.setDriverName(driver.getName());
					billDelivery.setDriverPhone(driver.getPhone());
					String result=httpService4app.insertDelivery(billDelivery,"insert");*/
					/*//解析json 获取运输任务号
					JSONObject jsonobject = JSONObject.fromObject(result);
					String resultStr = (String) jsonobject.getString("result");
					JSONObject result2 = JSONObject.fromObject(resultStr);
					String dataStr = (String) result2.getString("data");
					JSONObject result3 = JSONObject.fromObject(dataStr);
					String missioncode = (String)result3 .getString("missioncode");
					String approvestatus = (String)result3 .getString("approvestatus");
					if(approvestatus.equals(2)){
						billDelivery.setStatus(2);
					}
					billDelivery.setDeliveryBillNo(missioncode);
					dao.updateNo(billDelivery);
				} catch (IOException e) {
					e.printStackTrace();
				}*/
				if(driver.getIsOnDuty() == 0 && driver.getPlateNumber() ==null){
					driver.setIsOnDuty(1);
					driver.setPlateNumber(billDelivery.getPlateNumber());
					driver.setLastSignDate(new Date());
					driverDao.updateDuty(driver);
				}
			}

		}

		//司机消息推送
		JPushUtil.sendNotifyToDriver(billDelivery.getDriverId(), "运输任务审核", content);

		//插入司机消息
		DriverMessage message = new DriverMessage();
		message.setDriverId(billDelivery.getDriverId());
		message.setType(DriverMessage.TYPE_REMIND);
		message.setTitle("运输任务审核");
		message.setContent(content);
		message.setIsRead(Global.BOOLEAN_NO);
		message.setCreateDate(new Date());
		driverMessageDao.insert(message);

		approveHistory.setRemark(billDelivery.getDeliveryBillNo());
		approveHistory.setCreateTime(new Date());
		approveHistoryDao.insert(approveHistory);
		return 1;
	}
}