package com.thinkgem.jeesite.modules.api;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.oauth.entity.Result;
import com.thinkgem.jeesite.common.oauth.entity.ResultStatusCode;
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.vo.GridParam;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.annotation.IgnoreSecurity;
import com.thinkgem.jeesite.modules.base.entity.BaseCar;
import com.thinkgem.jeesite.modules.base.entity.BaseDriver;
import com.thinkgem.jeesite.modules.base.service.BaseCarService;
import com.thinkgem.jeesite.modules.base.service.BaseDriverService;
import com.thinkgem.jeesite.modules.bill.entity.BillConstant;
import com.thinkgem.jeesite.modules.bill.entity.BillDelivery;
import com.thinkgem.jeesite.modules.bill.entity.BillDeliverySign;
import com.thinkgem.jeesite.modules.bill.entity.BillLading;
import com.thinkgem.jeesite.modules.bill.service.BillConstantService;
import com.thinkgem.jeesite.modules.bill.service.BillDeliveryService;
import com.thinkgem.jeesite.modules.bill.service.BillDeliverySignService;
import com.thinkgem.jeesite.modules.bill.service.BillLadingService;
import com.thinkgem.jeesite.modules.driver.entity.DriverFaultRecord;
import com.thinkgem.jeesite.modules.driver.service.DriverFaultRecordService;
import com.thinkgem.jeesite.modules.point.service.PointLevelRewardService;
import com.thinkgem.jeesite.modules.point.service.PointSetService;
import com.thinkgem.jeesite.modules.sys.paramVo.DeliverySignVo;
import com.thinkgem.jeesite.modules.sys.paramVo.DeliveryVo;
import com.thinkgem.jeesite.modules.sys.utils.DictUtils;
import com.thinkgem.jeesite.modules.thirdApi.customer.demo.Interface4app;
import com.thinkgem.jeesite.modules.utils.BaiDuMapUtils;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Description:订单相关api接口
 * @Date: 2018/1/25
 * @Author: wcf
 */
@RestController
@RequestMapping("/api/bill")
public class BillController extends BaseController{
    @Autowired
    private BillDeliveryService billDeliveryService;
    @Autowired
    private BaseDriverService baseDriverService;
    @Autowired
    private BaseCarService baseCarService;
    @Autowired
    private DriverFaultRecordService driverFaultRecordService;
    @Autowired
    private BillLadingService billLadingService;
    @Autowired
    private BillConstantService constantService;
    @Autowired
    private PointSetService pointSetService;
    @Autowired
    private PointLevelRewardService pointLevelRewardService;
    @Autowired
    private BillDeliverySignService billDeliverySignService;


     /**    
      * @description 扫码获取提单或者出库单信息
      * @param 
      * @author wcf
      * @date 2018/2/2 
      * @return   
      */  
    @RequestMapping("scanLadingOrDelivery")
    public Result scanLadingOrDelivery(String billNo, HttpServletRequest request){
        BaseDriver driver = baseDriverService.get(getDriverId(request));

        if(billNo.contains("http")){
            //如果扫描的内容包含http，则认为扫描的是出库单信息
            Map<String, String> param = StringUtils.getUrlParam(billNo);
            if(param.containsKey("feedback.billno")){
                BillLading delivery = billDeliveryService.getByOutboundNo(param.get("feedback.billno"));
                if(delivery != null){
                    if(!driver.getCompanyId().equals(delivery.getDelivery().getCompanyId())){
                        return new Result(Global.ERROR_CODE, "您不是该公司的司机");
                    }
                    //如果在出库单中能够获取数据，则仓库类型为：内储库
                    return new Result(ResultStatusCode.OK, delivery);
                }else{
                    return new Result(Global.ERROR_CODE, "该出库单号不存在");
                }
            }
        }else {
            //如果不是http开头，则认为是扫描的提单
            //如果出库单中不存在，则认为是：外储库
            BillLading lading = billLadingService.getByLadingNo(billNo);
            if(lading != null){
                if(!lading.getCompanyId().equals(driver.getCompanyId())){
                    return new Result(Global.ERROR_CODE, "您不是该公司的司机");
                }
                if(lading.getStorehouseType().equals(BillLading.STORE_TYPE_INSIDE)){
                    return new Result(Global.ERROR_CODE, "内储库提单请直接扫出库单");
                }
                return new Result(ResultStatusCode.OK, lading);
            }
        }
        return new Result(Global.ERROR_CODE, "请扫描正确的条形码或者二维码");
    }

