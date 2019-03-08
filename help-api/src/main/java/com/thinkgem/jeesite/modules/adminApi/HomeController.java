package com.thinkgem.jeesite.modules.adminApi;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.oauth.entity.Result;
import com.thinkgem.jeesite.common.oauth.entity.ResultStatusCode;
import com.thinkgem.jeesite.common.vo.GridParam;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.adminApi.Vo.DriverVo;
import com.thinkgem.jeesite.modules.approve.entity.ApproveHistory;
import com.thinkgem.jeesite.modules.approve.service.ApproveHistoryService;
import com.thinkgem.jeesite.modules.base.entity.BaseCar;
import com.thinkgem.jeesite.modules.base.entity.BaseDriver;
import com.thinkgem.jeesite.modules.base.service.BaseCarService;
import com.thinkgem.jeesite.modules.base.service.BaseDriverService;
import com.thinkgem.jeesite.modules.bill.entity.BillConstant;
import com.thinkgem.jeesite.modules.bill.entity.BillDelivery;
import com.thinkgem.jeesite.modules.bill.entity.BillLading;
import com.thinkgem.jeesite.modules.bill.service.BillConstantService;
import com.thinkgem.jeesite.modules.bill.service.BillDeliveryService;
import com.thinkgem.jeesite.modules.bill.service.BillLadingService;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Date;
import java.util.List;

/**
 * @Description:
 * @Date: 2018/3/7
 * @Author: wcf
 */
@RestController
@RequestMapping("/admin/api/home")
public class HomeController extends BaseController{
    @Autowired
    private UserService userService;
    @Autowired
    private BaseDriverService driverService;
    @Autowired
    private BaseCarService baseCarService;
    @Autowired
    private ApproveHistoryService approveHistoryService;
    @Autowired
    private BillLadingService billLadingService;
    @Autowired
    private BillDeliveryService billDeliveryService;
    @Autowired
    private BillConstantService constantService;

    /**
     * app管理端，获取司机状态列表
     * @param request
     * @param param
     * @param vo
     * @return
     */
    @RequestMapping("driverList")
    public Result driverList(HttpServletRequest request, @Validated GridParam param, @Validated DriverVo vo){
        User user = userService.get(getAdminId(request));
        if(user.getRoleType().equals(3)){
            vo.setCompanyId(user.getCompanyId());
        }
        return new Result(ResultStatusCode.OK, driverService.getDriverStatusList(vo, param));
    }

    /**
     * app管理端，搜索司机状态列表
     * @param request
     * @param vo
     * @return
     */
    @RequestMapping("searchDriverList")
    public Result searchDriverList(HttpServletRequest request, @Validated DriverVo vo){
        User user = userService.get(getAdminId(request));
        if(user.getRoleType().equals(3)){
            vo.setCompanyId(user.getCompanyId());
        }
        return new Result(ResultStatusCode.OK, driverService.searchDriverStatusList(vo));
    }

    /**
     * 获取司机状态详情
     * @param deliveryBillNo：运单号
     * @param driverId：司机id
     * @return
     */
    @RequestMapping("driverDetail")
    public Result driverDetail(String deliveryBillNo,@RequestParam(required = true) Integer driverId){
        return new Result(ResultStatusCode.OK, driverService.getDriverDetail(deliveryBillNo, driverId));
    }

//    *********************************************二期接口*******************************************************

    /**
     * app管理端，获取需要审核的司机列表
     * @param request
     * @param param
     * @return
     */
    @RequestMapping("driverExamineList")
    public Result driverExamineList(HttpServletRequest request, @Validated GridParam param){
        User user = userService.get(getAdminId(request));
        BaseDriver driver=new BaseDriver();
        if(user.getRoleType().equals(3)){
            driver.setCompanyId(user.getCompanyId());
        }
        return new Result(ResultStatusCode.OK, driverService.finDriverExamine(driver, param));
    }

    /**
     * app管理端，获取需要审核的司机详情
     * @return
     */
    @RequestMapping("driverExamineDetail")
    public Result driverExamineDetail(@RequestParam(required = true) Integer driverId){
        return new Result(ResultStatusCode.OK, driverService.get(driverId));
    }

    /**
     * app管理端，驳回司机认证
     * @return
     */
    @RequestMapping("rejectDriverExamine")
    public Result rejectDriverExamine(HttpServletRequest request,@RequestParam(required = true) Integer driverId) throws IOException {
        Integer sysUserId=getAdminId(request);
        driverService.updateStatus(2,driverId,sysUserId);
        return new Result(ResultStatusCode.OK);
    }

    /**
     * app管理端，通过司机认证
     * @return
     */
    @RequestMapping("agreeDriverExamine")
    public Result agreeDriverExamine(HttpServletRequest request,@RequestParam(required = true) Integer driverId) throws IOException {
        Integer sysUserId=getAdminId(request);
        driverService.updateStatus(1,driverId,sysUserId);
        return new Result(ResultStatusCode.OK);
    }

