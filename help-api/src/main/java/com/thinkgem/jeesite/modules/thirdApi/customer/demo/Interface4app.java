package com.thinkgem.jeesite.modules.thirdApi.customer.demo;

import com.thinkgem.jeesite.modules.thirdApi.customer.service.*;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

public class Interface4app {

	protected static Logger logger = LoggerFactory.getLogger("Interface4app");

	public static void main(String[] args) {
		ArrayList<Outhouse> list = getOuthouse();
		for(Outhouse entity : list){
			System.out.println("出库单号：" + entity.getBillno());
			System.out.println("提单号：" + entity.getBillno2());
			System.out.println("货物名称：" + entity.getGoodsname());
			System.out.println("应发数：" + entity.getPlanquantity());
		}
	}

	public static ArrayList<Outhouse> getOuthouse(){
		//接口实例化
		Itf4AppService sv = new Itf4AppService();
		Itf4AppDelegate dg = sv.getItf4AppPort();
		
		//接口调用
		//retOuthouse(出库查询日期起，出库查询日期止)
		ArrayList<Outhouse> dataobject = (ArrayList<Outhouse>) dg.getOuthouse4App("2018-03-01", "2018-03-06", null);
		for(int i=0;i<dataobject.size();i++){
			String outhousedate=dataobject.get(i).getOuthousedate();	//出库日期
			String billno=dataobject.get(i).getBillno();				//出库单号
			String billno2=dataobject.get(i).getBillno2();				//提单号
			String goodsname=dataobject.get(i).getGoodsname();			//货物名称
			String planquantity=dataobject.get(i).getPlanquantity();	//应发数
			String selfweight=dataobject.get(i).getSelfweight();		//皮重
			String gross=dataobject.get(i).getGross();					//毛重
			String location=dataobject.get(i).getLocation();			//罐号
			String ownername=dataobject.get(i).getOwnername();			//货主名称
			String ownername2=dataobject.get(i).getOwnername2();		//提货单位
			String thfs=dataobject.get(i).getThfs();					//提货方式
			String company=dataobject.get(i).getCompany();				//仓库名称

			String qsdate=dataobject.get(i).getQsdate();				//签收时间
			String qsqty=dataobject.get(i).getQsqty();					//签收数
			String qsfile=dataobject.get(i).getQsfile();				//签收文件
		}
		return dataobject;
	}

	/**
	 * 通过提单号获取出库单列表
	 * @param ladingNo
	 * @return
	 */
	public static ArrayList<Outhouse> getOuthouse(String ladingNo){
		//接口实例化
		Itf4AppService sv = new Itf4AppService();
		Itf4AppDelegate dg = sv.getItf4AppPort();

		//接口调用
		//retOuthouse(出库查询日期起，出库查询日期止)
		ArrayList<Outhouse> dataobject = (ArrayList<Outhouse>) dg.getOuthouse4App(null, null, ladingNo);
		return dataobject;
	}

	/**
	 * 化轻公司客服平台获取物流APP通知信息
	 * @param ckNo 出库单号，内（出库单号），外（虚拟出库单号）
	 * @param ladingBillNo 提单号
	 * @param content 消息内容
	 * @param contentType 消息类型
	 * @param driverName 司机名称
	 * @param msgTime 消息时间
	 * @param driverPhone 司机手机号
	 * @param thUnit 提货单位
	 * @param plateNumber 车牌号
	 */
	public static void setMessage(String ckNo, String ladingBillNo, String content, String contentType, String driverName,
									String msgTime, String driverPhone, String thUnit, String plateNumber){
		//接口实例化
		Itf4AppService sv = new Itf4AppService();
		Itf4AppDelegate dg = sv.getItf4AppPort();
		
		//接口调用
		//setMessage4App()
		ArrayList<AppMessage> lst = new ArrayList<AppMessage>();
		AppMessage m = new AppMessage();
		m.setBillno(ckNo);								//出库单号
		m.setBillno2(ladingBillNo);							//提单号
		m.setContent(content);							//消息内容
		m.setContenttype(contentType);					//消息类型(预留字段)
		m.setDriver(driverName);								//司机
		m.setMaketime(msgTime);						//消息时间
		m.setMobile(driverPhone);								//联系电话
		m.setOwnername(thUnit);						//提货单位
		m.setShip(plateNumber);									//车牌号
		lst.add(m);
		
		String dataobject = dg.setMessage4App(lst);
		if(dataobject.equals("ok")){
			logger.info("=================化轻公司客服平台获取物流APP通知信息成功==================");
		}else {
			logger.info("=================化轻公司客服平台获取物流APP通知信息失败==================");
		}
	}

