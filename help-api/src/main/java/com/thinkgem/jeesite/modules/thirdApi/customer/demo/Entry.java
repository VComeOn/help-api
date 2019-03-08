package com.thinkgem.jeesite.modules.thirdApi.customer.demo;

import java.util.ArrayList;


import com.thinkgem.jeesite.modules.thirdApi.customer.service.AppTransport;
import com.thinkgem.jeesite.modules.thirdApi.customer.service.Outhouse;

public class Entry {

	public static void main(String[] args) {
		//调用接口1：物流app获取化轻公司客服平台出库记录
		ArrayList<Outhouse> test1= Interface4app.getOuthouse();
		
		//调用接口2:化轻公司客服平台获取物流APP通知信息
		/*String test2=Interface4app.setMessage();
		if(test2.equals("ok")){
			System.out.println("执行成功");
		}else{
			System.out.println("执行失败");
		}*/
		
		//调用接口3:化轻公司客服平台获取物流APP出库信息
		/*String test3=Interface4app.setTransport();
		if(test3.equals("ok")){
			System.out.println("执行成功");
		}else{
			System.out.println("执行失败");
		}*/
		
		//调用接口4:化轻公司传递客户评价给物流app
		ArrayList<AppTransport> test4=Interface4app.getEvaluate();
		
		
	}

}
