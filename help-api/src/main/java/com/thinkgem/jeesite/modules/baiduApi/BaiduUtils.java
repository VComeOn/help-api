package com.thinkgem.jeesite.modules.baiduApi;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.thinkgem.jeesite.modules.baiduApi.api.Utility;
import com.thinkgem.jeesite.modules.baiduApi.core.HttpClient;
import com.thinkgem.jeesite.modules.baiduApi.core.UrlDomain;
import com.thinkgem.jeesite.modules.baiduApi.model.AddressLocation;
import com.thinkgem.jeesite.modules.baiduApi.model.AddressPoint;
import com.thinkgem.jeesite.modules.baiduApi.model.SearchModel;
import com.thinkgem.jeesite.modules.baiduApi.model.StayPointModel;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Component;

import javax.sound.midi.Soundbank;
import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;

/**
 * @Description:
 * @Date: 2018/2/12
 * @Author: wcf
 */
@Component
public class BaiduUtils {

    private static final Integer serviceId = 158376;             //百度鹰眼提供的service_id
    private static final String ak = "Mjs6mj0E8viDoKtzS81d7UheriNFp2pU";    //百度地图使用的公钥

    /**
     * 百度鹰眼添加设备
     * @param entityName
     * @throws Exception
     */
    public void addBaiduEntity(String entityName, String companyName, int times) throws Exception{
        Map paramsMap = new TreeMap<String, String>();
        paramsMap.put("ak", ak);
        paramsMap.put("entity_name", entityName);
        paramsMap.put("entity_desc", companyName);
        paramsMap.put("service_id", serviceId + "");

        String paramsStr = Utility.toQueryString(paramsMap);
        String sData = Utility.PostData("http://yingyan.baidu.com/api/v3/entity/add", paramsStr);

        JSONObject jsonObject = JSONObject.fromObject(sData);

        if(jsonObject.getInt("status") == 1 && times < 5){
            //如果百度服务器出现异常，则休息一秒后再请求一次
            times ++;
            Thread.sleep(1000);
            addBaiduEntity(entityName, companyName, times);
        }else if (jsonObject.getInt("status") != 0) {
            throw new Exception(jsonObject.getString("message"));
        }
    }

    /**
     * 更新百度鹰眼设备
     * @param entityName
     * @throws Exception
     */
    public void updateBaiduEntity(String entityName, String companyName, int times) throws Exception{
        Map paramsMap = new TreeMap<String, String>();
        paramsMap.put("ak", ak);
        paramsMap.put("entity_name", entityName);
        paramsMap.put("entity_desc", companyName);
        paramsMap.put("service_id", serviceId + "");

        String paramsStr = Utility.toQueryString(paramsMap);
        String sData = Utility.PostData("http://yingyan.baidu.com/api/v3/entity/update", paramsStr);

        JSONObject jsonObject = JSONObject.fromObject(sData);

        if(jsonObject.getInt("status") == 1 && times < 5){
            //如果百度服务器出现异常，则休息一秒后再请求一次
            times ++;
            Thread.sleep(1000);
            updateBaiduEntity(entityName, companyName, times);
        }else if (jsonObject.getInt("status") != 0) {
            throw new Exception(jsonObject.getString("message"));
        }
    }

    /**
     * 删除百度鹰眼设备
     * @param entityName
     * @throws Exception
     */
    public void deleteBaiduEntity(String entityName, int times) throws Exception{
        Map paramsMap = new TreeMap<String, String>();
        paramsMap.put("ak", ak);
        paramsMap.put("entity_name", entityName);
        paramsMap.put("service_id", serviceId + "");

        String paramsStr = Utility.toQueryString(paramsMap);
        String sData = Utility.PostData("http://yingyan.baidu.com/api/v3/entity/delete", paramsStr);

        JSONObject jsonObject = JSONObject.fromObject(sData);
        if(jsonObject.getInt("status") == 1 && times < 5){
            //如果百度服务器出现异常，则休息一秒后再请求一次
            times ++;
            Thread.sleep(1000);
            deleteBaiduEntity(entityName, times);
        }else if (jsonObject.getInt("status") != 0) {
            throw new Exception(jsonObject.getString("message"));
        }
    }