    /**
     * 外储库提单绑定运单和司机
     * @param ladingBillNo
     * @param request
     * @return
     */
    /*@RequestMapping("bindOutsideLading")
    public Result bindOutsideLading(String ladingBillNo, HttpServletRequest request){
        BillLading lading = billLadingService.getByLadingNo(ladingBillNo);
        BaseDriver driver = baseDriverService.get(getDriverId(request));
        Result result = validLading(lading, driver, null);
        if(result != null){
            return result;
        }
        //创建运单信息
        BillDelivery delivery = new BillDelivery();
        delivery.setDeliveryBillNo("CKXN-" + new SimpleDateFormat("yyyyMM" + "-").format(new Date()));
        delivery.setLadingBillNo(ladingBillNo);
        delivery.setSignDate(new Date());
        delivery.setStatus(BillDelivery.STATUS_ALLOCAT);
        delivery.setDriverId(driver.getId());
        delivery.setPlateNumber(driver.getPlateNumber());
        delivery.setCompanyId(driver.getCompanyId());
        delivery.setIsErrorStop(Global.BOOLEAN_NO);
        delivery.setIsCloseApp(Global.BOOLEAN_NO);
        delivery.setIsBalance(Global.BOOLEAN_NO);
        delivery.setIsAdminComment(Global.BOOLEAN_NO);
        delivery.setIsCustomerComment(Global.BOOLEAN_NO);

        billDeliveryService.saveDelivery(delivery);

        return new Result(ResultStatusCode.OK);
    }*/

    /**
     * 外储库运单司机签收
     * @param ladingBillNo
     * @param signQuantity
     * @param signPhoto
     * @return
     */
    @RequestMapping("outsideLadingSign")
    public Result outsideLadingSign(@RequestParam(required = true) String ladingBillNo, String deliveryBillNo,@RequestParam(required = true) Double signQuantity,@RequestParam(required = true) String signPhoto, HttpServletRequest request){
        /*BillDelivery delivery = billDeliveryService.getByDeliveryNo(deliveryBillNo);
        BaseDriver driver = baseDriverService.get(getDriverId(request));
        Result result = validLading(delivery.getBillLading(), driver, delivery);
        if(result != null){
            return result;
        }*/
        BillDelivery delivery;
        BillLading lading;

        BaseDriver driver = baseDriverService.get(getDriverId(request));

        if(StringUtils.isNotBlank(deliveryBillNo)){
            //存在运单号，则表示是后台分配的运单
            delivery = billDeliveryService.getByDeliveryNo(deliveryBillNo);
            Result result = validLading(delivery.getBillLading(), driver, delivery);
            if(result != null){
                return result;
            }
            lading = delivery.getBillLading();
        }else{
            lading = billLadingService.getByLadingNo(ladingBillNo);
            Result result = validLading(lading, driver, null);
            if(result != null){
                return result;
            }

            //创建运单信息
            delivery = new BillDelivery();
            delivery.setDeliveryBillNo("CKXN-" + new SimpleDateFormat("yyyyMM" + "-").format(new Date()));
            delivery.setLadingBillNo(ladingBillNo);
            delivery.setSignDate(new Date());
            delivery.setStatus(BillDelivery.STATUS_ALLOCAT);
            delivery.setDriverId(driver.getId());
            delivery.setPlateNumber(driver.getPlateNumber());
            delivery.setCompanyId(driver.getCompanyId());
            delivery.setIsErrorStop(Global.BOOLEAN_NO);
            delivery.setIsCloseApp(Global.BOOLEAN_NO);
            delivery.setIsBalance(Global.BOOLEAN_NO);
            delivery.setIsAdminComment(Global.BOOLEAN_NO);
            delivery.setIsCustomerComment(Global.BOOLEAN_NO);
        }
        //对于司机签收数与分配提货量进行校验
        BillConstant constant = constantService.get();
        if(constant != null){
            if (StringUtils.isNotEmpty(constant.getErrorLower()) && delivery.getWillQuantity() != null){
                if(signQuantity < delivery.getWillQuantity() * (1 - Double.parseDouble(constant.getErrorLower()) / 100)){
                    return new Result(Global.ERROR_CODE, "签收数量不得小于最低提货量");
                }
            }
            if (StringUtils.isNotEmpty(constant.getErrorUpper()) && delivery.getWillQuantity() != null){
                if(signQuantity > delivery.getWillQuantity() * (1 + Double.parseDouble(constant.getErrorUpper()) / 100)){
                    return new Result(Global.ERROR_CODE, "签收数量不得大于最高提货量");
                }
            }
        }

        delivery.setSignQuantity(signQuantity);
        //delivery.setSignPhoto(signPhoto);
        delivery.setSignDate(new Date());

        billDeliveryService.deliveryDriverSign(delivery, driver, lading);

        return new Result(ResultStatusCode.OK, delivery);
    }

     
     /**
      * @description 内储库提单绑定出库单
      * @param 
      * @author wcf
      * @date 2018/2/5 
      * @return   
      */  
    @RequestMapping("bindInsideLading")
    public Result bindInsideLading(@RequestParam(required = true) String outboundBillNo, HttpServletRequest request){
        BillLading lading = billDeliveryService.getByOutboundNo(outboundBillNo);
        BaseDriver driver = baseDriverService.get(getDriverId(request));
        BillDelivery delivery = lading.getDelivery();
        if(lading == null){
            return new Result(Global.ERROR_CODE, "未获得出库单信息");
        }
        if(lading.getDelivery().getDriverId() != null){
            return new Result(Global.ERROR_CODE, "该出库单已被绑定");
        }
        if(lading.getStatus().equals(BillLading.STATUS_FINISH)){
            return new Result(Global.ERROR_CODE, "该提单已完成");
        }
        if(driver.getIsOnDuty().equals(Global.BOOLEAN_NO) || StringUtils.isEmpty(driver.getPlateNumber())){
            //如果当前司机不在执勤或者没有绑定车辆，则无法绑定外储库提单
            return new Result(Global.ERROR_CODE, "请先绑定执勤车辆");
        }else if(StringUtils.isNotEmpty(delivery.getPlateNumber()) && !driver.getPlateNumber().equals(delivery.getPlateNumber())){
            //对于运单已经指定车辆的，进行判断
            return new Result(Global.ERROR_CODE, "您当前执勤的车辆与指定车辆不一致，请更换车辆为：" + delivery.getPlateNumber());
        }
        if(billDeliveryService.countOnDeliveryBill(driver.getId()) > 0){
            return new Result(Global.ERROR_CODE, "您还有正在执勤的运单");
        }


        delivery.setStatus(BillDelivery.STATUS_ALLOCAT);
        delivery.setDriverId(driver.getId());
        delivery.setPlateNumber(driver.getPlateNumber());
        //内储库的运单，绑定即直接发送推送消息
        billDeliveryService.deliveryDriverSign(delivery, driver, lading);

        return new Result(ResultStatusCode.OK, delivery);
    }

