package com.thinkgem.jeesite.modules.utils;

import com.thinkgem.jeesite.modules.base.dao.BaseDriverDao;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * redis云存储工具类
 * @author chunqiu
 *
 */
@Component
public class RedisUtils {
	
	public static final String ACCESS_TOKEN = "TOKEN_WL_";//客户端请求服务器时携带的token参数
	public static final String REFRESH_TOKEN = "REFRESH_WL_";//客户端用户刷新token的参数
	public static final String PHONE_VALID_CODE = "PHONE_VALID_CODE_";//客户端短信验证码
	public static final String ADMIN_TOKEN = "ADMIN_TOKEN_";		//后台管理人员的token参数
	public static final String ADMIN_REFRESH = "ADMIN_REFRESH_";	//后台管理人员的刷新token的参数

	public static final String WAIT_DELIVERY_TRUCK = "WAIT_DELIVERY_TRUCK";		//即将出发车辆集合，格式：车牌号,运单号
	public static final String WILL_ARRIVE_TRUCK = "WILL_ARRIVE_TRUCK";			//即将到达车辆集合

	private RedisUtils(){}
	

	@Resource
	private RedisTemplate<String, String> redisTemplate;
	@Resource
	private RedisTemplate<String, Object> truckTemplate;
	@Resource
	private BaseDriverDao dao;
	
	/**
	 * @Description: 设置缓存，k-v形式
	 * @param key
	 * @param value
	 * @param timeout：单位：毫秒
	 * @author: wcf
	 * @date: 2017年7月6日
	 */
	public void setValue(String key, String value, long timeout ){
		redisTemplate.opsForValue().set(key, value, timeout , TimeUnit.MILLISECONDS);
	}

	public void setList(String key, Object value){
		truckTemplate.opsForList().rightPush(key, value);
	}

	public List<Object> getList(String key){
		return truckTemplate.opsForList().range(key, 0, -1);
	}

	public void removeList(String key, String value){
		//ount> 0：删除等于从头到尾移动的值的元素。
		//count <0：删除等于从尾到头移动的值的元素。
		//count = 0：删除等于value的所有元素。
		truckTemplate.opsForList().remove(key, 0, value);
	}

	/**
	 * @Description: 设置access_token的缓存
	 * @param userId
	 * @param value
	 * @param timeout
	 * @author: wcf
	 * @date: 2017年7月6日
	 */
	public void setToken(Integer userId, String value, long timeout){
		setValue(ACCESS_TOKEN + userId, value, timeout);
	}
	/**
	 * @Description: 设置refresh_token缓存，缓存时间固定为7天
	 * @param userId
	 * @param value
	 * @author: wcf
	 * @date: 2017年7月6日
	 */
	public void setRefreshToken(Integer userId, String value){
		setValue(REFRESH_TOKEN + userId, value, 604800000);
	}
	/**
	 * @Description: 获取缓存，通过key获取value值
	 * @param key
	 * @return
	 * @author: wcf
	 * @date: 2017年7月6日
	 */
	public String getValue(String key){
		return redisTemplate.opsForValue().get(key);
	}
	/**
	 * @Description: 获取redis中的access_token信息
	 * @param userId
	 * @return
	 * @author: wcf
	 * @date: 2017年7月6日
	 */
	public String getToken(Integer userId){
		return getValue(ACCESS_TOKEN + userId);
	}
	/**
	 * @Description: 获取redis中的refresh_token信息
	 * @param userId
	 * @return
	 * @author: wcf
	 * @date: 2017年7月6日
	 */
	public String getRefreshToken(Integer userId){
		return getValue(REFRESH_TOKEN + userId);
	}
	/**
	 * @Description: 删除缓存
	 * @param key
	 * @author: wcf
	 * @date: 2017年7月6日
	 */
	public void delete(String key){
		redisTemplate.delete(key);
	}
	
	 
	 /**    
	  * @description 设置后台管理人员的redis token缓存
	  * @param 
	  * @author wcf
	  * @date 2018/1/23 
	  * @return   
	  */  
	public void setAdminToken(Integer adminId, String value, long timeout){
		setValue(ADMIN_TOKEN + adminId, value, timeout);
	}
	
	 
	 /**    
	  * @description 获取后台管理人员的token缓存
	  * @param 
	  * @author wcf
	  * @date 2018/1/23 
	  * @return   
	  */  
	public String getAdminToken(Integer adminId) {
		return getValue(ADMIN_TOKEN + adminId);
	}

	 
	 /**    
	  * @description 设置后台管理人员的refresh token，时间默认为7天
	  * @param 
	  * @author wcf
	  * @date 2018/1/23 
	  * @return   
	  */  
	public void setAdminRefresh(Integer adminId, String value){
		setValue(ADMIN_REFRESH + adminId, value, 604800000);
	}

	 
	 /**    
	  * @description 获取后台管理人员的refhres token
	  * @param
	  * @author wcf
	  * @date 2018/1/23 
	  * @return   
	  */  
	public String getAdminRefresh(Integer adminId) {
		return getValue(ADMIN_REFRESH + adminId);
	}
}
