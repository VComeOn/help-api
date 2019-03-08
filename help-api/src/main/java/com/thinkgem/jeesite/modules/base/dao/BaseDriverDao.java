/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.base.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.adminApi.Vo.DriverDetailVo;
import com.thinkgem.jeesite.modules.adminApi.Vo.DriverVo;
import com.thinkgem.jeesite.modules.base.entity.BaseDriver;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 司机信息DAO接口
 * @author wcf
 * @version 2018-01-02
 */
@MyBatisDao
public interface BaseDriverDao extends CrudDao<BaseDriver> {

    /**
     * 通过手机号获取司机信息
     * @param phone
     * @return
     */
    BaseDriver getByPhone(String phone);

    /**
     * 解绑车辆
     * @param driverId
     * @return
     */
    int unbindCar(Integer driverId);

    /**
     * app管理端，获取司机状态列表
     * @param vo
     * @return
     */
    public List<DriverVo> getDriverStatusList(DriverVo vo);

    /**
     * 获取司机状态详情
     * @param deliveryBillNo
     * @param driverId
     * @return
     */
    public DriverDetailVo getDriverDetail(@Param("deliveryBillNo") String deliveryBillNo, @Param("driverId") Integer driverId);

    /**
     * 绑定或者解绑车辆
     * @param driver
     * @return
     */
    public int bindOrUnbindCar(BaseDriver driver);

    /**
     * 获取需要审核的司机列表
     * @return
     */
    public List<BaseDriver> finDriverExamine(BaseDriver baseDriver);

    /**
     * 改变司机驳回通过状态
     * @param status
     * @param id
     * @return
     */
    public int updateStatus(@Param("status") Integer status,@Param("id") Integer id);

    /**
     * 绑定车辆
     * @param driver
     * @return
     */
    public int updateDuty(BaseDriver driver);
}