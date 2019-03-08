package com.thinkgem.jeesite.modules.openApi;

import com.thinkgem.jeesite.common.beanvalidator.BeanValidators;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.oauth.entity.Result;
import com.thinkgem.jeesite.common.oauth.entity.ResultStatusCode;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.baiduApi.BaiduUtils;
import com.thinkgem.jeesite.modules.baiduApi.model.AddressLocation;
import com.thinkgem.jeesite.modules.base.entity.BaseCar;
import com.thinkgem.jeesite.modules.base.entity.BaseCompany;
import com.thinkgem.jeesite.modules.base.entity.BaseDriver;
import com.thinkgem.jeesite.modules.base.service.BaseCarService;
import com.thinkgem.jeesite.modules.base.service.BaseCompanyService;
import com.thinkgem.jeesite.modules.base.service.BaseDriverService;
import com.thinkgem.jeesite.modules.bill.entity.AddressLngLatRecord;
import com.thinkgem.jeesite.modules.bill.entity.BillConstant;
import com.thinkgem.jeesite.modules.bill.entity.BillDelivery;
import com.thinkgem.jeesite.modules.bill.entity.BillLading;
import com.thinkgem.jeesite.modules.bill.service.AddressLngLatRecordService;
import com.thinkgem.jeesite.modules.bill.service.BillConstantService;
import com.thinkgem.jeesite.modules.bill.service.BillDeliveryService;
import com.thinkgem.jeesite.modules.bill.service.BillLadingService;
import com.thinkgem.jeesite.modules.openApi.model.*;
import com.thinkgem.jeesite.modules.sys.service.SystemService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Validator;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description:
 * @Date: 2018/1/30
 * @Author: wcf
 */
@RestController
@RequestMapping("/api/open/")
public class OpenApiController {

