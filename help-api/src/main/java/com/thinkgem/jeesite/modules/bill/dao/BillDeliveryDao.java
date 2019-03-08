/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.bill.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.bill.entity.BillDelivery;
import com.thinkgem.jeesite.modules.bill.entity.BillLading;
import com.thinkgem.jeesite.modules.vo.LadingBillVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 运单信息DAO接口
 * @author wcf
 * @version 2018-01-15
 */
@MyBatisDao
public interface BillDeliveryDao extends CrudDao<BillDelivery> {

    /**
     * 更新运单编号
     * @param delivery
     * @return
     */
    int updateNo(BillDelivery delivery);

     /**    
      * @description  根据运单编号获取运单基础信息
      * @param 
      * @author wcf
      * @date 2018/1/26 
      * @return   
      */  
    public BillDelivery getBaseInfoByDeliveryNo(String deliveryNo);

    public BillDelivery getInfoByDeliveryNo(String deliveryNo);

    /**
     * 根据运单编号获取运单详细信息
     * @param deliveryNo
     * @return
     */
    public BillDelivery getByDeliveryNo(String deliveryNo);

    /**
     * @description  根据出库编号获取提单和出库单信息
     * @param
     * @author wcf
     * @date 2018/1/26
     * @return
     */
    public BillLading getByOutboundNo(String outboundBillNo);
     
     /**    
      * @description 获取司机未完成的运单数
      * @param 
      * @author wcf
      * @date 2018/2/2 
      * @return   
      */  
    public int countOnDeliveryBill(Integer driverId);
    
     
     /**    
      * @description 根据司机Id获取运单的列表信息
      * @param 
      * @author wcf
      * @date 2018/2/6 
      * @return   
      */  
    public List<LadingBillVo> getDeliveryBillByDeriverId(BillDelivery billDelivery);


    /**
     * 获取执勤签到记录
     * @param driverId
     * @param month
     * @return
     */
    public List<Map> getSignDays(@Param("driverId") Integer driverId, @Param("month") String month);

    /**
     * 获取城市列表
     * @return
     */
    public List<Map> getCityList();

    /**
     * 获取运单基础信息，以及最后一次开始运输的时间
     * @param delivery
     * @return
     */
    public List<BillDelivery> getDeliveryAndLastRunTime(BillDelivery delivery);

    /**
     * 通过提单号获取运单数量
     * @param ladingBillNo 提单号
     * @param isBalance 是否结算
     * @return
     */
    public int countByLading(@Param("ladingBillNo") String ladingBillNo, @Param("isBalance") Integer isBalance);

    /**
     * 获取所有客户未评价的列表
     * @return
     */
    public List<BillDelivery> getNotCustomerCommentList();


    /**
     * 获取累计司机提货量
     * @param ladingBillNo
     * @return
     */
    public double getTotalLadingQuantity(String ladingBillNo);

    /**
     * 获取是否有运单正在使用该车辆
     * @param plateNumber
     * @param id
     * @return
     */
    public int countByPlateNumber(@Param("plateNumber") String plateNumber, @Param("id") Integer id);


    //****************************************二期接口*****************************************

    /**
     * 通过司机车辆获取运输任务
     * @param delivery
     * @return
     */
    public  List<BillDelivery> getDeliveryByDriverAndCar(BillDelivery delivery);

    /**
     * 通过提单编号获取所有运单信息
     * @param ladingBillNo
     * @return
     */
    public List<BillDelivery> getAllByLadingNo(String ladingBillNo);

    /**
     * 通过运输委托单号司机车牌获取运输委托单
     * @param ladingBillNo
     * @param driverId
     * @param plateNumber
     * @return
     */
    public List<BillDelivery> getByLadingNoDriverCar(@Param("ladingBillNo") String ladingBillNo,@Param("driverId") Integer driverId, @Param("plateNumber") String plateNumber);

    /**
     * 通过运输委托单号司机车牌获取已派发车辆任务
     * @param ladingBillNo
     * @param driverId
     * @param plateNumber
     * @return
     */
    public List<BillDelivery> getDriverHaveStatus(@Param("ladingBillNo") String ladingBillNo,@Param("driverId") Integer driverId, @Param("plateNumber") String plateNumber);

    /**
     * 通过运输委托单号司机车牌获取已接单车辆任务
     * @param ladingBillNo
     * @param driverId
     * @param plateNumber
     * @return
     */
    public List<BillDelivery> getDriverHaveStatus3(@Param("ladingBillNo") String ladingBillNo,@Param("driverId") Integer driverId, @Param("plateNumber") String plateNumber);

    /**
     * 通过运输委托单号司机车牌获取已完成车辆任务
     * @param ladingBillNo
     * @param driverId
     * @param plateNumber
     * @return
     */
    public List<BillDelivery> getFinishByLadingNoDriverCar(@Param("ladingBillNo") String ladingBillNo,@Param("driverId") Integer driverId, @Param("plateNumber") String plateNumber);

    /**
     * @description 首页获取司机所有运单
     * @param
     * @author qj
     * @date 2018/10/07
     * @return
     */
    public List<BillDelivery> getAllocatOrTransitList(BillDelivery billDelivery);

    /**
     * @description 首页获取司机未完成运单
     * @param
     * @author qj
     * @date 2018/10/07
     * @return
     */
    public List<BillDelivery> getHomeTransitList(BillDelivery billDelivery);

    /**
     *通过车牌号、司机、运输委托单号、状态判断是否可以绑定运输委托单
     * @param billDelivery
     * @return
     */
    public List<BillDelivery> getDeliveryByDriverAndCarAndLadingNo(BillDelivery billDelivery);

    /**
     * 获取运单详情以及运输委托单任务、评论
     * @param id
     * @return
     */
    public BillDelivery getDetailById(Integer id);

    /**
     * 获取需要审核的任务列表
     * @param billDelivery
     * @return
     */
    public List<BillDelivery> findBillDeliveryExamine(BillDelivery billDelivery);

    /**
     * 改变车辆驳回通过状态
     * @param status
     * @param id
     * @return
     */
    public int updateStatus(@Param("status") Integer status,@Param("id") Integer id);

    /**
     * 获取司机
     * @param driverId
     * @return
     */
    public BillDelivery getLastDelivery(Integer driverId);
}