    /**
     * 验证提单信息
     * @param lading
     * @param driver
     * @return
     */
    public Result validLading(BillLading lading, BaseDriver driver, BillDelivery delivery){
        if(lading == null){
            return new Result(Global.ERROR_CODE, "未获得提单信息");
        }
        if(lading.getStatus().equals(BillLading.STATUS_FINISH)){
            return new Result(Global.ERROR_CODE, "该提单已完成");
        }
        if(lading.getStorehouseType().equals(BillLading.STORE_TYPE_INSIDE)){
            return new Result(Global.ERROR_CODE, "提单信息错误");
        }

        if(driver.getIsOnDuty().equals(Global.BOOLEAN_NO) || StringUtils.isEmpty(driver.getPlateNumber())){
            //如果当前司机不在执勤或者没有绑定车辆，则无法绑定外储库提单
            return new Result(Global.ERROR_CODE, "请先绑定执勤车辆");
        }
        if(delivery != null && delivery.getPlateNumber() != null && !delivery.getPlateNumber().equals(driver.getPlateNumber())){
            //对于运单已经指定车辆的，进行判断
            return new Result(Global.ERROR_CODE, "您当前执勤的车辆与指定车辆不一致，请更换车辆为：" + delivery.getPlateNumber());
        }
        if(billDeliveryService.countOnDeliveryBill(driver.getId()) > 0){
            return new Result(Global.ERROR_CODE, "您还有正在执勤的运单");
        }
        return null;
    }

    /**
     * 获取提单配置参数
     * @return
     */
    @RequestMapping("getConstant")
    public Result getConstant(){
        return new Result(ResultStatusCode.OK, constantService.get());
    }




//   *************************************************** 二期接口 ************************************************

    /**
     * @description 获取可选择的车辆列表
     * @param
     * @author wcf
     * @date 2018/1/26
     * @return
     */
    @RequestMapping("/getCarList")
    public Result getCarList(@Validated GridParam param, HttpServletRequest request, String plateNumber){
        BaseDriver driver = baseDriverService.get(getDriverId(request));
        BaseCar car = new BaseCar();
        car.setCompanyId(driver.getCompanyId());
        car.setPlateNumber(plateNumber);
        return new Result(ResultStatusCode.OK, baseCarService.getNotUseCarList(car, param));
    }

    /**
     * @description 绑定执勤车辆
     * @param
     * @author wcf
     * @date 2018/1/26
     * @return
     */
    @RequestMapping("/bindCar")
    public Result bindCar(@RequestParam(required = true) String plateNumber, HttpServletRequest request){
        BaseDriver driver = baseDriverService.get(getDriverId(request));
        BillDelivery billDelivery=new BillDelivery();
        billDelivery.setDriverId(getDriverId(request));
        billDelivery.setPlateNumber(driver.getPlateNumber());
        List<BillDelivery> billDeliveries=billDeliveryService.getDeliveryByDriverAndCar(billDelivery);
        if(!CollectionUtils.isEmpty(billDeliveries)){
            return new Result(Global.ERROR_CODE, "此车辆正在执行任务，无法重新绑定");
        }
        BaseCar car = baseCarService.getCarByPlateNumber(plateNumber);
        if(car == null){
            return new Result(Global.ERROR_CODE, "该车辆不存在");
        }

        BaseCar carParam = new BaseCar();
        carParam.setId(car.getId());

        List<BaseCar> carList = baseCarService.getNotUseCarList(carParam);
        if(CollectionUtils.isEmpty(carList)){
            return new Result(Global.ERROR_CODE, "该车辆已被使用");
        }
        //绑定车辆，并将司机状态修改为：执勤中
        driver.setPlateNumber(plateNumber);
        driver.setLastSignDate(new Date());
        driver.setIsOnDuty(Global.BOOLEAN_YES);
        baseDriverService.save(driver);
        return new Result(ResultStatusCode.OK);
    }


