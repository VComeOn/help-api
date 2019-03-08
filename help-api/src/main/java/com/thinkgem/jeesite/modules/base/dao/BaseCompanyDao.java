/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.base.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.base.entity.BaseCompany;

/**
 * 公司信息DAO接口
 * @author wcf
 * @version 2017-12-28
 */
@MyBatisDao
public interface BaseCompanyDao extends CrudDao<BaseCompany> {

    /**
     * 通过公司名查找公司
     * @param name
     * @return
     */
    public BaseCompany getByName(String name);

    /**
     * 通过车队公司机构代码查找公司
     * @param baseCompanyCode
     * @return
     */
    public BaseCompany getByCode(String baseCompanyCode);

    /**
     * 通过车队公司组织机构代码查找公司
     * @param organizationCode
     * @return
     */
    public BaseCompany getByOrganizationCode(String organizationCode);
}