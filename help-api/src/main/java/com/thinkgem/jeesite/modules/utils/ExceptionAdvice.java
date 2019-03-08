package com.thinkgem.jeesite.modules.utils;

import com.thinkgem.jeesite.common.oauth.entity.Result;
import com.thinkgem.jeesite.common.oauth.entity.ResultStatusCode;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.exception.TokenErrorException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.validation.ConstraintViolationException;

/**
 * @Description:全局异常处理类
 * @Date: 2018/1/3
 * @Author: wcf
 */
@ControllerAdvice
@ResponseBody
public class ExceptionAdvice extends BaseController{

     /**
      * @description token失效
      * @param
      * @author wcf
      * @date 2018/1/4
      * @return
      */
    @ExceptionHandler(TokenErrorException.class)
    public Result handleTokenErrorException(TokenErrorException e){
        logger.error("token失效", e);
        return new Result(ResultStatusCode.INVALID_TOKEN, null);
    }

    public Result handleSignErrorException(){
        return new Result(ResultStatusCode.INVALID_TOKEN, null);
    }

    /**
     * 400 - Bad Request
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({HttpMessageNotReadableException.class, MissingServletRequestParameterException.class, BindException.class, ServletRequestBindingException.class, MethodArgumentNotValidException.class, ConstraintViolationException.class})
    public Result handleHttpMessageNotReadableException(Exception e) {
        logger.error("参数解析失败", e);
        logger.error(e.getMessage());
        return new Result(ResultStatusCode.BAD_REQUEST.getCode(), e.getMessage());
    }

    /**
     * 405 - Method Not Allowed
     */
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public Result handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        logger.error("不支持当前请求方法", e);
        return new Result(ResultStatusCode.METHOD_NOT_ALLOWED, null);
    }

    /**
     * 500
     * @param e
     * @return
     */
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public Result handleException(Exception e) {
        logger.error("服务运行异常", e);
        return new Result(ResultStatusCode.SYSTEM_ERR, null);
    }
}