    /**
     * @description 移除绑定车辆
     * @param
     * @author wcf
     * @date 2018/1/26
     * @return
     */
    @RequestMapping("/removeCar")
    public Result removeCar(HttpServletRequest request){
        BaseDriver driver = baseDriverService.get(getDriverId(request));
        if(driver.getPlateNumber() == null){
            return new Result(Global.ERROR_CODE, "您还未绑定任何车辆");
        }
        BillDelivery billDelivery=new BillDelivery();
        billDelivery.setDriverId(getDriverId(request));
        billDelivery.setPlateNumber(driver.getPlateNumber());
        List<BillDelivery> billDeliveries=billDeliveryService.getDeliveryByDriverAndCar(billDelivery);
        if(!CollectionUtils.isEmpty(billDeliveries)){
            return new Result(Global.ERROR_CODE, "此车辆正在执行任务，无法解除绑定");
        }
        baseDriverService.unbindCar(getDriverId(request));
        return new Result(ResultStatusCode.OK);
    }

    /**
     * @description 判断司机是否认证
     * @param
     * @author wcf
     * @date 2018/1/26
     * @return
     */
    @RequestMapping("/ifAuthentication")
    public Result ifAuthentication(HttpServletRequest request){
        BaseDriver driver = baseDriverService.get(getDriverId(request));
        Integer flag=0;
        if(driver !=null && driver.getStatus() ==1 ){
            flag=1;
        }

        return new Result(ResultStatusCode.OK,flag);
    }

    /**
     * @description 通过提单密令获取运输委托单
     * @param
     * @author qj
     * @date 2018/10/08
     * @return
     */
    @RequestMapping("/getBillLadingBySecret")
    public Result getBillLadingBySecret(@RequestParam(required = true) String billSecret){
        BillLading billLading=billLadingService.getByBillSecret(billSecret);
        if(billLading == null){
            return new Result(Global.ERROR_CODE, "请输入正确的条形码或者二维码");
        }else if(billLading.getStatus() == BillLading.STATUS_FINISH){
            return new Result(Global.ERROR_CODE, "此运输委托单已完成");
        }else if(billLading.getStatus() == 2){
            return new Result(Global.ERROR_CODE, "此运输委托单已挂起");
        }
        return new Result(ResultStatusCode.OK, billLading);
    }

    /**
     * @description 判定是否可以绑定运输委托单运输任务
     * @param
     * @author qj
     * @date 2018/10/08
     * @return
     */
    @RequestMapping("bindBillLading")
    public Result bindBillLading(@RequestParam(required = true) String billLadingNo, HttpServletRequest request){
        BillLading lading = billLadingService.getByLadingNo(billLadingNo);
        BaseDriver driver = baseDriverService.get(getDriverId(request));
        if(lading == null){
            return new Result(Global.ERROR_CODE, "未获得运输委托单信息");
        }
        if(lading.getStatus().equals(BillLading.STATUS_FINISH)){
            return new Result(Global.ERROR_CODE, "该提单已完成");
        }
        if(lading.getStatus().equals(2)){
            return new Result(Global.ERROR_CODE, "该提单已挂起");
        }
        if(driver.getIsOnDuty().equals(Global.BOOLEAN_NO) || StringUtils.isEmpty(driver.getPlateNumber())){
            //如果当前司机不在执勤或者没有绑定车辆，则无法绑定外储库提单
            return new Result(Global.ERROR_CODE, "请先绑定执勤车辆");
        }
        if(billDeliveryService.countOnDeliveryBill(driver.getId()) > 3){
            //判断该司机未完成订单是否超过3单
            return new Result(Global.ERROR_CODE, "您未完成的运单任务已超3单，请先结束");
        }
        BillDelivery billDelivery=new BillDelivery();
        billDelivery.setDriverId(getDriverId(request));
        billDelivery.setPlateNumber(driver.getPlateNumber());
        billDelivery.setLadingBillNo(billLadingNo);
        if(!CollectionUtils.isEmpty(billDeliveryService.getDeliveryByDriverAndCarAndLadingNo(billDelivery))){
            //通过车牌号、司机、运输委托单号、状态判断是否可以绑定运输委托单
            return new Result(Global.ERROR_CODE, "此车辆已绑定过此运输委托单，请从首页查看");
        }
        return new Result(ResultStatusCode.OK, lading);
    }

