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
import com.thinkgem.jeesite.modules.bill.dao.BillDeliveryDao;
import com.thinkgem.jeesite.modules.bill.dao.BillDeliverySignDao;
import com.thinkgem.jeesite.modules.bill.entity.BillDelivery;
import com.thinkgem.jeesite.modules.bill.entity.BillDeliverySign;
import com.thinkgem.jeesite.modules.driver.dao.DriverFaultRecordDao;
import com.thinkgem.jeesite.modules.driver.entity.DriverFaultRecord;
import com.thinkgem.jeesite.modules.message.dao.DriverMessageDao;
import com.thinkgem.jeesite.modules.message.dao.UserMessageDao;
import com.thinkgem.jeesite.modules.message.entity.DriverMessage;
import com.thinkgem.jeesite.modules.message.entity.UserMessage;
import com.thinkgem.jeesite.modules.point.dao.PointLevelRewardDao;
import com.thinkgem.jeesite.modules.point.dao.PointRecordDao;
import com.thinkgem.jeesite.modules.point.entity.PointLevelReward;
import com.thinkgem.jeesite.modules.point.entity.PointRecord;
import com.thinkgem.jeesite.modules.sys.dao.UserDao;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.thirdApi.terrace.HttpService4app;
import com.thinkgem.jeesite.modules.utils.JPushUtil;
import com.thinkgem.jeesite.modules.utils.SmsUtil;
import org.apache.commons.collections.CollectionUtils;
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
	@Resource
	private UserDao userDao;
	@Resource
	private PointLevelRewardDao pointLevelRewardDao;
	@Resource
	private PointRecordDao pointRecordDao;
	@Resource
	private UserMessageDao userMessageDao;
	@Resource
	private DriverFaultRecordDao faultRecordDao;
	@Resource
	private ApproveHistoryDao approveHistoryDao;
	@Resource
	private BillDeliveryDao billDeliveryDao;
	@Resource
	private BillDeliverySignDao billDeliverySignDao;
	@Resource
	private DriverMessageDao driverMessageDao;
	/**
	 * 通过手机号获取司机信息
	 * @param phone
	 * @return
	 */
	public BaseDriver getByPhone(String phone){
		return dao.getByPhone(phone);
	}


	public static final int SMS_TYPE_BIND_LADING = 0;		//绑定提单
	public static final int SMS_TYPE_START_DELIVERY = 1;	//开始送货
	public static final int SMS_TYPE_WILL_ARRIVE = 2;		//即将到达
	public static final int SMS_TYPE_ARRIVE_SIGN = 3;		//送货签收
	public static final int SMS_TYPE_ERROR_STOP = 4;		//异常停靠
	public static final int SMS_TYPE_ERROR_STATUS = 5;		//异常状态

	/**
	 * 推送或者发短信提醒公司管理员和平台管理员
	 * @param companyId 公司id
	 * @param department 部门编码
	 * @param type 类型，0：绑定提单，提单xxx，司机xxx手机xxx已提货xxx吨！
	 *             		1：开始送货，提单xxx,司机xxx手机xxx已驶出xxx，开始送货！
	 *             		2：即将到达，提单xxx,司机xxx手机xxx离送货地点还有xxx公里！
	 *             		3：送货签收，提单xxx对应的运单xxx已签收，签收数为xxx吨！
	 *             		4：异常停靠，提单xxx，司机xxx手机xxx已异常停靠x分钟!
	 *             		5：异常状态，提单xxx，类型：xxx，司机xxx手机xxx
	 * @param deliveryBillNo 运单编号
	 * @param driverId 司机id
	 * @param param 推送内容所需要的参数
	 */
	@Transactional(readOnly = false)
	public void pushOrSms(Integer companyId, String department, Integer type, String deliveryBillNo, String customerName, Integer driverId,  String... param){
		List<User> managers = userDao.getAllManager(companyId, department);
		//统计所有需要推送的管理员
		List<Integer> ids = new ArrayList<Integer>();
		//统计所有需要发送短信的管理员
		StringBuilder phones = new StringBuilder();
		//管理员消息记录
		List<UserMessage> messageList = new ArrayList<UserMessage>();
		for(User user : managers){
			if(user.getPushType() == null || user.getPushType().equals(User.PUSH_TYPE_JPUSH)){
				ids.add(user.getId());
			}else if (user.getPushType().equals(User.PUSH_TYPE_SMS)){
				if(StringUtils.isNotEmpty(user.getPhone())){
					phones.append(",").append(user.getPhone());
				}
			}
			UserMessage message = new UserMessage();
			if(type.equals(0)){
				//提单绑定
				message.setContent("【提单绑定】，提单" + param[0] + "，司机" + param[1] + "手机" + param[2] + "已提货" + param[3] + "吨！【" + customerName + "】");
			}else if(type.equals(1)){
				//开始送货
				message.setContent("【开始运输】，提单" + param[0] + "，司机" + param[1] + "手机" + param[2] + "已驶出" + param[3] + "，开始送货！【" + customerName + "】");
			}else if(type.equals(2)){
				//即将到达
				message.setContent("【即将到达】，提单" + param[0] + "，司机" + param[1] + "手机" + param[2] + "离送货地点还有" + param[3] + "公里！【" + customerName + "】");
			}else if(type.equals(3)){
				//送货签收
				message.setContent("【送货签收】，提单" + param[0] + "对应的运单" + param[1] + "已签收，签收数为" + param[2] + "吨！【" + customerName + "】");
			}else if(type.equals(4)){
				//异常停靠
				message.setContent("【异常停靠】，提单" + param[0] + "，司机" + param[1] + "手机" + param[2] + "已异常停靠" + param[3] + "分钟！【" + customerName + "】");
			}else if(type.equals(5)){
				//异常状态
				message.setContent("【异常状态】，提单" + param[0] + "，类型：" + param[1] + "，司机" + param[2] + "手机" + param[3] + "【" + customerName + "】");
			}
			message.setUserId(user.getId());
			message.setDeliveryBillNo(deliveryBillNo);
			message.setIsRead(Global.BOOLEAN_NO);
			message.setCreateDate(new Date());
			message.setDriverId(driverId);

			messageList.add(message);
		}
		if(!CollectionUtils.isEmpty(messageList)){
			userMessageDao.insertList(messageList);
		}


		//推送
		if(CollectionUtils.isNotEmpty(ids)){
			if(type.equals(0)){
				//提单绑定
				JPushUtil.sendNotifyToManager(ids, "绑定提单", "提单" + param[0] + "，司机" + param[1] + "手机" + param[2] + "已提货" + param[3] + "吨！");
			}else if(type.equals(1)){
				//开始送货
				JPushUtil.sendNotifyToManager(ids, "开始送货", "提单" + param[0] + "，司机" + param[1] + "手机" + param[2] + "已驶出" + param[3] + "，开始送货！");
			}else if(type.equals(2)){
				//即将到达
				JPushUtil.sendNotifyToManager(ids, "即将到达", "提单" + param[0] + "，司机" + param[1] + "手机" + param[2] + "离送货地点还有" + param[3] + "公里！");
			}else if(type.equals(3)){
				//送货签收
				JPushUtil.sendNotifyToManager(ids, "送货签收", "提单" + param[0] + "对应的运单" + param[1] + "已签收，签收数为" + param[2] + "吨！");
			}else if(type.equals(4)){
				//异常停靠
				JPushUtil.sendNotifyToManager(ids, "异常停靠", "提单" + param[0] + "，司机" + param[1] + "手机" + param[2] + "已异常停靠" + param[3] + "分钟！");
			}else if(type.equals(5)){
				//异常状态
				JPushUtil.sendNotifyToManager(ids, "异常状态", "提单" + param[0] + "，类型：" + param[1] + "，司机" + param[2] + "手机" + param[3]);
			}

		}
		//短信
		if(phones.length() > 0){
			if(type.equals(0)){
				//提单绑定
				SmsUtil.bindLadingSms(phones.toString().substring(1), param[0], param[1], param[2], param[3]);
			}else if(type.equals(1)){
				//开始送货
				SmsUtil.startDelierySms(phones.toString().substring(1), param[0], param[1], param[2], param[3]);
			}else if(type.equals(2)){
				//即将到达
				SmsUtil.approachingSms(phones.toString().substring(1), param[0], param[1], param[2], param[3]);
			}else if(type.equals(3)){
				//送货签收
				SmsUtil.arriveSignSms(phones.toString().substring(1), param[0], param[1], param[2]);
			}else if(type.equals(4)){
				//异常停靠
				SmsUtil.errorStopSms(phones.toString().substring(1), param[0], param[1], param[2], param[3]);
			}else if(type.equals(5)){
				//异常状态
				SmsUtil.errorStatusSms(phones.toString().substring(1), param[0], param[2], param[3], param[1]);
			}

		}
	}

	/**
	 * 更新司机积分记录
	 * @param driverId 司机id
	 * @param point 积分数
	 * @param remark 积分记录备注
	 * @param billDeliveryId 运单id
	 * @param exchangeId 兑换id
	 */
	@Transactional(readOnly = false)
	public synchronized void updateDriverPoint(Integer driverId, Integer point, String remark, Integer billDeliveryId, Integer exchangeId){
		BaseDriver driver = dao.get(driverId);
		driver.setPoint(driver.getPoint() + point);
		if(point > 0){
			driver.setPointTotal(driver.getPointTotal() + point);

			PointLevelReward level = pointLevelRewardDao.getMaxLevelByPoint(driver.getPointTotal());
			if(level != null){
				//更新等级
				if(level.getLevel() > driver.getLevel()){
					driver.setLevel(level.getLevel());
				}
			}
		}

		dao.update(driver);

		PointRecord record = new PointRecord();
		record.setCompanyId(driver.getCompanyId());
		record.setDriverId(driverId);
		record.setPointBefore(driver.getPoint() - point);
		record.setPointChange(Math.abs(point));
		record.setPointAfter(driver.getPoint());
		record.setOperationType(point < 0 ? PointRecord.OPERATE_TYPE_MINUS : PointRecord.OPERATE_TYPE_PLUS);
		record.setRemark(remark);
		record.setOrderId(billDeliveryId);
		record.setExchangeId(exchangeId);
		record.setCreateDate(new Date());
		pointRecordDao.insert(record);
	}

	/**
	 * app管理端，获取司机状态列表
	 * @param vo
	 * @param param
	 * @return
	 */
	public Grid getDriverStatusList(DriverVo vo, GridParam param){
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
	}

	/**
	 * app管理端，搜索司机状态列表
	 * @param vo
	 * @return
	 */
	public List<DriverVo> searchDriverStatusList(DriverVo vo){
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
	}

	/**
	 * 获取司机状态详情
	 * @param deliveryBillNo
	 * @param driverId
	 * @return
	 */
	public DriverDetailVo getDriverDetail(String deliveryBillNo, Integer driverId){
		DriverDetailVo vo = dao.getDriverDetail(deliveryBillNo, driverId);
		if(vo.getDelivery() != null){
			DriverFaultRecord record = new DriverFaultRecord();
			record.setDriverId(driverId);
			vo.setFaultRecordList(faultRecordDao.findList(record));
			BillDeliverySign billDeliverySign=new BillDeliverySign();
			billDeliverySign.setDeliveryBillNo(deliveryBillNo);
			vo.setBillDeliverySigns(billDeliverySignDao.findList(billDeliverySign));
		}
		return vo;
	}

	/**
	 * 更改司机认证驳回状态
	 * @param status
	 * @param id
	 * @return
	 */
	@Transactional(readOnly = false)
	public int updateStatus(Integer status,Integer id,Integer sysUserId) throws IOException {
		dao.updateStatus(status,id);
		BaseDriver baseDriver=dao.get(id);
		String content = "【司机认证】,司机认证已驳回";
		if(status == 1){
			HttpService4app httpService4app=new HttpService4app();
			String result=httpService4app.insertDriver(baseDriver,"insert&appId=APP");
			content = "【司机认证】,司机认证已通过";

		}
		//司机消息推送
		JPushUtil.sendNotifyToDriver(id, "司机认证", content);

		//插入司机消息
		DriverMessage message = new DriverMessage();
		message.setDriverId(id);
		message.setType(DriverMessage.TYPE_REMIND);
		message.setTitle("司机认证");
		message.setContent(content);
		message.setIsRead(Global.BOOLEAN_NO);
		message.setCreateDate(new Date());
		driverMessageDao.insert(message);

		ApproveHistory approveHistory=new ApproveHistory();
		approveHistory.setSysUserId(sysUserId);
		approveHistory.setType(0);
		approveHistory.setStatus(status);
		approveHistory.setRemark(baseDriver.getName());
		approveHistory.setCreateTime(new Date());
		approveHistoryDao.insert(approveHistory);
		return 1;
	}


	// **************************二期接口**************************
	/**
	 * 解绑车辆
	 * @param driverId
	 * @return
	 */
	@Transactional(readOnly = false)
	public int unbindCar(Integer driverId){
		return dao.unbindCar(driverId);
	}


	// **************************管理端二期接口**************************
	/**
	 * app管理端，获取需要审核的司机列表
	 * @param param
	 * @return
	 */
	public Grid finDriverExamine(BaseDriver baseDriver, GridParam param){
		Grid grid = new Grid(param);
		baseDriver.setGrid(grid);
		grid.setRows(dao.finDriverExamine(baseDriver));
		return grid;
	}

	/**
	 * 获取可供选择的司机列表
	 * @param baseDriver
	 * @return
	 */
	public List<BaseDriver> driverSelectList(BaseDriver baseDriver){

		return dao.findList(baseDriver);
	}

}