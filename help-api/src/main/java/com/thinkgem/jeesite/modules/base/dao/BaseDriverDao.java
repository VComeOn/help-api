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

    public BaseDriver getBaseDriverByPlateNumber(String plateNumber);


}