    /**
     * @description 通过运输委托单号获取运输委托单下的运输任务
     * @param
     * @author qj
     * @date 2018/10/08
     * @return
     */
    @RequestMapping("getDeliveryByLadingNo")
    public Result getDeliveryByLadingNo(@RequestParam(required = true) String billLadingNo){
        List<BillDelivery> billDeliveries=billDeliveryService.getAllByLadingNo(billLadingNo);
        return new Result(ResultStatusCode.OK, billDeliveries);
    }

    /**
     * @description 确认接单
     * @param
     * @author qj
     * @date 2018/10/08
     * @return
     */
    @RequestMapping("comfirmBillDelivery")
    public Result comfirmBillDelivery(@RequestParam(required = true) String billLadingNo,@RequestParam(required = true) Double signQuantity,@RequestParam(required = true) String estimatedArrivalTime,HttpServletRequest request){
        BaseDriver driver = baseDriverService.get(getDriverId(request));
        BillLading lading = billLadingService.getByLadingNo(billLadingNo);
        if(driver.getIsOnDuty().equals(Global.BOOLEAN_NO) || StringUtils.isEmpty(driver.getPlateNumber())){
            //如果当前司机不在执勤或者没有绑定车辆，则无法绑定外储库提单
            return new Result(Global.ERROR_CODE, "请先绑定执勤车辆");
        }
        if(lading.getStatus().equals(BillLading.STATUS_FINISH)){
            return new Result(Global.ERROR_CODE, "该提单已完成");
        }
        if(lading.getStatus().equals(2)){
            return new Result(Global.ERROR_CODE, "该提单已挂起");
        }
        if(billDeliveryService.countOnDeliveryBill(driver.getId()) > 3){
            //判断该司机未完成订单是否超过3单
            return new Result(Global.ERROR_CODE, "您未完成的运单任务已超3单，请先结束");
        }
         //BillConstant constant = constantService.findAllList(new BillConstant()).get(0);
        /* if(constant != null){
            if(StringUtils.isNotEmpty(constant.getErrorUpper())){
                Double total = billDeliveryService.getTotalLadingQuantity(billLadingNo);
                if((total + signQuantity) >= lading.getTotalVolume() * (1 + Double.parseDouble(constant.getErrorUpper()) / 100)){
                    return new Result(Global.ERROR_CODE,"预计送货数量已超出运输委托单总量上限");
                }
            }
        }*/

        List<BillDelivery> billDeliveryParam=billDeliveryService.getDriverHaveStatus(billLadingNo,driver.getId(),driver.getPlateNumber());
        if(billDeliveryParam !=null && billDeliveryParam.size()>0){
            if(billDeliveryParam.get(0).getSignQuantity() == null ){
                billDeliveryParam.get(0).setSignQuantity(0.00);
            }
            billDeliveryParam.get(0).setSignQuantity(signQuantity+billDeliveryParam.get(0).getSignQuantity());
            billDeliveryParam.get(0).setEstimatedArrivalTime(estimatedArrivalTime);
            billDeliveryParam.get(0).setSignDate(new Date());
            billDeliveryParam.get(0).setStatus(BillDelivery.STATUS_TAKING);  //已接单
            billDeliveryService.save(billDeliveryParam.get(0));
            return new Result(ResultStatusCode.OK,billDeliveryService.getByDeliveryNo(billDeliveryParam.get(0).getDeliveryBillNo()));
        }else{
            List<BillDelivery> billDelivery2=billDeliveryService.getFinishByLadingNoDriverCar(billLadingNo,driver.getId(),driver.getPlateNumber());
            if(billDelivery2 !=null && billDelivery2.size()>0){
                billDelivery2.get(0).setSignQuantity(billDelivery2.get(0).getSignQuantity()+signQuantity);
                billDelivery2.get(0).setEstimatedArrivalTime(estimatedArrivalTime);
                billDelivery2.get(0).setSignDate(new Date());
                billDelivery2.get(0).setStatus(BillDelivery.STATUS_TAKING);
                billDelivery2.get(0).setIsBalance(Global.BOOLEAN_NO);//未结算
                billDelivery2.get(0).setIsCloseApp(Global.BOOLEAN_NO);//未关闭app
                billDeliveryService.save(billDelivery2.get(0));
                return new Result(ResultStatusCode.OK,billDeliveryService.getByDeliveryNo( billDelivery2.get(0).getDeliveryBillNo()));
            }
            List<BillDelivery> billDelivery=billDeliveryService.getByLadingNoDriverCar(billLadingNo,driver.getId(),driver.getPlateNumber());
            if(billDelivery !=null && billDelivery.size()>0){
                return new Result(Global.ERROR_CODE, "此车辆已存在于此运输委托单下，请先完成");
            }
            BillDelivery billDeliveryInsert=new BillDelivery();
            billDeliveryInsert.setLadingBillNo(billLadingNo);
            billDeliveryInsert.setSignQuantity(signQuantity);
            billDeliveryInsert.setSignDate(new Date());
            billDeliveryInsert.setStatus(BillDelivery.STATUS_UNALLOCAT);//未审核
            billDeliveryInsert.setDriverId(driver.getId());
            billDeliveryInsert.setPlateNumber(driver.getPlateNumber());
            billDeliveryInsert.setCompanyId(lading.getCompanyId());
            billDeliveryInsert.setCreateDate(new Date());
            billDeliveryInsert.setWillQuantity(signQuantity);
            billDeliveryInsert.setShipperName(lading.getCustomerName());
            billDeliveryInsert.setEstimatedArrivalTime(estimatedArrivalTime);
            billDeliveryInsert.setIsBalance(Global.BOOLEAN_NO);//未结算
            billDeliveryInsert.setIsCloseApp(Global.BOOLEAN_NO);//未关闭app
            billDeliveryInsert.setIsErrorStop(Global.BOOLEAN_NO);//未异常停靠
            billDeliveryInsert.setIsAdminComment(Global.BOOLEAN_NO);//未评论
            billDeliveryInsert.setIsCustomerComment(Global.BOOLEAN_NO);//客户未评论
            billDeliveryService.insertDelivery(billDeliveryInsert);
            List<BillDelivery> billDeliveryParam3=billDeliveryService.getDriverHaveStatus3(billLadingNo,driver.getId(),driver.getPlateNumber());
            if(CollectionUtils.isEmpty(billDeliveryParam3)){
                return new Result(Global.ERROR_CODE, "运单已提交");
            }else {
                return new Result(ResultStatusCode.OK,billDeliveryParam3.get(0));
            }

        }
    }