    /**
     * app管理端，获取需要审核的车辆
     * @param request
     * @param param
     * @return
     */
    @RequestMapping("carExamineList")
    public Result carExamineList(HttpServletRequest request, @Validated GridParam param){
        User user = userService.get(getAdminId(request));
        BaseCar baseCar=new BaseCar();
        if(user.getRoleType().equals(3)){
            baseCar.setCompanyId(user.getCompanyId());
        }
        return new Result(ResultStatusCode.OK, baseCarService.findCarExamine(baseCar, param));
    }

    /**
     * app管理端，获取需要审核的车辆详情
     * @return
     */
    @RequestMapping("carExamineDetail")
    public Result carExamineDetail(@RequestParam(required = true) Integer carId){
        return new Result(ResultStatusCode.OK, baseCarService.get(carId));
    }

    /**
     * app管理端，驳回车辆认证
     * @return
     */
    @RequestMapping("rejectCarExamine")
    public Result rejectCarExamine(HttpServletRequest request,@RequestParam(required = true) Integer carId) throws IOException {
        Integer sysUserId=getAdminId(request);
        baseCarService.updateStatus(2,carId,sysUserId);
        return new Result(ResultStatusCode.OK);
    }

    /**
     * app管理端，通过车辆认证
     * @return
     */
    @RequestMapping("agreeCarExamine")
    public Result agreeCarExamine(HttpServletRequest request,@RequestParam(required = true) Integer carId) throws IOException {
        Integer sysUserId=getAdminId(request);
        baseCarService.updateStatus(1,carId,sysUserId);
        return new Result(ResultStatusCode.OK);
    }

    /**
     * app管理端，获取审核历史
     * @return
     */
    @RequestMapping("approveHistoryList")
    public Result approveHistoryList(HttpServletRequest request, @Validated GridParam param){
        Integer sysUserId=getAdminId(request);
        ApproveHistory approveHistory=new ApproveHistory();
        approveHistory.setSysUserId(sysUserId);
        return new Result(ResultStatusCode.OK,approveHistoryService.list(approveHistory,param));
    }

    /**
     * app管理端，获取运输委托单列表
     * @param request
     * @param param
     * @return
     */
    @RequestMapping("billLadingList")
    public Result billLadingList(HttpServletRequest request, @Validated GridParam param,String tiDanHao){
        User user = userService.get(getAdminId(request));
        BillLading billLading=new BillLading();
        billLading.setTiDanHao(tiDanHao);
        if(user.getRoleType().equals(3)){
            billLading.setCompanyId(user.getCompanyId());
        }
        return new Result(ResultStatusCode.OK, billLadingService.list(billLading, param));
    }

    /**
     * app管理端，获取运输委托单详情
     * @return
     */
    @RequestMapping("getBillLadingDetail")
    public Result getBillLadingDetail(@RequestParam(required = true) Integer billLadingId){
        return new Result(ResultStatusCode.OK, billLadingService.get(billLadingId));
    }

    /**
     * app管理端，获取运输委托单下面的运输任务
     * @return
     */
    @RequestMapping("billDeliveryList")
    public Result billDeliveryList(@RequestParam(required = true) String ladingBillNo){
        BillDelivery billDelivery=new BillDelivery();
        billDelivery.setLadingBillNo(ladingBillNo);
        return new Result(ResultStatusCode.OK,billDeliveryService.billDeliveryList(billDelivery));
    }

    /**
     * app管理端，获取可以委托的司机列表
     * @return
     */
    @RequestMapping("driverSelect")
    public Result driverSelect(HttpServletRequest request,@RequestParam(required = true) String driverName){
        User user = userService.get(getAdminId(request));
        BaseDriver baseDriver=new BaseDriver();
        baseDriver.setName(driverName);
        if(user.getRoleType().equals(3)){
            baseDriver.setCompanyId(user.getCompanyId());
        }

        return new Result(ResultStatusCode.OK,driverService.driverSelectList(baseDriver));
    }

    /**
     * app管理端，获取可以委托的车辆列表
     * @return
     */
    @RequestMapping("carSelect")
    public Result carSelect(HttpServletRequest request,@RequestParam(required = true) String plateNumber){
        User user = userService.get(getAdminId(request));
        BaseCar baseCar=new BaseCar();
        baseCar.setPlateNumber(plateNumber);
        if(user.getRoleType().equals(3)){
            baseCar.setCompanyId(user.getCompanyId());
        }

        return new Result(ResultStatusCode.OK,baseCarService.selectCarList(baseCar));
    }