    @Autowired
    private BillConstantService constantService;
    @Autowired
    private BillLadingService ladingService;
    @Autowired
    private BaseCompanyService companyService;
    @Autowired
    protected Validator validator;
    @Autowired
    private BillDeliveryService deliveryService;
    @Autowired
    private AddressLngLatRecordService addressLngLatRecordService;
    @Autowired
    private BaiduUtils baiduUtils;
    @Autowired
    private BaseDriverService baseDriverService;
    @Autowired
    private BaseCarService baseCarService;
    /**
     * 上传委托单信息
     * @param model
     * @return
     */
    @RequestMapping("uploadLadingBill")
    public Result uploadLadingBill(@Validated @RequestBody BillLadingListModel model) throws ParseException {
        if(!OpenApiUtils.validSign(model.getT(), "uploadLadingBill", model.getSign())){
            return new Result(ResultStatusCode.SIGN_ERROR);
        }
        if((System.currentTimeMillis() / 1000) - model.getT() > 172800){
            return new Result(ResultStatusCode.TIME_OUT);
        }
        List<BillLading> list = new ArrayList<BillLading>();
        for (BillLadingModel billLadingModel : model.getList()){
            //数据验证
            BeanValidators.validateWithException(validator, billLadingModel);

            BaseCompany company = companyService.getByCode(billLadingModel.getCompanyCode());
            if(company == null){
                return new Result(Global.ERROR_CODE, billLadingModel.getCompanyCode() + " 该运输公司不存在");
            }
            BillLading lading = new BillLading();
            BeanUtils.copyProperties(billLadingModel, lading);
            //设置运输公司信息
            lading.setCompanyName(company.getName());
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat formatter2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            lading.setBillDate(formatter.parse(billLadingModel.getBillDate()));
            lading.setLatestLadingTime(formatter2.parse(billLadingModel.getLatestLadingTime()));
            lading.setLatestArriveTime(formatter2.parse(billLadingModel.getLatestArriveTime()));
            //起点经纬度校验
            if(billLadingModel.getDeliveryLat() != null && billLadingModel.getDeliveryLng() != null && billLadingModel.getDeliveryLat() != 0 && billLadingModel.getDeliveryLng() != 0){
                lading.setDeliveryLat(new BigDecimal(billLadingModel.getDeliveryLat()));
                lading.setDeliveryLng(new BigDecimal(billLadingModel.getDeliveryLng()));
            }else{
                //起点地址检索
                AddressLngLatRecord startAddress = addressLngLatRecordService.getByAdress(lading.getStorageAddress());
                if(startAddress != null){
                    lading.setDeliveryLng(startAddress.getLng());
                    lading.setDeliveryLat(startAddress.getLat());
                }else{
                    //通过地址获取经纬度
                    AddressLocation location = baiduUtils.addressToPoint(lading.getStorageAddress());
                    if(location != null){
                        lading.setDeliveryLng(new BigDecimal(location.getLng()));
                        lading.setDeliveryLat(new BigDecimal(location.getLat()));
                        //保存地址记录，以便下次可以直接获取
                        AddressLngLatRecord address = new AddressLngLatRecord();
                        address.setAddress(lading.getStorageAddress());
                        address.setLng(new BigDecimal(location.getLng()));
                        address.setLat(new BigDecimal(location.getLat()));

                        addressLngLatRecordService.save(address);
                    }
                }
            }

            if(billLadingModel.getArriveLat() != null && billLadingModel.getArriveLng() != null && billLadingModel.getArriveLat() != 0 && billLadingModel.getArriveLng() != 0){
                lading.setArriveLat(new BigDecimal(billLadingModel.getArriveLat()));
                lading.setArriveLng(new BigDecimal(billLadingModel.getArriveLng()));
            }else {
                //终点地址检索
                AddressLngLatRecord endAddress = addressLngLatRecordService.getByAdress(lading.getDeliveryAddress());
                if(endAddress != null){
                    lading.setArriveLng(endAddress.getLng());
                    lading.setArriveLat(endAddress.getLat());
                }else{
                    //通过地址获取经纬度
                    AddressLocation location = baiduUtils.addressToPoint(lading.getDeliveryAddress());
                    if(location != null){
                        lading.setArriveLng(new BigDecimal(location.getLng()));
                        lading.setArriveLat(new BigDecimal(location.getLat()));

                        //保存地址记录，以便下次可以直接获取
                        AddressLngLatRecord address = new AddressLngLatRecord();
                        address.setAddress(lading.getDeliveryAddress());
                        address.setLng(new BigDecimal(location.getLng()));
                        address.setLat(new BigDecimal(location.getLat()));

                        addressLngLatRecordService.save(address);
                    }
                }
            }

            BillConstant constant = constantService.get();

            lading.setCompanyId(company.getId());
            lading.setStatus(0);    //未完成
            //设置默认围栏半径
            if(StringUtils.isNotEmpty(constant.getRailRadius())){
                lading.setStartRailRadius(Double.valueOf(constant.getRailRadius()));
                lading.setEndRailRadius(Double.valueOf(constant.getRailRadius()));
            }
            //设置提单号
            String tiDanHao=lading.getZiTiDanHao();
            lading.setZiTiDanHao(lading.getTiDanHao());
            lading.setTiDanHao(tiDanHao);
            list.add(lading);
        }

        ladingService.insertList(list);

        return new Result(ResultStatusCode.OK);
    }

    /**
     * 检查提单是否可关闭
     * @param model
     * @return
     */
    @RequestMapping("checkLading")
    public Result checkLading(@RequestBody @Validated BillLadingCheckListModel model){
        if(!OpenApiUtils.validSign(model.getT(), "checkLading", model.getSign())){
            return new Result(ResultStatusCode.SIGN_ERROR);
        }
        if((System.currentTimeMillis() / 1000) - model.getT() > 172800){
            return new Result(ResultStatusCode.TIME_OUT);
        }
        for(BillLadingCheckModel checkModel : model.getList()){
            //数据验证
            BeanValidators.validateWithException(validator, checkModel);

            BillLading lading = ladingService.getByLadingNo(checkModel.getLadingBillNo());
            if(lading != null){
                int total = deliveryService.countByLading(checkModel.getLadingBillNo(), null);          //总运单数量
                int isBalance = deliveryService.countByLading(checkModel.getLadingBillNo(), Global.BOOLEAN_YES);    //已结算的运单数量
                if(total > 0 && total == isBalance){
                    //return new Result(ResultStatusCode.OK, true);   //可关闭提单
                    checkModel.setStatus(true);
                }else {
                    //return new Result(ResultStatusCode.OK, false);  //不可关闭提单
                    checkModel.setStatus(false);
                }
            }else{
                //return new Result(Global.ERROR_CODE, "该提单不存在");
                checkModel.setStatus(false);
            }
        }
        return new Result(ResultStatusCode.OK, model.getList());
    }