	/**
	 * 化轻公司客服平台获取物流APP出库信息
	 * @param ckNo 出库单号，内（出库单），外（虚拟出库单号）
	 * @param ladingBillNo 提单编号
	 * @param driverName 司机名称
	 * @param driverPhone 司机手机
	 * @param plateNumber 车牌号
	 * @param signQuantity 司机签收数量
	 * @param signDate 司机签收日期
	 * @param arriveSignQuantity 客户签收数量
	 * @param arriveSignDate 客户签收日期
	 * @param status 状态（已提货、运输中、已送达）
	 * @param ckName 仓库名称
	 * @return
	 */
	//司机签收、驶出电子围栏、客户签收
	public static String setTransport(String ckNo, String ladingBillNo, String driverName, String driverPhone, String plateNumber, String signQuantity,
									  String signDate, String arriveSignQuantity, String arriveSignDate, String status, String ckName){
		//接口实例化
		Itf4AppService sv = new Itf4AppService();
		Itf4AppDelegate dg = sv.getItf4AppPort();
		
		//接口调用
		//setTransport4App()
		ArrayList<AppTransport> lst = new ArrayList<AppTransport>();
		AppTransport m = new AppTransport();
		m.setBillno(ckNo);						//出库单号，内（出库单），外（虚拟出库单号）
		m.setBillno2(ladingBillNo);					//提单号
		m.setDriver(driverName);						//司机
		m.setMobile(driverPhone);						//电话
		m.setShip(plateNumber);							//车牌号
		m.setSendqty(signQuantity);							//司机提货签收数量
		m.setSendtime(signDate);				//提货签收时间
		m.setReceiveqty(arriveSignQuantity);						//送到签收数
		m.setReceivetime(arriveSignDate);				//送到签收时间
		m.setCstate(status);							//运输状态（已提货、运输中、已送达）
		m.setWarehouse(ckName);                   //仓库名称
		lst.add(m);
		
		String dataobject = dg.setTransport4App(lst);
		
		return dataobject;
	}

	//化轻公司传递客户评价给物流app
	public static ArrayList<AppTransport> getEvaluate(){
		//接口实例化
		Itf4AppService sv = new Itf4AppService();
		Itf4AppDelegate dg = sv.getItf4AppPort();
		
		//接口调用
		//getEvaluate4App(出库单号，提单号)
		ArrayList<AppTransport> dataobject = (ArrayList<AppTransport>) dg.getEvaluate4App("billno", "billno2");
		for(int i=0;i<dataobject.size();i++){
			String billno = dataobject.get(i).getBillno();				//出库单号
			String billno2 = dataobject.get(i).getBillno2();				//提单号
			String evaluate1 = dataobject.get(i).getEvaluate1();			//客户满意度评分
			String evaluate2 = dataobject.get(i).getEvaluate2();			//客户满意度评价
			String evaluate3 = dataobject.get(i).getEvaluate3();			//评论时间
		}
		return dataobject;
	}

	/**
	 * 化轻公司传递客户评价给物流app
	 * @param ladingBillNo
	 * @param ckNo
	 * @return
	 */
	public static AppTransport getEvaluate(String ladingBillNo, String ckNo){
		//接口实例化
		Itf4AppService sv = new Itf4AppService();
		Itf4AppDelegate dg = sv.getItf4AppPort();

		//接口调用
		//getEvaluate4App(出库单号，提单号)
		ArrayList<AppTransport> dataobject = (ArrayList<AppTransport>) dg.getEvaluate4App(ckNo, ladingBillNo);
		if(CollectionUtils.isNotEmpty(dataobject)){
			return dataobject.get(0);
		}
		return null;
	}
}