    /**
     * @description 运输任务开始运输
     * @param
     * @author wcf
     * @date 2018/1/29
     * @return
     */
    @RequestMapping("startDelivery")
    public Result startDelivery(@Validated DeliveryVo deliveryVo, HttpServletRequest request){
        BillDelivery delivery = billDeliveryService.getBaseInfoByDeliveryNo(deliveryVo.getDeliveryBillNo());
        //验证运单信息
        Result result = validDelivery(delivery, BillDelivery.STATUS_TAKING, null, request, baseDriverService.get(getDriverId(request)));
        if(result != null){
            return result;
        }

       /* BillLading lading = billLadingService.getByLadingNo(delivery.getLadingBillNo());
        if(lading.getDeliveryLat() == null || lading.getArriveLat() == null){
            return new Result(Global.ERROR_CODE, "您还未设置起点或者终点位置的坐标点，请进入管理后台进行设置");
        }
        if(lading.getStartRailRadius() != null){
            if(BaiDuMapUtils.getDistance(deliveryVo.getLng(), deliveryVo.getLat(), lading.getDeliveryLng(), lading.getDeliveryLat()) > lading.getStartRailRadius() * 1000){
                return new Result(Global.ERROR_CODE, "您的车辆未到达储存仓库范围，还不能进行操作");
            }
        }*/
        billDeliveryService.startDelivery(delivery);

        return new Result(ResultStatusCode.OK);
    }

    /**
     * @description 中途停车
     * @param
     * @author wcf
     * @date 2018/2/1
     * @return
     */
    @RequestMapping("stopOnWay")
    public Result stopOnWay(@RequestParam(required = true) String deliveryBillNo, HttpServletRequest request){
        BillDelivery delivery = billDeliveryService.getBaseInfoByDeliveryNo(deliveryBillNo);
        //验证运单信息
        Result result = validDelivery(delivery, BillDelivery.STATUS_TRANSIT, null, request, baseDriverService.get(getDriverId(request)));
        if(result != null){
            return result;
        }
        billDeliveryService.stopDelivery(delivery);
        return new Result(ResultStatusCode.OK);
    }

    /**
     * 继续运输
     * @param deliveryBillNo
     * @param request
     * @return
     */
    @RequestMapping("continueDelivery")
    public Result continueDelivery(@RequestParam(required = true) String deliveryBillNo, HttpServletRequest request){
        BillDelivery delivery = billDeliveryService.getBaseInfoByDeliveryNo(deliveryBillNo);
        //验证运单信息
        Result result = validDelivery(delivery, BillDelivery.STATUS_STOP, null, request, baseDriverService.get(getDriverId(request)));
        if(result != null){
            return result;
        }
        billDeliveryService.startDelivery(delivery);
        return new Result(ResultStatusCode.OK);
    }

