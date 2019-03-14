package com.thinkgem.jeesite.modules.api;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.oauth.entity.Result;
import com.thinkgem.jeesite.common.oauth.entity.ResultStatusCode;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.vo.GridParam;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.annotation.IgnoreSecurity;
import com.thinkgem.jeesite.modules.base.entity.BaseDriver;
import com.thinkgem.jeesite.modules.base.service.*;
import com.thinkgem.jeesite.modules.bill.entity.BillDelivery;
import com.thinkgem.jeesite.modules.bill.service.BillDeliveryService;
import com.thinkgem.jeesite.modules.driver.entity.DriverFaultRecord;
import com.thinkgem.jeesite.modules.driver.entity.UserFeedback;
import com.thinkgem.jeesite.modules.driver.service.DriverFaultRecordService;
import com.thinkgem.jeesite.modules.driver.service.UserFeedbackService;
import com.thinkgem.jeesite.modules.vo.PointExchangeRecordVo;
import com.thinkgem.jeesite.modules.vo.PointMallVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;


 /**
  * @description 个人中心模块api接口
  * @param
  * @author wcf
  * @date 2018/1/25
  * @return
  */
@RestController
@RequestMapping("/api/center")
public class CenterController extends BaseController{
    @Autowired
    private UserFeedbackService feedbackService;
    @Autowired
    private DriverFaultRecordService driverFaultRecordService;
    @Autowired
    private BillDeliveryService deliveryService;
    @Autowired
    private BaseDriverService baseDriverService;

     // ****************************************** 二期接口 **************************************************************


     /**
      * @description 提交反馈
      * @param
      * @author wcf
      * @date 2018/1/11
      * @return
      */
     @RequestMapping("commitFeedBack")
     public Result commitFeedBack(HttpServletRequest request, @RequestParam(required = true) String content){
         if(StringUtils.isBlank(content)){
             return new Result(Global.ERROR_CODE, "请填写意见反馈内容");
         }
         if(content.length() > 200){
             return new Result(Global.ERROR_CODE, "意见反馈内容长度不得超过200字");
         }
         UserFeedback feedback = new UserFeedback();
         feedback.setContent(content);
         feedback.setDriverId(getDriverId(request));
         feedbackService.save(feedback);
         return new Result(ResultStatusCode.OK);
     }

     /**
      * @description 获取司机的故障记录
      * @param
      * @author wcf
      * @date 2018/1/23
      * @return
      */
     @RequestMapping("getFaultRecordList")
     public Result getFaultRecordList(@Valid GridParam param, HttpServletRequest request){
         DriverFaultRecord record = new DriverFaultRecord();
         record.setDriverId(getDriverId(request));
         return new Result(ResultStatusCode.OK, driverFaultRecordService.list(record, param));
     }

     /**
      * @description 获取司机的故障记录详情
      * @param
      * @author qj
      * @date 2018/10/09
      * @return
      */
     @RequestMapping("getFaultRecordDetail")
     public Result getFaultRecordDetail(Integer id, HttpServletRequest request){
         DriverFaultRecord record = driverFaultRecordService.get(id);

         return new Result(ResultStatusCode.OK,record);
     }

     /**
      * 获取司机的运单列表
      * @param param
      * @param request
      * @return
      */
     @RequestMapping("getDeliveryBillList")
     public Result getDeliveryBillList(@Validated GridParam param, HttpServletRequest request){
         return new Result(ResultStatusCode.OK, deliveryService.getDeliveryBillList(param, getDriverId(request)));
     }


     /**
      * @description 获取运单详情以及运输委托单任务、评论
      * @param
      * @author wcf
      * @date 2018/2/6
      * @return
      */
     @RequestMapping("getDeliveryBillDetail")
     public Result getDeliveryBillDetail(Integer id, HttpServletRequest request){
         BillDelivery delivery = deliveryService.getDetailById(id);
         if(delivery == null || !delivery.getDriverId().equals(getDriverId(request))){
             return new Result(Global.ERROR_CODE, "运单信息错误");
         }
         return new Result(ResultStatusCode.OK, delivery);
     }


     /**
      * 获取执勤签到记录
      * @param month
      * @param request
      * @return
      */
     @RequestMapping("getSignDays")
     public Result getSignDays(@RequestParam(required = true) String month, HttpServletRequest request){
         return new Result(ResultStatusCode.OK, deliveryService.getSignDays(getDriverId(request), month));
     }

}
