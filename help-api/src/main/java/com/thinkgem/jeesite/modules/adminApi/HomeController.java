package com.thinkgem.jeesite.modules.adminApi;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.oauth.entity.Result;
import com.thinkgem.jeesite.common.oauth.entity.ResultStatusCode;
import com.thinkgem.jeesite.common.vo.GridParam;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.approve.entity.ApproveHistory;
import com.thinkgem.jeesite.modules.approve.service.ApproveHistoryService;
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
    private ApproveHistoryService approveHistoryService;
    @Autowired
    private BillLadingService billLadingService;
    @Autowired
    private BillDeliveryService billDeliveryService;
    @Autowired
    private BillConstantService constantService;



    /**
     * app管理端，获取需要审核的司机详情
     * @return
     */
    @RequestMapping("driverExamineDetail")
    public Result driverExamineDetail(@RequestParam(required = true) Integer driverId){
        return new Result(ResultStatusCode.OK, driverService.get(driverId));
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

}
