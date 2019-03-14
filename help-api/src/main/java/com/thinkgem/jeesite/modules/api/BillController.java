package com.thinkgem.jeesite.modules.api;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.oauth.entity.Result;
import com.thinkgem.jeesite.common.oauth.entity.ResultStatusCode;
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.vo.GridParam;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.annotation.IgnoreSecurity;
import com.thinkgem.jeesite.modules.base.entity.BaseDriver;
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
import com.thinkgem.jeesite.modules.sys.paramVo.DeliverySignVo;
import com.thinkgem.jeesite.modules.sys.paramVo.DeliveryVo;
import com.thinkgem.jeesite.modules.sys.utils.DictUtils;
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
    private DriverFaultRecordService driverFaultRecordService;
    @Autowired
    private BillLadingService billLadingService;
    @Autowired
    private BillConstantService constantService;
    @Autowired
    private BillDeliverySignService billDeliverySignService;



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

}