    /**
     * 结束运输（趟次数）
     * @param deliveryVo
     * @param request
     * @return
     */
    @RequestMapping("endDelivery")
    public Result endDelivery(@Validated DeliveryVo deliveryVo, HttpServletRequest request){
        BillDelivery delivery = billDeliveryService.getBaseInfoByDeliveryNo(deliveryVo.getDeliveryBillNo());
        //验证运单信息
        Result result = validDelivery(delivery, BillDelivery.STATUS_TRANSIT, BillDelivery.STATUS_STOP, request, baseDriverService.get(getDriverId(request)));
        if(result != null){
            return result;
        }
        BillLading lading = billLadingService.getByLadingNo(delivery.getLadingBillNo());
        if(lading.getEndRailRadius() != null){
            System.out.println(BaiDuMapUtils.getDistance(deliveryVo.getLng(), deliveryVo.getLat(), lading.getArriveLng(), lading.getArriveLat()));
            if(BaiDuMapUtils.getDistance(deliveryVo.getLng(), deliveryVo.getLat(), lading.getArriveLng(), lading.getArriveLat()) > lading.getEndRailRadius() * 1000){
                return new Result(Global.ERROR_CODE, "您的车辆未到达目的地仓库范围，还不能进行操作");
            }
        }
        List<BillDeliverySign> billDeliverySigns=billDeliverySignService.findListBybillDeliveryNoAndPlate(delivery.getDeliveryBillNo(),delivery.getDriverId(),delivery.getPlateNumber());
        if(CollectionUtils.isNotEmpty(billDeliverySigns)){
            Calendar c = Calendar.getInstance();
            long now = c.getTimeInMillis();
            c.setTime(billDeliverySigns.get(0).getArriveSignDate());
            long lastly = c.getTimeInMillis();
            if(now-lastly<3600000){
                return new Result(Global.ERROR_CODE, "您的车辆在1小时内已提交签收单，请在1小时后尝试。");
            }
        }

        billDeliveryService.endDelivery(delivery);
        return new Result(ResultStatusCode.OK);
    }

    /**
     * 确认签收
     * @param vo
     * @param request
     * @return
     */
    @RequestMapping("customerSign")
    public Result customerSign(@RequestBody @Validated DeliverySignVo vo, HttpServletRequest request){
        BillDelivery delivery = billDeliveryService.getByDeliveryNo(vo.getDeliveryBillNo());
        if(delivery != null && delivery.getStatus().equals(BillDelivery.STATUS_ARRIVE) && delivery.getDriverId().equals(getDriverId(request))){
            BillLading lading = billLadingService.getByLadingNo(delivery.getLadingBillNo());
            if(lading.getEndRailRadius() != null){
                if(BaiDuMapUtils.getDistance(vo.getLng(), vo.getLat(), lading.getArriveLng(), lading.getArriveLat()) > lading.getEndRailRadius() * 1000){
                    return new Result(Global.ERROR_CODE, "您的车辆未到达目的地仓库范围，还不能进行操作");
                }
            }

            BillDeliverySign billDeliverySign=new BillDeliverySign();
            billDeliverySign.setDeliveryBillNo(delivery.getDeliveryBillNo());
            billDeliverySign.setLadingBillNo(delivery.getLadingBillNo());
            billDeliverySign.setArriveSignQuantity(vo.getArriveSignQuantity().toString());
            billDeliverySign.setArriveSignDate(new Date());
            billDeliverySign.setDriverId(delivery.getDriverId());
            billDeliverySign.setPlateNumber(delivery.getPlateNumber());
            billDeliverySign.setArriveSignPhoto(vo.getArriveSignPhoto());
            billDeliverySign.setArriveSignRemark(vo.getArriveSignRemark());
            billDeliverySign.setArriveSignType(vo.getArriveSignType());
            billDeliverySign.setDriverName(delivery.getDriverName());
            billDeliverySign.setCapacity(delivery.getCapacity());


            /* delivery.setArriveSignDate(new Date());
            delivery.setArriveSignPhoto(vo.getArriveSignPhoto());
            delivery.setArriveSignQuantity(vo.getArriveSignQuantity());
            delivery.setArriveSignRemark(vo.getArriveSignRemark());
            delivery.setArriveSignType(vo.getArriveSignType());*/
            billDeliveryService.customerSign(delivery, lading,billDeliverySign);
            return new Result(ResultStatusCode.OK);
        }else{
            return new Result(Global.ERROR_CODE, "运单信息错误");
        }
    }

    /**
     * 结束运输任务
     * @param request
     * @return
     */
    @RequestMapping("finishDelivery")
    public Result finishDelivery(String deliveryBillNo, HttpServletRequest request){
        BillDelivery delivery = billDeliveryService.getByDeliveryNo(deliveryBillNo);
        if(delivery != null && delivery.getDriverId().equals(getDriverId(request))){
            BillLading lading = billLadingService.getByLadingNo(delivery.getLadingBillNo());
            delivery.setStatus(BillDelivery.STATUS_FINISH);
            delivery.setEndDeliveryDate(new Date());
            return new Result(ResultStatusCode.OK,billDeliveryService.finisfDelivery(delivery,lading));
        }else{
            return new Result(Global.ERROR_CODE, "运单信息错误");
        }
    }


