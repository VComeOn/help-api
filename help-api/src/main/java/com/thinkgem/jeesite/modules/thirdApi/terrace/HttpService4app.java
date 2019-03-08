package com.thinkgem.jeesite.modules.thirdApi.terrace;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.thinkgem.jeesite.modules.base.entity.BaseCar;
import com.thinkgem.jeesite.modules.base.entity.BaseCompany;
import com.thinkgem.jeesite.modules.base.entity.BaseDriver;
import com.thinkgem.jeesite.modules.bill.entity.BillDelivery;
import com.thinkgem.jeesite.modules.bill.entity.BillDeliverySign;
import com.thinkgem.jeesite.modules.thirdApi.terrace.model.*;
import org.apache.http.HttpEntity;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.Map;


@Service
public class HttpService4app {

	public static void main(String[] args) throws IOException {
		//调用接口1：插入车辆
		/*BaseCar baseCar =new BaseCar();
		baseCar.setCapacity("77");
		baseCar.setCompanyCode("115");
		baseCar.setPlateNumber("苏F6LH50");
		String result=insertTruck(baseCar,"insert");*/

		//调用接口2：插入司机
		/*BaseDriver baseDriver =new BaseDriver();
		baseDriver.setCardId("320686199506075090");
		baseDriver.setCertificateId("25689527587");
		baseDriver.setPhone("18068607199");
		String result=insertDriver(baseDriver,"insert");*/

		//调用接口3：插入运输公司
		/*BaseCompany baseCompany =new BaseCompany();
		baseCompany.setName("南通运输");
		baseCompany.setLeader("杰克发射点");
		baseCompany.setPhone("18068607126");
		baseCompany.setRole(1);
		baseCompany.setBaseCompanyCode("88888223878");
		String result=insertCompany(baseCompany,"insert");*/

		//调用接口4：插入运输任务获取任务单号
		/*BillDelivery billDelivery =new BillDelivery();
		billDelivery.setLadingBillNo("YSWTD2018110120");
		billDelivery.setPlateNumber("苏F6LH50");
		billDelivery.setIdcardDriver("32268319920707558");
		billDelivery.setStatus(0);
		String result=insertDelivery(billDelivery,"insert");*/

		//调用接口5：插入运输任务签收单子
		/*BillDeliverySign billDeliverySign =new BillDeliverySign();
		billDeliverySign.setDeliveryBillNo("CLRW201811060033");
		billDeliverySign.setPlateNumber("苏B123456");
		billDeliverySign.setDriverPhone("18515467852");
		billDeliverySign.setArriveSignDate(new Date());
		billDeliverySign.setArriveSignQuantity("2");
		billDeliverySign.setRemark("车辆app测试");
		billDeliverySign.setCapacity("4");
		billDeliverySign.setDriverName("南通测试");
		String result=insertDeliverySign(billDeliverySign,"insert");*/


	}

	private final static Logger logger = LoggerFactory.getLogger(HttpService4app.class);

	public void testService() {
		System.out.println(">>>>>>the serviec is ok>>>>>>>");
	}

	private static final CloseableHttpClient httpclient;
	public static final String CHARSET = "utf-8";
	//ESB内网
	//public static final String HOST = "http://192.168.7.93:9081";
	//ESB外网
	//public static final String HOST = "http://222.184.238.11:9081";
	//ESB正式
	public static final String HOST = "http://192.168.7.47:9081";

	public static final String CONTENTTYPE = "text/plain";



	static {
		RequestConfig config = RequestConfig.custom().setConnectTimeout(10000).setSocketTimeout(10000).build();
		httpclient = HttpClientBuilder.create().setDefaultRequestConfig(config).build();
	}
	
	
	
	/**
	 * HTTP Get 获取内容
	 * @return 页面内容
	 */
	public static String sendGet(String url, Map<String, Object> params) throws ParseException, UnsupportedEncodingException, IOException{
		
		if(params !=null && !params.isEmpty()){
			
			//拼接参数
			String pams = "";
			for (String key :params.keySet()){
				pams = pams + key + "=" + URLEncoder.encode(params.get(key).toString(), CHARSET);
			}
			url +="?"+pams;
		}
		
		HttpGet httpGet = new HttpGet(url);
		httpGet.setHeader("Authorization", "BearereyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ1MSIsImNyZWF0ZWQiOjE1MzYwNDI0MjgwOTAsImV4cCI6MTU5NjUyMjQyOH0.8kBQ3W1WweZiX87jpjwCdygrWGGf3CP7O_M1JTh84utrfAnbAHROCJjCUO09eZLHxEJKT34TjDTkllnkCfY4zQ");
		//httpGet.setHeader("Content-Type","application/json");
		CloseableHttpResponse response = httpclient.execute(httpGet);
		int statusCode = response.getStatusLine().getStatusCode();
		if(statusCode !=200){
			httpGet.abort();
			throw new RuntimeException("HttpClient,error status code :" + statusCode);
		}
		HttpEntity entity = response.getEntity();
        String result = null;
        if (entity != null) {
            result = EntityUtils.toString(entity, "utf-8");
            EntityUtils.consume(entity);
            response.close();
            return result;
        }else{
        	return null;
        }
	}
	