    /**
     * 关闭提单
     * @param model
     * @return
     */
    @RequestMapping("closeLading")
    public Result closeLading(@RequestBody @Validated BillLadingCheckListModel model){
        if(!OpenApiUtils.validSign(model.getT(), "closeLading", model.getSign())){
            return new Result(ResultStatusCode.SIGN_ERROR);
        }
        if((System.currentTimeMillis() / 1000) - model.getT() > 172800){
            return new Result(ResultStatusCode.TIME_OUT);
        }
        for(BillLadingCheckModel checkModel : model.getList()) {
            //数据验证
            BeanValidators.validateWithException(validator, checkModel);

            BillLading lading = ladingService.getByLadingNo(checkModel.getLadingBillNo());
            if(lading != null){
                int total = deliveryService.countByLading(checkModel.getLadingBillNo(), null);          //总运单数量
                int isBalance = deliveryService.countByLading(checkModel.getLadingBillNo(), Global.BOOLEAN_YES);    //已结算的运单数量
                if(total > 0 && total == isBalance){
                    lading.setStatus(BillLading.STATUS_FINISH);
                    ladingService.save(lading);

                    checkModel.setStatus(true);
                }else {
                    checkModel.setStatus(false);
                }
            }else{
                checkModel.setStatus(false);
            }
        }
        return new Result(ResultStatusCode.OK, model.getList());
    }

    /**
     * 新增/编辑运输车队公司接口，同步到运输app后台
     * @param model
     * @return
     */
    @RequestMapping("syncCompany")
    public Result syncCompany(@RequestBody @Validated BaseCompanyListModel  model){
        if(!OpenApiUtils.validSign(model.getT(), "syncCompany", model.getSign())){
            return new Result(ResultStatusCode.SIGN_ERROR);
        }
        if((System.currentTimeMillis() / 1000) - model.getT() > 172800){
            return new Result(ResultStatusCode.TIME_OUT);
        }
        for(BaseCompanyModel companyModel : model.getList()) {
            //数据验证
            BeanValidators.validateWithException(validator, companyModel);
            //通过公司机构代码查询是否在此运输车队公司
            BaseCompany baseCompany = companyService.getByCode(companyModel.getBaseCompanyCode());
            if(baseCompany !=null){
                baseCompany.setName(companyModel.getName());
                baseCompany.setBaseCompanyCode(companyModel.getBaseCompanyCode());
                baseCompany.setRole(companyModel.getRole());
                baseCompany.setAddress(companyModel.getAddress());
                baseCompany.setLeader(companyModel.getLeader());
                baseCompany.setPhone(companyModel.getPhone());
            }else if(baseCompany == null){
                baseCompany=new BaseCompany();
                BeanUtils.copyProperties(companyModel,baseCompany );
            }
            companyService.save(baseCompany);
        }
        return new Result(ResultStatusCode.OK);
    }

