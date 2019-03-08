package com.thinkgem.jeesite.modules.intercepter;

import com.thinkgem.jeesite.common.oauth.entity.Audience;
import com.thinkgem.jeesite.common.oauth.utils.JwtHelper;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.annotation.IgnoreSecurity;
import com.thinkgem.jeesite.modules.exception.TokenErrorException;
import com.thinkgem.jeesite.modules.utils.RedisUtils;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 请求api服务器时，对accessToken进行拦截判断，有效则可以反问接口，否则返回错误
 * @author Win7
 *
 */
public class InterceptorAdminJWT extends HandlerInterceptorAdapter{
	
	@Autowired  
    private Audience audienceEntity;
	@Autowired
	private RedisUtils redisUtils;
	
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		// 若目标方法忽略了安全性检查，则直接调用目标方法
		if (handler.getClass().isAssignableFrom(HandlerMethod.class)) {
			//如果方法上有@IgnoreSecurity注解，则不需要进行token验证
			IgnoreSecurity ignoreSecurity = ((HandlerMethod) handler).getMethodAnnotation(IgnoreSecurity.class);
			if (ignoreSecurity != null)
				return true;
		}
		String accessToken = StringUtils.isNotEmpty(request.getParameter("token")) ? request.getParameter("token") : request.getHeader("token");

		if(StringUtils.isNotEmpty(accessToken)){
			String HeadStr = accessToken.substring(0, 12).toLowerCase();
			if(HeadStr.equals("admin_bearer")){
				accessToken = accessToken.substring(12);

				Claims claims = JwtHelper.parseJWT(accessToken, audienceEntity.getBase64Secret());
				//判断密钥是否相等，如果不等则认为时无效的token
				if(claims != null){
					Integer adminId = (Integer)claims.get("adminId");
					//token未失效，token需要和redis服务器中的储存的token值一样才有效
					if(claims.getAudience().equals(audienceEntity.getClientId()) && accessToken.equals(redisUtils.getAdminToken(adminId))){
						request.setAttribute("adminId", adminId);
						return true;
					}
				}
			}
		}

		throw new TokenErrorException();
	}

	
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {

		super.postHandle(request, response, handler, modelAndView);
	}

	
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {

		super.afterCompletion(request, response, handler, ex);
	}

	
	public void afterConcurrentHandlingStarted(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {

		super.afterConcurrentHandlingStarted(request, response, handler);
	}
}