    /**
     * 验证运输任务
     * @param delivery
     * @param status
     * @param request
     * @return
     */
    public Result validDelivery(BillDelivery delivery, Integer status, Integer status2, HttpServletRequest request, BaseDriver driver){
        if(delivery == null){
            return new Result(Global.ERROR_CODE, "未获得提单信息");
        }
        if(status2 != null){
            if(!delivery.getStatus().equals(status) && !delivery.getStatus().equals(status2)){
                return new Result(Global.ERROR_CODE, "运单状态错误");
            }
        }else {
            if(!delivery.getStatus().equals(status)){
                return new Result(Global.ERROR_CODE, "运单状态错误");
            }
        }
        if(!delivery.getDriverId().equals(getDriverId(request))){
            return new Result(Global.ERROR_CODE, "您无法操作该运输任务信息");
        }
        if(status.equals(BillDelivery.STATUS_ALLOCAT)){
            if(delivery.getPlateNumber() == null){
                return new Result(Global.ERROR_CODE, "您还未绑定车辆");
            }
        }
        if(driver != null){
            if(driver.getIsOnDuty().equals(Global.BOOLEAN_NO)){
                return new Result(Global.ERROR_CODE, "请先绑定执勤车辆");
            }else if(!driver.getPlateNumber().equals(delivery.getPlateNumber())){
                return new Result(Global.ERROR_CODE, "您当前执勤的车辆与指定车辆不一致，请更换车辆为：" + delivery.getPlateNumber());
            }
        }
        return null;
    }

    /**
     * @description 首页获取司机所有运单
     * @param
     * @author qj
     * @date 2018/10/07
     * @return
     */
    @RequestMapping("/getAllocationDeliveryBillList")
    public Result getAllocationDeliveryBillList(@Validated GridParam param,HttpServletRequest request){
        BillDelivery delivery = new BillDelivery();
        delivery.setDriverId(getDriverId(request));
        return new Result(ResultStatusCode.OK, billDeliveryService.getAllocatOrTransitList(delivery,param));
    }

    /**
     * @description 首页获取司机未完成运单
     * @param
     * @author qj
     * @date 2018/10/07
     * @return
     */
    @RequestMapping("/getHomeTransitList")
    public Result getHomeTransitList(@Validated GridParam param,HttpServletRequest request){
        BillDelivery delivery = new BillDelivery();
        delivery.setDriverId(getDriverId(request));
        return new Result(ResultStatusCode.OK, billDeliveryService.getHomeTransitList(delivery,param));
    }

    /**
     * 获取城市列表
     * @return
     */
    @RequestMapping("getCityList")
    @IgnoreSecurity
    public Result getCityList(){
        return new Result(ResultStatusCode.OK, billDeliveryService.getCityList());
    }


    /**
     * @description 故障反馈
     * @param
     * @author wcf
     * @date 2018/1/26
     * @return
     */
    @RequestMapping("faultFeedback")
    public Result faultFeedback(@RequestBody @Validated DriverFaultRecord record, HttpServletRequest request){
        BillDelivery delivery = billDeliveryService.getByDeliveryNo(record.getdeliveryBillNo());
        if(delivery == null){
            return new Result(Global.ERROR_CODE, "未获得提单信息");
        }
        //运输中或者中途停车才能进行操作
        if(delivery.getStatus().intValue() != BillDelivery.STATUS_TRANSIT && delivery.getStatus().intValue() != BillDelivery.STATUS_STOP){
            return new Result(Global.ERROR_CODE, "运单状态错误");
        }
        BaseDriver driver = baseDriverService.get(getDriverId(request));

        if(delivery.getDriverId().intValue() != driver.getId().intValue()){
            return new Result(Global.ERROR_CODE, "您无法操作该提单信息");
        }

        record.setId(null);
        record.setDriverId(driver.getId());
        record.setCompanyId(driver.getCompanyId());
        record.setladingBillNo(delivery.getLadingBillNo());
        driverFaultRecordService.save(record);

        //异常状态，发送短信和推送消息
        baseDriverService.pushOrSms(delivery.getCompanyId(), delivery.getBillLading().getDepartment(), BaseDriverService.SMS_TYPE_ERROR_STATUS, delivery.getDeliveryBillNo(), delivery.getBillLading().getCustomerName(), driver.getId(), delivery.getLadingBillNo(), DictUtils.getDictLabel(record.getType() + "", "driver_fault_type", ""), driver.getName(), driver.getPhone());

        //化轻公司客服平台获取物流APP通知信息
        Interface4app.setMessage(delivery.getDeliveryBillNo(),
                delivery.getLadingBillNo(), "提单" + delivery.getLadingBillNo() + "，类型：" + DictUtils.getDictLabel(record.getType() + "", "driver_fault_type", "") + "，司机" + driver.getName() + "手机" + driver.getPhone(),
                "异常状态", driver.getName(), DateUtils.getDateTime(), driver.getPhone(), delivery.getBillLading().getCustomerName(), delivery.getPlateNumber());

        return new Result(ResultStatusCode.OK);
    }
}
