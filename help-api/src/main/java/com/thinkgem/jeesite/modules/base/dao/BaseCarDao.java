/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.base.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.base.entity.BaseCar;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 车辆信息DAO接口
 * @author wcf
 * @version 2017-12-28
 */
@MyBatisDao
public interface BaseCarDao extends CrudDao<BaseCar> {
    
     
     /**    
      * @description 获取不在使用中的车辆列表
      * @param 
      * @author wcf
      * @date 2018/1/26 
      * @return   
      */  
    public List<BaseCar> getNotUseCarList(BaseCar car);

    /**
     * @description 获取车辆列表
     * @param
     * @author wcf
     * @date 2018/1/26
     * @return
     */
    public List<BaseCar> getUseCarList(BaseCar car);


     /**
      * @description 通过车牌号获取车辆信息
      * @param
      * @author wcf
      * @date 2018/1/26
      * @return
      */
    public BaseCar getByPlateNumber(@Param("companyId") Integer companyId, @Param("plateNumber") String plateNumber);

    public BaseCar getCarByPlateNumber(@Param("plateNumber") String plateNumber);

    /**
     * 获取需要审核的车辆
     * @return
     */
    public List<BaseCar> findCarExamine(BaseCar baseCar);

    /**
     * 改变车辆驳回通过状态
     * @param status
     * @param id
     * @return
     */
    public int updateStatus(@Param("status") Integer status,@Param("id") Integer id);
}