	/**
	 * 格式化json字符串
	 */
	
	public String addSentDataPackage(String data) {
        String body = "{\r\n" + 
        		"    \"Data\": [" + data + "    ]\r\n" + 
        				"}";
		return body;
		
	}
	
	/**
	 * HTTP Post 获取内容
	 * @return 页面内容
	 * @throws IOException 
	 * @throws ClientProtocolException 
	 */
 
	public  static String sendPost(String url,String body)  {
 
		String result = null;
        HttpPost httpPost = new HttpPost(url);
        httpPost.setHeader("Authorization", "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ1MSIsImNyZWF0ZWQiOjE1MzYwNDI0MjgwOTAsImV4cCI6MTU5NjUyMjQyOH0.8kBQ3W1WweZiX87jpjwCdygrWGGf3CP7O_M1JTh84utrfAnbAHROCJjCUO09eZLHxEJKT34TjDTkllnkCfY4zQ");
		//httpPost.setHeader("Content-Type","application/json");
        httpPost.addHeader("Content-type",CONTENTTYPE);

        //httpPost.setEntity(new StringEntity(URLEncoder.encode(body, CHARSET)));
        httpPost.setEntity(new StringEntity(body, "UTF-8"));
        CloseableHttpResponse response;
		try {
			response = httpclient.execute(httpPost);

        int statusCode = response.getStatusLine().getStatusCode();
        if (statusCode != 200) {
            httpPost.abort();
        }
        HttpEntity entity = response.getEntity();
       
        if (entity != null) {
            result = EntityUtils.toString(entity, "utf-8");
            EntityUtils.consume(entity);
	        response.close();
        }
		} catch (IOException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 平台新增/编辑车辆同步接口
	 *
	 */
	public static String insertTruck(BaseCar baseCar,String operationtype) throws ParseException, UnsupportedEncodingException, IOException {
		String url = HOST+"/transport_provide/TD4CL_CHXX/Method?operationtype="+operationtype;
		//String url = HOST+"/truck/save?operationtype="+operationtype;
		logger.info("平台新增/编辑车辆同步接口地址>>>>>>>>>>"+url);
		Truck4terrace detail = new Truck4terrace();
		detail.setCode(baseCar.getPlateNumber());
		//需要确定是否有车辆类型
		detail.setTypeTruck("Type_2");
		BigDecimal b = new BigDecimal(baseCar.getCapacity());
		b=b.setScale(2, BigDecimal.ROUND_DOWN);
		detail.setQtyLoad(b);
		detail.setCodeTeam(baseCar.getCompanyCode());
		JSONObject jsonObj = (JSONObject) JSON.toJSON(detail);
		String body = jsonObj.toString();
		logger.info("平台新增/编辑车辆同步接口>>>>>>>>>>"+body);
		String result =  sendPost(url, body);
		logger.info("平台新增/编辑车辆同步接口<<<<<<<<<<"+result);
		return result;
	}

	/**
	 * 平台新增/编辑运输公司同步接口
	 * 
	 */
	public static String insertCompany(BaseCompany baseCompany,String operationtype) throws ParseException, UnsupportedEncodingException, IOException {
		String url = HOST+"/transport_provide/TD4CL_YSGS/Method?operationtype="+operationtype;
		//String url = HOST+"/tranteam/save?operationtype="+operationtype;
		logger.info("平台新增/编辑运输公司同步接口地址>>>>>>>>>>"+url);
		//String url ="http://192.168.7.93:9081/transport_provide/TD4CL_YSGS/Method?operationtype="+operationtype;
		Company4terrace detail = new Company4terrace();
		detail.setCode(baseCompany.getBaseCompanyCode());
		detail.setNameTranteam(baseCompany.getName());
		detail.setNameTranteamleader(baseCompany.getLeader());
		detail.setTelTranteamleader(baseCompany.getPhone());
		detail.setCodeOrganizationid(baseCompany.getBaseCompanyCode());
		detail.setTypeTranteam(baseCompany.getRole().toString());
		detail.setAddressTranteam(baseCompany.getAddress());
		JSONObject jsonObj = (JSONObject) JSON.toJSON(detail);
		String body = jsonObj.toString();
		logger.info("平台新增/编辑运输公司同步接口>>>>>>>>>>"+body);
		String result =  sendPost(url, body);
		logger.info("平台新增/编辑运输公司同步接口<<<<<<<<<<"+result);
		return result;
	}

	/**
	 * 平台新增/编辑司机信息同步接口
	 */
	public static String insertDriver(BaseDriver baseDriver,String operationtype) throws ParseException, UnsupportedEncodingException, IOException {
		String url = HOST+"/transport_provide/TD4CL_JSY/Method?operationtype="+operationtype;
		//String url = HOST+"/driver/save?operationtype="+operationtype;
		logger.info("平台新增/编辑司机信息同步接口地址>>>>>>>>>>"+url);
		Driver4terrace detail = new Driver4terrace();
		detail.setCode(baseDriver.getCardId());
		detail.setCodeCertificate(baseDriver.getCertificateId());
		detail.setTelDriver(baseDriver.getPhone());
		JSONObject jsonObj = (JSONObject) JSON.toJSON(detail);
		String body = jsonObj.toString();
		logger.info("平台新增/编辑司机信息同步接口>>>>>>>>>>"+body);
		String result =  sendPost(url, body);
		logger.info("平台新增/编辑司机信息同步接口<<<<<<<<<<"+result);
		return result;
		
	}

	/**
	 * 新增运输任务接口uploadBillDelivery
	 */
	public static String insertDelivery(BillDelivery billDelivery,String operationtype) throws ParseException, UnsupportedEncodingException, IOException {
			String url = HOST+"/u9push/TD4U9_RWD/Method?operationtype="+operationtype;
			//String url = HOST+"/truckmission/save?operationtype="+operationtype;
			logger.info("新增运输任务接口地址>>>>>>>>>>"+url);
			Delivery4terrace detail = new Delivery4terrace();
			detail.setCode(billDelivery.getDeliveryBillNo());
			detail.setCodeTranorder(billDelivery.getLadingBillNo()); //对应运输委托单单号
			detail.setTypeFrombill("1");//单据是车辆app
			detail.setCodeTruck(billDelivery.getPlateNumber());      //车牌号
			detail.setIdcardDriver(billDelivery.getIdcardDriver());   //司机身份证号
		    System.out.println("APP车辆任务状态==========="+billDelivery.getStatus());
			if(billDelivery.getStatus() == 0 || billDelivery.getStatus() ==1 || billDelivery.getStatus() ==2){
				detail.setStatusTruckmission("a"); //运输任务状态
			}else if(billDelivery.getStatus() ==3){
				detail.setStatusTruckmission("b"); //运输任务状态
			}
			detail.setQtyLoad(billDelivery.getCapacity()); //核载量
			detail.setNameDriver(billDelivery.getDriverName());
			detail.setTelDriver(billDelivery.getDriverPhone());
		JSONObject jsonObj = (JSONObject) JSON.toJSON(detail);
		String body = jsonObj.toString();
		logger.info("新增运输任务接口>>>>>>>>>>"+body);
		String result =  sendPost(url, body);
		logger.info("新增运输任务接口<<<<<<<<<<"+result);
		return result;

	}


	/**
	 * 推送运输任务签收接口uploadBillDelivery
	 */
	public static String insertDeliverySign(BillDeliverySign billDeliverySign, String operationtype) throws ParseException, UnsupportedEncodingException, IOException {
		String url = HOST+"/transport_provide/TD4CL_CLZT/Method?operationtype="+operationtype;
		//String url = HOST+"/outrecapp/save?operationtype="+operationtype;
		logger.info("新增运输任务签收接口地址>>>>>>>>>>"+url);
		DeliverySign4terrace detail = new DeliverySign4terrace();
		detail.setCodeMaster(billDeliverySign.getDeliveryBillNo()); //对应运输委托单单号
		detail.setCodeTruck(billDeliverySign.getPlateNumber());// 车牌号
		detail.setTelDriver(billDeliverySign.getDriverPhone());      //司机手机号
		detail.setDateAppsign(billDeliverySign.getArriveSignDate().toString());   //签收时间
		detail.setQtyAppsign(billDeliverySign.getArriveSignQuantity()); //签收数量
		detail.setMemo(billDeliverySign.getRemark());     //备注
		detail.setQtyLoad(billDeliverySign.getCapacity());
		detail.setNameDriver(billDeliverySign.getDriverName());

		JSONObject jsonObj = (JSONObject) JSON.toJSON(detail);
		String body = jsonObj.toString();
		logger.info("新增运输任务签收接口>>>>>>>>>>"+body);
		String result =  sendPost(url, body);
		logger.info("新增运输任务签收接口<<<<<<<<<<"+result);
		return result;

	}















	/**
	 * 变更运输任务状态接口
	 */
	/*public String updateDelivery(List<Truckmission> listTruckmission) throws ParseException, UnsupportedEncodingException, IOException {
		String url = HOST+"/updateDelivery/Method";
		
		Truckmission4app truckmission4app = new Truckmission4app(secretKey,"updateDelivery");
		List<Truckmission4appdetail> list = new ArrayList<>();
		
		for(Truckmission truckmission : listTruckmission) {
			
			Truckmission4appdetail detail = new Truckmission4appdetail();
			
			detail.setLadingBillNo(truckmission.getCode());
			detail.setStatus(truckmission.getStatusTruck());
			
			list.add(detail);
		}
		
		truckmission4app.setList(list);
		
		JSONObject jsonObj = (JSONObject) JSON.toJSON(truckmission4app);
		String body = jsonObj.toString();
		logger.info("变更运输任务状态接口>>>>>>>>>>"+body);
		String result =  sendPost(url, body);
		logger.info("变更运输任务状态接口<<<<<<<<<<"+result);
		return result;
		
	}*/

	/**
	 * 平台新增/编辑运输委托单同步接口uploadLadingBill
	 */
	/*public String uploadLadingBill(List<Tranorder> listTranorder) throws ParseException, UnsupportedEncodingException, IOException {
		String url = HOST+"/uploadLadingBill/Method";
		
		Tranorderupload4app tranorderupload4app = new Tranorderupload4app(secretKey,"uploadLadingBill");
		List<Tranorderupload4appdetail> list = new ArrayList<>();
		
		for(Tranorder tranorder : listTranorder) {
			
			Tranorderupload4appdetail detail = new Tranorderupload4appdetail();
			//TODO CC 数据库存的经纬度，待确认
			
			String xyDelivery = tranorder.getXyDelivery();
			String[] xy1 = xyDelivery.split(",");
			detail.setArriveLat(Double.parseDouble(xy1[0]));
			detail.setArriveLng(Double.parseDouble(xy1[1]));
			
			String xyFromwh = tranorder.getXyFromwh();
			String[] xy2 = xyFromwh.split(",");
			detail.setDeliveryLat(Double.parseDouble(xy2[0]));
			detail.setDeliveryLng(Double.parseDouble(xy2[1]));
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			detail.setBillDate(sdf.format(tranorder.getCreateTime()));
			detail.setBillSecret(tranorder.getCodePin());
			detail.setCompanyCode(tranorder.getCodeTrancompany());
			detail.setCustomerContact(tranorder.getContactCustomer());
			
			TranorderdetailExample detailExample = new TranorderdetailExample();
			detailExample.createCriteria().andIdMasterEqualTo(tranorder.getId());
			Tranorderdetail tranorderdetail = tranorderdetailMapper.selectByExample(detailExample).get(0);
			
			detail.setCustomerLossRate(tranorderdetail.getRateCustloss().toString());
			detail.setCustomerName(tranorder.getNameCustomer());
			detail.setCustomerPhone(tranorder.getTelCustomer());
			detail.setDeliveryAddress(tranorder.getAddressDelivery());
			detail.setDepartment(tranorder.getCodeDept());
			detail.setFreightPrice(tranorderdetail.getUpriceTranreal().doubleValue());
			detail.setLadingBillNo(tranorder.getCodeLadingbill());
			detail.setLatestArriveTime(tranorder.getDateDeliverydeadline());
			detail.setLatestLadingTime(tranorder.getDateValidity());
			detail.setMaterielName(tranorder.getNameItem());
			detail.setOrganizationName(tranorder.getNameOrg());
			detail.setRemark(tranorder.getMemo());
			detail.setStorageAddress(tranorder.getAddressFromwh());
			detail.setStorageName(tranorder.getNameFromwh());
			detail.setTakeMode(Integer.parseInt(tranorder.getTypeLading()));
			detail.setTotalVolume(tranorder.getQtyPlan().doubleValue());
			
			detail.setStorehouseType(tranorder.getTypeWh());
			
			list.add(detail);
		}
		
		tranorderupload4app.setList(list);
		
		JSONObject jsonObj = (JSONObject) JSON.toJSON(tranorderupload4app);
		String body = jsonObj.toString();
		logger.info("平台新增/编辑运输委托单同步接口>>>>>>>>>>"+body);
		String result =  sendPost(url, body);
		logger.info("平台新增/编辑运输委托单同步接口<<<<<<<<<<"+result);
		return result;
		
	}*/

	
}