    /**
     * 平台新增/编辑司机信息同步接口
     * @param model
     * @return
     */
    @RequestMapping("syncDriver")
    public Result syncDriver(@RequestBody @Validated BaseDriverListModel  model){
        if(!OpenApiUtils.validSign(model.getT(), "syncDriver", model.getSign())){
            return new Result(ResultStatusCode.SIGN_ERROR);
        }
        if((System.currentTimeMillis() / 1000) - model.getT() > 172800){
            return new Result(ResultStatusCode.TIME_OUT);
        }
        for(BaseDriverModel baseDriverModel : model.getList()) {
            //数据验证
            BeanValidators.validateWithException(validator, baseDriverModel);
            BaseDriver baseDriver = new BaseDriver();
            BeanUtils.copyProperties(baseDriverModel, baseDriver);
            BaseCompany company =new BaseCompany();
            if(baseDriverModel !=null && baseDriverModel.getBaseCompanyCode() !=null){
                company=companyService.getByCode(baseDriverModel.getBaseCompanyCode());
                if(company == null){
                    return new Result(Global.ERROR_CODE, "所属运输车队公司代码为"+baseDriverModel.getBaseCompanyCode() + " 不存在");
                }
            }
            BaseDriver baseDriver2 = baseDriverService.getByPhone(baseDriver.getPhone());
                if( baseDriver2== null){
                    baseDriver.setPwd(SystemService.entryptPassword("123456Dr"));//设置默认密码
                    baseDriver.setPoint(0);
                    baseDriver.setPointTotal(0);
                    baseDriver.setLevel(0);
                    baseDriver.setIsOnDuty(0);//不在执勤
                    baseDriver.setStatus(1) ; //默认已审核
                    baseDriver.setCompanyId(company.getId());

                }else{
                    baseDriver.setCompanyId(company.getId());
                    baseDriver.setId(baseDriver2.getId());
                }
            baseDriverService.save(baseDriver);

        }
        return new Result(ResultStatusCode.OK);
    }

    /**
     * 平台新增/编辑车辆信息同步接口
     * @param model
     * @return
     */
    @RequestMapping("syncCar")
    public Result syncCar(@RequestBody @Validated BaseCarListModel  model){
        if(!OpenApiUtils.validSign(model.getT(), "syncCar", model.getSign())){
            return new Result(ResultStatusCode.SIGN_ERROR);
        }
        if((System.currentTimeMillis() / 1000) - model.getT() > 172800){
            return new Result(ResultStatusCode.TIME_OUT);
        }
        for(BaseCarModel baseCarModelModel : model.getList()) {
            //数据验证
            BeanValidators.validateWithException(validator, baseCarModelModel);
            BaseCar baseCar = new BaseCar();
            BeanUtils.copyProperties(baseCarModelModel, baseCar);
            BaseCar baseCar2 = baseCarService.getCarByPlateNumber(baseCarModelModel.getPlateNumber());
            if(baseCarModelModel !=null && baseCarModelModel.getBaseCompanyCode() !=null){
                BaseCompany baseCompany=companyService.getByCode(baseCarModelModel.getBaseCompanyCode());
                if(baseCompany == null){
                    return new Result(Global.ERROR_CODE, "所属运输车队公司代码为"+baseCarModelModel.getBaseCompanyCode() + " 不存在");
                }
                baseCar.setCompanyId(baseCompany.getId());
            }
            if(baseCar2 != null){
                baseCar.setId(baseCar2.getId());
            }
            baseCar.setStatus(1);
            baseCarService.save(baseCar);
        }
        return new Result(ResultStatusCode.OK);
    }

