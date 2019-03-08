package com.thinkgem.jeesite.modules.task;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.baiduApi.BaiduUtils;
import com.thinkgem.jeesite.modules.baiduApi.model.SearchEntityModel;
import com.thinkgem.jeesite.modules.baiduApi.model.SearchModel;
import com.thinkgem.jeesite.modules.baiduApi.model.StayPointEntityModel;
import com.thinkgem.jeesite.modules.baiduApi.model.StayPointModel;
import com.thinkgem.jeesite.modules.base.entity.BaseDriver;
import com.thinkgem.jeesite.modules.base.service.BaseDriverService;
import com.thinkgem.jeesite.modules.bill.entity.BillConstant;
import com.thinkgem.jeesite.modules.bill.entity.BillDelivery;
import com.thinkgem.jeesite.modules.bill.service.BillConstantService;
import com.thinkgem.jeesite.modules.bill.service.BillDeliveryService;
import com.thinkgem.jeesite.modules.bill.service.BillLadingService;
import com.thinkgem.jeesite.modules.message.entity.DriverMessage;
import com.thinkgem.jeesite.modules.message.service.DriverMessageService;
import com.thinkgem.jeesite.modules.sys.service.UserService;
import com.thinkgem.jeesite.modules.thirdApi.customer.demo.Interface4app;
import com.thinkgem.jeesite.modules.thirdApi.customer.service.AppTransport;
import com.thinkgem.jeesite.modules.utils.BaiDuMapUtils;
import com.thinkgem.jeesite.modules.utils.JPushUtil;
import com.thinkgem.jeesite.modules.utils.RedisUtils;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @Description:
 * @Date: 2018/2/27
 * @Author: wcf
 */
@Service
@Lazy(false)
public class TaskController extends BaseController{
    @Autowired
    private RedisUtils redisUtils;
    @Autowired
    private BaiduUtils baiduUtils;
    @Autowired
    private BillLadingService ladingService;
    @Autowired
    private BillDeliveryService deliveryService;
    @Autowired
    private UserService userService;
    @Autowired
    private BaseDriverService driverService;
    @Autowired
    private BillConstantService constantService;
    @Autowired
    private DriverMessageService driverMessageService;

