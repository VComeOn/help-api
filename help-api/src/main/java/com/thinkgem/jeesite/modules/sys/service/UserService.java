package com.thinkgem.jeesite.modules.sys.service;

import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.sys.dao.UserDao;
import com.thinkgem.jeesite.modules.sys.entity.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Description:
 * @Date: 2018/1/22
 * @Author: wcf
 */
@Service
@Transactional(readOnly = true)
public class UserService extends CrudService<UserDao, User> {
    @Resource
    private UserDao dao;

    /**
     * @description 通过帐号或者手机号查询用户
     * @param
     * @author wcf
     * @date 2018/1/23
     * @return
     */
    public User getByPhoneOrAccount(String account){
        return dao.getByPhoneOrAccount(account);
    }

    /**
     * @description 通过手机号查询用户
     * @param
     * @author wcf
     * @date 2018/1/23
     * @return
     */
    public User getByPhone(String phone){
        return dao.getByPhone(phone);
    }

    /**
     * 更新用户密码
     * @param user
     * @return
     */
    @Transactional(readOnly = false)
    public int updatePasswordById(User user){
        return dao.updatePasswordById(user);
    }

    /**
     * 更新登录信息，如：登录IP、登录时间
     * @param user
     * @return
     */
    @Transactional(readOnly = false)
    public int updateLoginInfo(User user){
        return dao.updateLoginInfo(user);
    }

    /**
     * 更新用户信息
     * @param user
     * @return
     */
    @Transactional(readOnly = false)
    public int updateUserInfo(User user){
        return dao.updateUserInfo(user);
    }

    /**
     * 更新用户推送方式
     * @param user
     * @return
     */
    @Transactional(readOnly = false)
    public int updatePushType(User user){
        return dao.updatePushType(user);
    }

    /**
     * 获取公司所有管理员
     * @param companyId
     * @return
     */
    public List<User> getAllManager(Integer companyId, String department){
        return dao.getAllManager(companyId, department);
    }
}