    /**
     * 通过设备号（车牌号）检索
     * @param entityName
     * @return
     */
    public SearchModel search(String entityName){
        Map paramsMap = new TreeMap<String, String>();
        paramsMap.put("ak", ak);
        paramsMap.put("service_id", serviceId + "");
        paramsMap.put("query", entityName);

        try {
            String paramsStr = Utility.toQueryString(paramsMap);
            String sData = HttpClient.sendRequest(UrlDomain.YINGYAN_HTTP_URL + "entity/search", paramsStr, HttpClient.METHOD_GET);

            ObjectMapper mapper = new ObjectMapper();
            SearchModel response = mapper.readValue(sData, SearchModel.class);
            return response;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 通过设备号（车牌号），对该设备的停留点进行分析
     * @param entityName
     * @param startTime
     * @param stayTime
     * @return
     */
    public static StayPointModel staypoint(String entityName, long startTime, int stayTime){
        Map paramsMap = new TreeMap<String, String>();
        paramsMap.put("ak", ak);
        paramsMap.put("service_id", serviceId + "");
        paramsMap.put("entity_name", entityName);
        paramsMap.put("start_time", startTime + "");
        paramsMap.put("end_time", System.currentTimeMillis() / 1000 + "");
        paramsMap.put("stay_time",stayTime + "");

        try {
            String paramsStr = Utility.toQueryString(paramsMap);
            String sData = HttpClient.sendRequest(UrlDomain.YINGYAN_HTTP_URL + "analysis/staypoint", paramsStr, HttpClient.METHOD_GET);

            ObjectMapper mapper = new ObjectMapper();
            StayPointModel response = mapper.readValue(sData, StayPointModel.class);
            return response;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 正向地址分析，将地址转化为经纬度
     * @param address
     * @return
     */
    public AddressLocation addressToPoint(String address){
        Map paramsMap = new TreeMap<String, String>();
        paramsMap.put("address", address);
        paramsMap.put("ak", ak);
        paramsMap.put("output", "json");

        try {
            String paramsStr = Utility.toQueryString(paramsMap);

            String sData = HttpClient.sendRequest(UrlDomain.BAIDU_HTTP_URL, paramsStr, HttpClient.METHOD_GET);
            ObjectMapper mapper = new ObjectMapper();
            AddressPoint response = mapper.readValue(sData, AddressPoint.class);
            if(response.getStatus() == 0 && response.getResult() != null && response.getResult().getPrecise() == 1){
                return response.getResult().getLocation();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) throws IOException {
       /* Map paramsMap = new TreeMap<String, String>();
        paramsMap.put("ak", ak);
        paramsMap.put("entity_name", "苏F8662V");
        paramsMap.put("entity_desc", "南通化轻物流");
        paramsMap.put("service_id", serviceId + "");

        String paramsStr = Utility.toQueryString(paramsMap);

       *//* String wholeStr = new String("/api/v3/entity/add?" + paramsStr + sk);

        // 对上面wholeStr再作utf8编码
        String tempStr = URLEncoder.encode(wholeStr, "UTF-8");

        String sn = Utility.MD5(tempStr);
        // 调用下面的MD5方法得到最后的sn签名7de5a22212ffaa9e326444c75a58f9a0
        System.out.println(sn);

        paramsMap.put("sn", sn);*//*

        String sData = Utility.PostData("http://yingyan.baidu.com/api/v3/entity/add", paramsStr);
        System.out.println(sData);

        JSONObject jsonObject = JSONObject.fromObject(sData);
        if (jsonObject.getInt("status") != 0) {
            System.out.println(jsonObject.getString("message"));
        }*/

        //search("苏F8661V");
        //staypoint("苏F8661V", System.currentTimeMillis() / 1000 - 3600, 600);

        //System.out.println(BaiduUtils.addressToPoint("江苏省南通市崇川区中南世纪城17号楼"));
        /*AddressLocation location = addressToPoint("江苏省南通市崇川区中南世纪城17号楼");
        if(location != null){
            System.out.println("经度：" + location.getLng() + "；纬度：" + location.getLat());
        }*/

    }
}
