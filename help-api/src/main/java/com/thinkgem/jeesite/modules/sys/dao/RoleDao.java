/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sys.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.sys.entity.Role;
import org.apache.ibatis.annotations.Param;

/**
 * 角色DAO接口
 * @author ThinkGem
 * @version 2013-12-05
 */
@MyBatisDao
public interface RoleDao extends CrudDao<Role> {

	public Role getByName(Role role);
	
	public Role getByEnname(Role role);

	/**
	 * 维护角色与菜单权限关系
	 * @param role
	 * @return
	 */
	public int deleteRoleMenu(Role role);

	public int insertRoleMenu(Role role);
	
	/**
	 * 维护角色与公司部门关系
	 * @param role
	 * @return
	 */
	public int deleteRoleOffice(Role role);

	public int insertRoleOffice(Role role);
	
	/**
	 * @Description: 根据角色Id和菜单Id删除角色菜单关系	
	 * @param roleId
	 * @param delMenus
	 * @return
	 * @author wcf
	 * @date 2016年9月21日
	 */
	public int delRoleMenu(@Param("roleId") Integer roleId,@Param("delMenus") String delMenus);
	
	/**
	 * @Description: 检测角色名是否重复	
	 * @param role
	 * @return
	 * @author wcf
	 * @date 2017年1月17日
	 */
	public int checkRoleName(Role role);
}