    /**
     * 开始运输车辆监测，是否已经驶出电子围栏
     */
    @Scheduled(cron = "0 0/10 6-20 * * ?")
    public void startDelivery(){
        logger.info("====================执行检查驶出车辆任务===================");
        List<Object> list = redisUtils.getList(RedisUtils.WAIT_DELIVERY_TRUCK);
        if(CollectionUtils.isNotEmpty(list)){
            for (int i = 0; i < list.size(); i++){
                String[] group = ((String)list.get(i)).split(",");

                BillDelivery delivery = deliveryService.getByDeliveryNo(group[1]);
                System.out.println("开始运输车辆监测，是否已经驶出电子围栏车辆任务号"+delivery.getDeliveryBillNo());
                if(delivery != null){
                    System.out.println("开始运输车辆监测，是否已经驶出电子围栏车牌号"+group[0]);
                    SearchModel result = baiduUtils.search(group[0]);
                    System.out.println("开始运输车辆监测，是否已经驶出电子围栏百度结果"+result.toString());
                    if(result != null && result.getStatus() == 0 && result.getEntities().size() > 0){
                        SearchEntityModel entity = result.getEntities().get(0);
                        if(entity.getLatest_location().getLoc_time() > 1){
                            //当车辆位置位于起点电子围栏半径之外时，提醒
                            if(BaiDuMapUtils.getDistance(entity.getLatest_location().getLongitude(), entity.getLatest_location().getLatitude(), delivery.getBillLading().getDeliveryLng().doubleValue(),
                                    delivery.getBillLading().getDeliveryLat().doubleValue()) > delivery.getBillLading().getStartRailRadius() * 1000){

                                //开始送货，发送短信和推送消息
                                BaseDriver driver = driverService.get(delivery.getDriverId());
                                driverService.pushOrSms(delivery.getCompanyId(), delivery.getBillLading().getDepartment(), BaseDriverService.SMS_TYPE_START_DELIVERY, delivery.getDeliveryBillNo(), delivery.getBillLading().getCustomerName(), delivery.getDriverId(), delivery.getLadingBillNo(), driver.getName(), driver.getPhone(), delivery.getBillLading().getStorageAddress());
                                //从即将出发车辆队列中删除，并添加到即将到达车辆队列中
                                redisUtils.removeList(RedisUtils.WAIT_DELIVERY_TRUCK, (String) list.get(i));
                                redisUtils.setList(RedisUtils.WILL_ARRIVE_TRUCK, list.get(i));

                                //化轻公司客服平台获取物流APP通知信息
                            /*  Interface4app.setMessage(delivery.getDeliveryBillNo(),
                                        delivery.getBillLading().getTiDanHao(), "提单" + delivery.getBillLading().getTiDanHao() + "，司机" + driver.getName() + "手机" + driver.getPhone() + "已驶出" + delivery.getBillLading().getStorageAddress() + "，开始送货！",
                                        "开始送货", driver.getName(), DateUtils.getDateTime(), driver.getPhone(), delivery.getBillLading().getCustomerName(), delivery.getPlateNumber());*/

                                //化轻公司客服平台获取物流APP出库信息
                               /* Interface4app.setTransport(delivery.getDeliveryBillNo(),
                                        delivery.getBillLading().getTiDanHao(), null, null, null, null, null,
                                        null, null, "运输中", "");*/
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * 即将到达车辆检测，是否已经到终点范围之内
     */
    @Scheduled(cron = "0 5/10 6-20 * * ?")
    public void willArrive(){
        logger.info("====================执行检查即将到达车辆任务===================");
        List<Object> list = redisUtils.getList(RedisUtils.WILL_ARRIVE_TRUCK);
        if(CollectionUtils.isNotEmpty(list)){
            BillConstant constant = constantService.get();
            double bj = 0;
            if(constant.getDeliveryDistance() != null){
                bj = Double.parseDouble(constant.getDeliveryDistance());
            }
            for (int i = 0; i < list.size(); i++){
                String[] group = ((String)list.get(i)).split(",");

                BillDelivery delivery = deliveryService.getByDeliveryNo(group[1]);
                if(delivery != null){
                    SearchModel result = baiduUtils.search(group[0]);
                    if(result != null && result.getStatus() == 0 && result.getEntities().size() > 0){
                        SearchEntityModel entity = result.getEntities().get(0);
                        if(entity.getLatest_location().getLoc_time() > 1){
                            //当汽车位置位于 终点电子围栏半径+距离送货点距离 范围之内时，提醒
                            long distance = BaiDuMapUtils.getDistance(entity.getLatest_location().getLongitude(), entity.getLatest_location().getLatitude(), delivery.getBillLading().getArriveLng().doubleValue(),
                                    delivery.getBillLading().getArriveLat().doubleValue());
                            if(distance <= ((delivery.getBillLading().getEndRailRadius() + bj) * 1000)){

                                //即将到达，发送短信和推送消息
                                BaseDriver driver = driverService.get(delivery.getDriverId());
                                driverService.pushOrSms(delivery.getCompanyId(), delivery.getBillLading().getDepartment(), BaseDriverService.SMS_TYPE_WILL_ARRIVE, delivery.getDeliveryBillNo(), delivery.getBillLading().getCustomerName(), delivery.getDriverId(), delivery.getLadingBillNo(), driver.getName(), driver.getPhone(), String.format("%.1f", ((double)distance / 1000)).toString());
                                //从即将到达车辆队列中删除
                                redisUtils.removeList(RedisUtils.WILL_ARRIVE_TRUCK, (String) list.get(i));

                                //化轻公司客服平台获取物流APP通知信息
                               /* Interface4app.setMessage(delivery.getDeliveryBillNo(),
                                        delivery.getBillLading().getTiDanHao(), "提单" + delivery.getBillLading().getTiDanHao() + "，司机" + driver.getName() + "手机" + driver.getPhone() + "离送货地点还有" + String.format("%.1f", ((double)distance / 1000)).toString() + "公里！",
                                        "即将到达", driver.getName(), DateUtils.getDateTime(), driver.getPhone(), delivery.getBillLading().getCustomerName(), delivery.getPlateNumber());*/
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * 异常停靠车辆检测，是否已经到终点范围之内
     */
    @Scheduled(cron = "0 3/30 6-20 * * ?")
    public void stayPoint(){
        logger.info("====================执行检查行异常停靠车辆监测任务===================");
        BillDelivery param = new BillDelivery();
        param.setStatus(BillDelivery.STATUS_STOP);
        //查找正在运输中的车辆
        List<BillDelivery> deliveryList = deliveryService.findList(param);
        if(CollectionUtils.isNotEmpty(deliveryList)){
            BillConstant constant = constantService.get();
            if(constant.getErrorStopTime() == null){
                return;
            }
            for(BillDelivery delivery : deliveryList){
                StayPointModel result = baiduUtils.staypoint(delivery.getPlateNumber(), delivery.getStartDeliveryDate().getTime() / 1000, constant.getErrorStopTime() * 60);
                if(result != null && result.getStatus() == 0 && result.getStaypoint_num() > 0){
                    for(StayPointEntityModel entity : result.getStay_points()){
                        //停留点的结束时间，在当前时间10分钟之内才有效
                        if(entity.getEnd_time() > (System.currentTimeMillis() / 1000 - 600)){
                            //异常停靠，发送短信和推送消息
                            BaseDriver driver = driverService.get(delivery.getDriverId());
                            driverService.pushOrSms(delivery.getCompanyId(), delivery.getBillLading().getDepartment(), BaseDriverService.SMS_TYPE_ERROR_STOP, delivery.getDeliveryBillNo(), delivery.getBillLading().getCustomerName(), delivery.getDriverId(), delivery.getBillLading().getTiDanHao(), driver.getName(), driver.getPhone(), (entity.getDuration() / 60) + "");

                            delivery.setIsErrorStop(Global.BOOLEAN_YES);
                            deliveryService.save(delivery);

                            break;
                        }
                    }
                }
            }
        }
    }
    /**
     * 司机是否关闭app检测
     */
    @Scheduled(cron = "0 8/30 6-20 * * ?")
    public void closeApp(){
        logger.info("====================执行检查司机是否关闭app监测任务===================");
        BillDelivery param = new BillDelivery();
        param.setStatus(BillDelivery.STATUS_TRANSIT);
        param.setIsCloseApp(Global.BOOLEAN_YES);
        //查找正在运输且未关闭过手机的车辆
        List<BillDelivery> deliveryList = deliveryService.findList(param);
        if(CollectionUtils.isNotEmpty(deliveryList)){
            BillConstant constant = constantService.get();
            if(constant.getNotRefreshTime() == null){
                return;
            }
            for(BillDelivery delivery : deliveryList){
                SearchModel result = baiduUtils.search(delivery.getPlateNumber());
                if(result != null && result.getStatus() == 0 && result.getEntities().size() > 0){
                    SearchEntityModel entity = result.getEntities().get(0);
                    if(entity.getLatest_location().getLoc_time() > 1){
                        if((System.currentTimeMillis() / 1000 - entity.getLatest_location().getLoc_time()) > (constant.getNotRefreshTime() * 60)){
                            delivery.setIsCloseApp(Global.BOOLEAN_YES);
                            deliveryService.save(delivery);
                        }
                    }else{
                        if(((System.currentTimeMillis() - delivery.getStartDeliveryDate().getTime()) / 1000) > (constant.getNotRefreshTime() * 60)){
                            delivery.setIsCloseApp(Global.BOOLEAN_YES);
                            deliveryService.save(delivery);
                        }
                    }
                }
            }
        }
    }

    /**
     * 疲劳驾驶，提醒司机
     */
    @Scheduled(cron = "0 23/30 * * * ?")
    public void  fatigueDriving(){
        logger.info("====================执行检查行驶中的司机是否有疲劳驾驶监测任务===================");
        BillDelivery param = new BillDelivery();
        param.setStatus(BillDelivery.STATUS_TRANSIT);
        List<BillDelivery> deliveryList = deliveryService.getDeliveryAndLastRunTime(param);
        if(CollectionUtils.isNotEmpty(deliveryList)){
            BillConstant constant = constantService.get();
            if(constant.getFatigueDrive() != null){
                for(BillDelivery delivery : deliveryList){
                    if(delivery.getLastRunDate() == null){
                        continue;
                    }
                    //先检测运输中的车辆最后一次开始运输的时间距离当前时间是否有超过疲劳驾驶设置的时间，如果超过，再获取鹰眼最后一次定位的时间，如果最后一次定位的时间也超过疲劳驾驶时间，则提醒疲劳驾驶
                    if((System.currentTimeMillis() - delivery.getLastRunDate().getTime()) / 1000 > (constant.getFatigueDrive() * 60)){
                        SearchModel result = baiduUtils.search(delivery.getPlateNumber());
                        if(result != null && result.getStatus() == 0 && result.getEntities().size() > 0){
                            SearchEntityModel entity = result.getEntities().get(0);
                            //如果能够获取到汽车最后一次的定位点，则按照最后一次获取定位的时间来计算
                            if(entity.getLatest_location().getLoc_time() > 1 && (entity.getLatest_location().getLoc_time() - delivery.getLastRunDate().getTime() / 1000) > (constant.getFatigueDrive() * 60)){
                                String content = "您已连续驾驶" + DateUtils.getHourAndMinute(entity.getLatest_location().getLoc_time() * 1000 - delivery.getLastRunDate().getTime()) + "，请注意休息！";
                                JPushUtil.sendNotifyToDriver(delivery.getDriverId(), "疲劳驾驶", content);

                                DriverMessage message = new DriverMessage();
                                message.setDriverId(delivery.getDriverId());
                                message.setType(DriverMessage.TYPE_REMIND);
                                message.setTitle("疲劳驾驶");
                                message.setContent(content);
                                message.setIsRead(Global.BOOLEAN_NO);
                                message.setCreateDate(new Date());

                                driverMessageService.save(message);
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * 从客服服务平台拉取出库单数据
     */
   /* @Scheduled(cron = "0 38/2 6-20 * * ?")
    public void loadDeliveryBillFromCustomerService(){
        BillLading param = new BillLading();
        param.setStatus(BillLading.STATUS_UNFINISH);
        param.setStorehouseType(BillLading.STORE_TYPE_INSIDE);
        //只获取未完成的、内储库的提单
        List<BillLading> ladingList = ladingService.findAllList(param);

        if(CollectionUtils.isNotEmpty(ladingList)){
            for(BillLading lading : ladingList){
                List<Outhouse> outhouseList = Interface4app.getOuthouse(lading.getLadingBillNo());
                if(CollectionUtils.isNotEmpty(outhouseList)){
                    List<BillDelivery> deliveryList = deliveryService.getAllByLadingNo(lading.getLadingBillNo());

                    s : for (Outhouse outhouse : outhouseList){
                        //如果该出库单号已经获取过，则不需要再次存储，避免过度的消耗id
                        if(CollectionUtils.isNotEmpty(deliveryList)){
                            for (BillDelivery delivery : deliveryList){
                                if(outhouse.getBillno().equals(delivery.getOutboundBillNo())){
                                    continue s;
                                }
                            }
                        }
                        BillDelivery delivery = new BillDelivery();
                        delivery.setLadingBillNo(lading.getLadingBillNo());     //提单编号
                        delivery.setOutboundBillNo(outhouse.getBillno());       //出库单号
                        delivery.setStatus(BillDelivery.STATUS_UNALLOCAT);      //未分配
                        delivery.setCompanyId(lading.getCompanyId());           //公司id
                        delivery.setCreateDate(DateUtils.parseDate(outhouse.getOuthousedate(),"yyyy-MM-dd HH:mm:ss"));    //出库时间
                        delivery.setWillQuantity(Double.parseDouble(outhouse.getPlanquantity()));   //应发数
                        if(StringUtils.isNotBlank(outhouse.getSelfweight())){
                            delivery.setTareWeight(Double.parseDouble(outhouse.getSelfweight()));       //皮重
                        }
                        if(StringUtils.isNotBlank(outhouse.getGross())){
                            delivery.setGrossWeight(Double.parseDouble(outhouse.getGross()));           //毛重
                        }
                        delivery.setTankNo(outhouse.getLocation());             //罐号
                        delivery.setShipperName(outhouse.getOwnername());       //货主名称
                        delivery.setIsBalance(Global.BOOLEAN_NO);
                        delivery.setIsAdminComment(Global.BOOLEAN_NO);
                        delivery.setIsCustomerComment(Global.BOOLEAN_NO);
                        delivery.setIsCloseApp(Global.BOOLEAN_NO);
                        delivery.setIsErrorStop(Global.BOOLEAN_NO);

                        if(StringUtils.isNotBlank(outhouse.getQsqty())){
                            //如果出库单存在签收数，则使用u9系统传过来的数据
                            delivery.setSignQuantity(Double.parseDouble(outhouse.getQsqty()));      //司机签收数
                            delivery.setSignDate(DateUtils.parseDate(outhouse.getQsdate(), "yyyy-MM-dd HH:mm:ss"));     //司机签收时间
                            delivery.setSignPhoto(outhouse.getQsfile());    //司机签收文件
                        }else{
                            //如果出库单不存在签收数，则司机签收数=出库单应发数，签收时间为出库时间
                            delivery.setSignQuantity(Double.parseDouble(outhouse.getPlanquantity()));
                            delivery.setSignDate(DateUtils.parseDate(outhouse.getOuthousedate(),"yyyy-MM-dd HH:mm:ss"));
                        }

                        //deliveryList.add(delivery);
                        deliveryService.saveDelivery(delivery);
                    }
                }
            }
        }
    }*/

    /**
     * 从客户服务平台获取运单的客户评价
     */
    @Scheduled(cron = "0 48 6-20 * * ?")
    public void getCustomerComment(){
        List<BillDelivery> deliveryList = deliveryService.getNotCustomerCommentList();
        if(CollectionUtils.isNotEmpty(deliveryList)){
            for (BillDelivery delivery : deliveryList){
                AppTransport result = Interface4app.getEvaluate(delivery.getBillLading().getTiDanHao(),  delivery.getDeliveryBillNo());
                if(result != null){
                    deliveryService.customerComment(result, delivery);
                }
            }
        }
    }
}
