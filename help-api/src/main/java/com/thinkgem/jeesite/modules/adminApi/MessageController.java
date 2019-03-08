package com.thinkgem.jeesite.modules.adminApi;

import com.thinkgem.jeesite.common.oauth.entity.Result;
import com.thinkgem.jeesite.common.oauth.entity.ResultStatusCode;
import com.thinkgem.jeesite.common.vo.GridParam;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.message.entity.UserMessage;
import com.thinkgem.jeesite.modules.message.service.UserMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @Description:
 * @Date: 2018/3/7
 * @Author: wcf
 */
@RestController("adminMessageController")
@RequestMapping("/admin/api/message")
public class MessageController extends BaseController{
    @Autowired
    private UserMessageService service;

    /**
     * 获取管理员消息列表
     * @param param
     * @param request
     * @return
     */
    @RequestMapping("getMessageList")
    public Result getMessgeList(@Validated GridParam param, HttpServletRequest request){
        UserMessage message = new UserMessage();
        message.setUserId(getAdminId(request));

        return new Result(ResultStatusCode.OK, service.list(message, param));
    }

    /**
     * 设置消息已读
     * @param id
     * @return
     */
    @RequestMapping("readMessage")
    public Result readMessage(@RequestParam(required = true) Integer id){
        service.setRead(id);
        return new Result(ResultStatusCode.OK);
    }
}
