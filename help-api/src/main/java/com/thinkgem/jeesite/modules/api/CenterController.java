package com.thinkgem.jeesite.modules.api;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.oauth.entity.Result;
import com.thinkgem.jeesite.common.oauth.entity.ResultStatusCode;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.vo.GridParam;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.annotation.IgnoreSecurity;
import com.thinkgem.jeesite.modules.base.entity.BaseCar;
import com.thinkgem.jeesite.modules.base.entity.BaseDriver;
import com.thinkgem.jeesite.modules.base.entity.SysAbout;
import com.thinkgem.jeesite.modules.base.entity.SysHelp;
import com.thinkgem.jeesite.modules.base.service.*;
import com.thinkgem.jeesite.modules.bill.entity.BillDelivery;
import com.thinkgem.jeesite.modules.bill.service.BillDeliveryService;
import com.thinkgem.jeesite.modules.driver.entity.DriverFaultRecord;
import com.thinkgem.jeesite.modules.driver.entity.UserFeedback;
import com.thinkgem.jeesite.modules.driver.service.DriverFaultRecordService;
import com.thinkgem.jeesite.modules.driver.service.UserFeedbackService;
import com.thinkgem.jeesite.modules.point.entity.PointExchangeRecord;
import com.thinkgem.jeesite.modules.point.entity.PointMall;
import com.thinkgem.jeesite.modules.point.entity.PointRecord;
import com.thinkgem.jeesite.modules.point.service.PointExchangeRecordService;
import com.thinkgem.jeesite.modules.point.service.PointMallService;
import com.thinkgem.jeesite.modules.point.service.PointRecordService;
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
    private SysHelpService helpService;
    @Autowired
    private UserFeedbackService feedbackService;
    @Autowired
    private PointMallService mallService;
    @Autowired
    private PointRecordService recordService;
    @Autowired
    private PointExchangeRecordService exchangeRecordService;
    @Autowired
    private DriverFaultRecordService driverFaultRecordService;
    @Autowired
    private BillDeliveryService deliveryService;
    @Autowired
    private SysAboutService sysAboutService;
    @Autowired
    private BaseCarService baseCarService;
    @Autowired
    private BaseDriverService baseDriverService;
    @Autowired
    private AppVersionService appVersionService;

     // ****************************************** 二期接口 **************************************************************
     /**
      * 获取帮助列表
      * @param param
      * @return
      */
     @RequestMapping("getHelpList")
     public Result getHelpList(@Valid GridParam param){
         return new Result(ResultStatusCode.OK, helpService.list(new SysHelp(), param));
     }


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
      * @description 获取积分兑换商品列表
      * @param
      * @author wcf
      * @date 2018/1/11
      * @return
      */
     @RequestMapping("getMallGoodsList")
     public Result getMallGoodsList(@Valid GridParam param){
         return new Result(ResultStatusCode.OK, mallService.list(new PointMall(), param));
     }

     /**
      * @description 获取积分记录列表
      * @param
      * @author wcf
      * @date 2018/1/11
      * @return
      */
     @RequestMapping("getPointRecordList")
     public Result getPointRecordList(@Valid GridParam param, HttpServletRequest request){
         PointRecord record = new PointRecord();
         record.setDriverId(getDriverId(request));
         return new Result(ResultStatusCode.OK, recordService.list(record, param));
     }

     /**
      * @description 获取积分兑换商品记录
      * @param
      * @author wcf
      * @date 2018/1/11
      * @return
      */
     @RequestMapping("getExchangeRecordList")
     public Result getExchangeRecordList(@Valid GridParam param, HttpServletRequest request){
         PointExchangeRecord record = new PointExchangeRecord();
         record.setDriverId(getDriverId(request));
         record.setStatus(PointExchangeRecord.STATUS_AGREE);
         return new Result(ResultStatusCode.OK, exchangeRecordService.list(record, param));
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
      * 获取充值卡详情
      * @param mallId
      * @return
      */
     @RequestMapping("getMallDetail")
     public Result getMallDetail(@RequestParam(required = true) Integer mallId, HttpServletResponse response){

         PointMallVo vo = new PointMallVo();
         BeanUtils.copyProperties(mallService.get(mallId), vo);

         if(StringUtils.isNotBlank(vo.getIntroduce())){
             vo.setIntroduce(vo.getIntroduce().replace("<img src=\"", "<img src=\"" + Global.baseUrl + ""));
         }

         return new Result(ResultStatusCode.OK, vo);
     }

     /**
      * 司机申请积分兑换充值卡
      * @param mallId
      * @param request
      * @return
      */
     @RequestMapping("exchangeMall")
     public Result exchangeMall(@RequestParam(required = true) Integer mallId, HttpServletRequest request, HttpServletResponse response){

         return exchangeRecordService.exchange(mallId, getDriverId(request));
     }

     /**
      * 获取积分兑换详情
      * @param id
      * @param request
      * @return
      */
     @RequestMapping("getExchangeDetail")
     public Result getExchangeDetail(@RequestParam(required = true) Integer id, HttpServletRequest request, HttpServletResponse response){

         PointExchangeRecordVo vo = exchangeRecordService.getDetail(id);
         if(vo == null || !vo.getDriverId().equals(getDriverId(request))){
             return new Result(Global.ERROR_CODE, "数据错误");
         }

         if(StringUtils.isNotBlank(vo.getVo().getIntroduce())){
             vo.getVo().setIntroduce(vo.getVo().getIntroduce().replace("<img src=\"", "<img src=\"" + Global.baseUrl + ""));
         }

         return new Result(ResultStatusCode.OK, vo);
     }

     /**
      * 关于我们
      * @param request
      * @param response
      * @return
      */
     @RequestMapping("getAbout")
     @IgnoreSecurity
     public Result getAbout(HttpServletRequest request, HttpServletResponse response){

         return new Result(ResultStatusCode.OK, sysAboutService.findAllList(new SysAbout()).get(0));
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

     /**
      * @description 个人中心获取车辆列表
      * @param
      * @author wcf
      * @date 2018/1/26
      * @return
      */
     @RequestMapping("/getCarList")
     public Result getCarList(@Validated GridParam param, HttpServletRequest request){
         BaseDriver driver = baseDriverService.get(getDriverId(request));
         BaseCar car = new BaseCar();
         car.setCompanyId(driver.getCompanyId());
         return new Result(ResultStatusCode.OK, baseCarService.getUseCarList(car, param));
     }

     /**
      * @description 新增车辆
      * @param
      * @author wcf
      * @date 2018/1/26
      * @return
      */
     @RequestMapping("/addCar")
     public Result addCar(@RequestParam(required = true)String plateNumber,Integer companyId,@RequestParam(required = true)String capacity){


         BaseCar car=baseCarService.getCarByPlateNumber(plateNumber);
        if(car == null){
            BaseCar carInsert = new BaseCar();
            carInsert.setStatus(0);
            carInsert.setPlateNumber(plateNumber.replaceAll("\\s*",""));
            carInsert.setCompanyId(companyId);
            carInsert.setCapacity(capacity);
            baseCarService.save(carInsert);
        }else{
            return new Result(ResultStatusCode.HAVE_PLATENUMBER);
        }
         return new Result(ResultStatusCode.OK);
     }


     /**
      * @description 获取APP版本
      * @param
      * @author wcf
      * @date 2018/1/11
      * @return
      */
     @RequestMapping("getVersion")
     public Result getVersion(){
         return new Result(ResultStatusCode.OK, appVersionService.get(1));
     }
}