    /**
     * app管理端，委托车辆
     * @return
     */
    @RequestMapping("saveAllocation")
    public Result saveAllocation(@RequestParam(required = true) String ladingBillNo
            ,@RequestParam(required = true) Integer driverId
            ,@RequestParam(required = true) String plateNumber
           /* ,@RequestParam(required = true) Double willQuantity
            ,@RequestParam(required = true) String estimatedArrivalTime*/){
        //获取运单系统常量
        BillConstant constant = constantService.findAllList(new BillConstant()).get(0);
        BillLading lading = billLadingService.getByLadingNo(ladingBillNo);
        if(lading.getStatus() == 1 ){
            //通过车牌号、司机、运输委托单号、状态判断是否可以绑定运输委托单
            return new Result(Global.ERROR_CODE, "此运输委托单已完成");
        }
        if(lading.getStatus() == 2 ){
            //通过车牌号、司机、运输委托单号、状态判断是否可以绑定运输委托单
            return new Result(Global.ERROR_CODE, "此运输委托单已挂起");
        }
        List<BillDelivery>  billDelivery3=billDeliveryService.getByLadingNoDriverCar(ladingBillNo,driverId,plateNumber);
        if( billDelivery3!=null &&  billDelivery3.size()>0){
            //通过车牌号、司机、运输委托单号、状态判断是否可以绑定运输委托单
            return new Result(Global.ERROR_CODE, "此车辆已派单");
        }
        List<BillDelivery> billDelivery2=billDeliveryService.getFinishByLadingNoDriverCar(ladingBillNo,driverId,plateNumber);
        if(billDelivery2 !=null && billDelivery2.size()>0){
            billDelivery2.get(0).setStatus(BillDelivery.STATUS_DISPATCH);
            billDelivery2.get(0).setIsBalance(Global.BOOLEAN_NO);//未结算
            billDelivery2.get(0).setIsCloseApp(Global.BOOLEAN_NO);//未关闭app
            billDeliveryService.save(billDelivery2.get(0));
            System.out.println("车辆APP系统存在此单，重新打开");
            return new Result(ResultStatusCode.OK);
        }
        BillDelivery billDelivery=new BillDelivery();
        billDelivery.setCompanyId(lading.getCompanyId());
        billDelivery.setShipperName(lading.getCustomerName());
        billDelivery.setLadingBillNo(ladingBillNo);
        //billDelivery.setWillQuantity(willQuantity);
        billDelivery.setDriverId(driverId);
        billDelivery.setPlateNumber(plateNumber);
       // billDelivery.setEstimatedArrivalTime(estimatedArrivalTime);
        billDelivery.setStatus(BillDelivery.STATUS_DISPATCH);
        billDelivery.setIsBalance(Global.BOOLEAN_NO);//未结算
        billDelivery.setIsCloseApp(Global.BOOLEAN_NO);//未关闭app
        billDelivery.setIsErrorStop(Global.BOOLEAN_NO);//未异常停靠
        billDelivery.setIsAdminComment(Global.BOOLEAN_NO);//未评论
        billDelivery.setIsCustomerComment(Global.BOOLEAN_NO);//客户未评论


        billDeliveryService.insertDelivery(billDelivery);
        return new Result(ResultStatusCode.OK);
    }

    /**
     * app管理端，获取需要审核的任务
     * @param request
     * @param param
     * @return
     */
    @RequestMapping("billDeliveryExamineList")
    public Result billDeliveryExamineList(HttpServletRequest request, @Validated GridParam param){
        User user = userService.get(getAdminId(request));
        BillDelivery billDelivery=new BillDelivery();
        if(user.getRoleType().equals(3)){
            billDelivery.setCompanyId(user.getCompanyId());
        }
        return new Result(ResultStatusCode.OK, billDeliveryService.findBillDeliveryExamine(billDelivery, param));
    }


    /**
     * app管理端，获取任务详情
     * @param request
     * @return
     */
    @RequestMapping("getBillDeliveryDetail")
    public Result getBillDeliveryDetail(HttpServletRequest request, @RequestParam(required = true) Integer deliveryId){
        BillDelivery billDelivery=billDeliveryService.get(deliveryId);
        BaseCar car=baseCarService.getCarByPlateNumber(billDelivery.getPlateNumber());
        if(car.getStatus() == 0){
            return new Result(Global.ERROR_CODE,"绑定车辆是新增车辆，需要审核车辆",car.getId());
        }else if(car.getStatus() == 2){
            return new Result(Global.ERROR_CODE,"绑定车辆状态错误");
        }
        return new Result(ResultStatusCode.OK,billDelivery);
    }

    /**
     * app管理端，驳回任务认证
     * @return
     */
    @RequestMapping("rejectBillDeliveryExamine")
    public Result rejectBillDeliveryExamine(HttpServletRequest request,@RequestParam(required = true) Integer deliveryId){
        Integer sysUserId=getAdminId(request);
        billDeliveryService.updateStatus(BillDelivery.STATUS_RBUT,deliveryId,sysUserId);
        return new Result(ResultStatusCode.OK);
    }

    /**
     * app管理端，同意任务认证
     * @return
     */
    @RequestMapping("agreeBillDeliveryExamine")
    public Result agreeBillDeliveryExamine(HttpServletRequest request,@RequestParam(required = true) Integer deliveryId){
        Integer sysUserId=getAdminId(request);
        billDeliveryService.updateStatus(BillDelivery.STATUS_TAKING,deliveryId,sysUserId);
        return new Result(ResultStatusCode.OK);
    }
}