    /**
     *  新增运输任务接口
     * @param model
     * @return
     */
    @RequestMapping("uploadBillDelivery")
    public Result uploadBillDelivery(@RequestBody @Validated BillDeliveryListModel  model){
        if(!OpenApiUtils.validSign(model.getT(), "uploadBillDelivery", model.getSign())){
            return new Result(ResultStatusCode.SIGN_ERROR);
        }
        if((System.currentTimeMillis() / 1000) - model.getT() > 172800){
            return new Result(ResultStatusCode.TIME_OUT);
        }
        for(BillDeliveryModel billDeliveryModel : model.getList()) {
            //数据验证
            BeanValidators.validateWithException(validator, billDeliveryModel);
            //复制实体类数据
            BillDelivery billDelivery=new BillDelivery();
            BeanUtils.copyProperties(billDeliveryModel, billDelivery);
            if(billDeliveryModel.getCompanyCode() != null){
                BaseCompany baseCompany=companyService.getByOrganizationCode(billDeliveryModel.getCompanyCode());
                if(baseCompany !=null){
                    billDelivery.setCompanyId(baseCompany.getId());
                }else{
                    return new Result(Global.ERROR_CODE, billDeliveryModel.getCompanyCode() + " 该运输公司不存在");
                }
            }
            if(billDelivery.getId() == null){
                BillLading lading = ladingService.getByLadingNo(billDelivery.getLadingBillNo());
                if(lading == null){
                    return new Result(Global.ERROR_CODE, billDelivery.getLadingBillNo() + " 运输委托单不存在");
                }
                if(lading.getStatus() !=null && lading.getStatus() == 1){
                    return new Result(Global.ERROR_CODE, billDelivery.getLadingBillNo() + " 已完成");
                }

               /* BillConstant constant = constantService.findAllList(new BillConstant()).get(0);
                if(constant != null){
                    if(StringUtils.isNotEmpty(constant.getErrorUpper())){
                        //BillDelivery delivery1 = billDeliveryService.get(delivery.getId());
                        BillLading lading = ladingService.getByLadingNo(billDelivery.getLadingBillNo());

                        Double total = deliveryService.getTotalLadingQuantity(billDelivery.getLadingBillNo());
                        if((total + billDelivery.getWillQuantity()) >= lading.getTotalVolume() * (1 + Double.parseDouble(constant.getErrorUpper()) / 100)){
                            return new Result(Global.ERROR_CODE, billDelivery.getLadingBillNo() + " 的提货量已超出提单总量上限");
                        }
                    }
                }*/

                billDelivery.setStatus(BillDelivery.STATUS_DISPATCH);//已发单
                billDelivery.setIsBalance(Global.BOOLEAN_NO);//未结算
                billDelivery.setIsCloseApp(Global.BOOLEAN_NO);//未关闭app
                billDelivery.setIsErrorStop(Global.BOOLEAN_NO);//未异常停靠
                billDelivery.setIsAdminComment(Global.BOOLEAN_NO);//后台未评价
                billDelivery.setIsCustomerComment(Global.BOOLEAN_NO);//客户未评价
                deliveryService.insertDelivery(billDelivery);
            }


        }
        return new Result(ResultStatusCode.OK);
    }

    /**
     *  平台更改运输任务状态接口
     * @param model
     * @return
     */
    @RequestMapping("updateDelivery")
    public Result updateDelivery(@RequestBody @Validated BillDeliveryStatusListModel  model){
        if(!OpenApiUtils.validSign(model.getT(), "updateDelivery", model.getSign())){
            return new Result(ResultStatusCode.SIGN_ERROR);
        }
        if((System.currentTimeMillis() / 1000) - model.getT() > 172800){
            return new Result(ResultStatusCode.TIME_OUT);
        }
        for(BillDeliveryStatusModel billDeliveryStatusModel : model.getList()) {
            //数据验证
            BeanValidators.validateWithException(validator, billDeliveryStatusModel);
            BillDelivery delivery = deliveryService.getBaseInfoByDeliveryNo(billDeliveryStatusModel.getDeliveryBillNo());
           if(delivery != null){
               if(billDeliveryStatusModel.getStatus() == 1){
                   billDeliveryStatusModel.setStatus(3);
               }
               delivery.setStatus(billDeliveryStatusModel.getStatus());
               deliveryService.save(delivery);
           }else if(delivery == null){
               return new Result(Global.ERROR_CODE, billDeliveryStatusModel.getDeliveryBillNo() + " 不存在");
           }
        }
        return new Result(ResultStatusCode.OK);
    }

    /**
     * 关闭运输委托单
     * @param model
     * @return
     */
    @RequestMapping("closeBillLading")
    public Result closeBillLading(@RequestBody @Validated BillLadingCheckListModel model){
        if(!OpenApiUtils.validSign(model.getT(), "closeBillLading", model.getSign())){
            return new Result(ResultStatusCode.SIGN_ERROR);
        }
        if((System.currentTimeMillis() / 1000) - model.getT() > 172800){
            return new Result(ResultStatusCode.TIME_OUT);
        }
        for(BillLadingCheckModel checkModel : model.getList()) {
            //数据验证
            BeanValidators.validateWithException(validator, checkModel);

            BillLading lading = ladingService.getByLadingNo(checkModel.getLadingBillNo());
            if(lading != null){
                lading.setStatus(checkModel.getLadingStatus());
                ladingService.save(lading);
            }else{
                return new Result(1,"运输委托单不存在");
            }
        }
        return new Result(ResultStatusCode.OK);
    }

}
