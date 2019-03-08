package com.thinkgem.jeesite.modules.api;

import com.thinkgem.jeesite.common.oauth.entity.Result;
import com.thinkgem.jeesite.common.oauth.entity.ResultStatusCode;
import com.thinkgem.jeesite.common.vo.GridParam;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.message.entity.DriverMessage;
import com.thinkgem.jeesite.modules.message.service.DriverMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @Description: 消息模块api接口
 * @Date: 2018/1/25
 * @Author: wcf
 */
@RestController
@RequestMapping("/api/message")
public class MessageController extends BaseController{
    @Autowired
    private DriverMessageService driverMessageService;

    /**
     * 获取消息列表
     * @param param
     * @param type
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping("getMessageList")
    public Result getMessageList(@Validated GridParam param, @RequestParam(required = true) Integer type, HttpServletRequest request) throws Exception{
        if(type.intValue() != 0 && type.intValue() != 1){
            throw  new ServletRequestBindingException("type的取值范围为0-1");
        }
        DriverMessage message = new DriverMessage();
        message.setDriverId(getDriverId(request));
        message.setType(type);
        return new Result(ResultStatusCode.OK, driverMessageService.list(message, param));
    }

    /**
     * 设置消息已读
     * @param id
     * @return
     */
    @RequestMapping("readMessage")
    public Result readMessage(@RequestParam(required = true) Integer id){
        driverMessageService.setRead(id);
        return new Result(ResultStatusCode.OK);
    }
}
