package com.thinkgem.jeesite.modules.baiduApi.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.util.Date;
import java.util.Map;


public  class Utility {
	
	public static int GetStamp(Date d)
	{
	
		return (int)(d.getTime()/1000);
		
	}
	
	
	 /**
     * 数组
     * */
    private static final String[] hexDigits = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f" };

  

    /**
     * 字符串重写
     * 
     * @param b
     * @return
     */
    private static String byteArrayToHexString(byte[] b) {
        StringBuilder resultSb = new StringBuilder();
        for (int i = 0; i < b.length; i++) {
            resultSb.append(byteToHexString(b[i]));
        }

        return resultSb.toString();
    }

    /**
     * byteToHesString
     * */
    private static String byteToHexString(byte b) {
        int n = b;
        if (n < 0) {
            n += 256;
        }
        int d1 = n / 16;
        int d2 = n % 16;
        return hexDigits[d1] + hexDigits[d2];
    }

    /**
     * MD5加密
     * 
     * @param origin
     * @param charsetname
     * @return
     */
    public static String DoMD5(String origin, String charsetname) {
        String resultString = null;
        try {
            resultString = new String(origin);
            MessageDigest md = MessageDigest.getInstance("MD5");
            if (charsetname == null || "".equals(charsetname)) {
                resultString = byteArrayToHexString(md.digest(resultString.getBytes()));
            } else {
                resultString = byteArrayToHexString(md.digest(resultString.getBytes(charsetname)));
            }
        } catch (Exception exception) {
            resultString = null;
        }
        return resultString;
    }

    // 对Map内所有value作utf8编码，拼接返回结果
    public static String toQueryString(Map<?, ?> data)
            throws UnsupportedEncodingException {
        StringBuffer queryString = new StringBuffer();
        for (Map.Entry<?, ?> pair : data.entrySet()) {
            queryString.append(pair.getKey() + "=");
            queryString.append(URLEncoder.encode((String) pair.getValue(),
                    "UTF-8") + "&");
        }
        if (queryString.length() > 0) {
            queryString.deleteCharAt(queryString.length() - 1);
        }
        return queryString.toString();
    }

    // 来自stackoverflow的MD5计算方法，调用了MessageDigest库函数，并把byte数组结果转换成16进制
    public static String MD5(String md5) {
        try {
            MessageDigest md = MessageDigest
                    .getInstance("MD5");
            byte[] array = md.digest(md5.getBytes());
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < array.length; ++i) {
                sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100)
                        .substring(1, 3));
            }
            return sb.toString();
        } catch (java.security.NoSuchAlgorithmException e) {
        }
        return null;
    }

    public static String getData(String sUrl, String sContent) throws IOException{
        return request(sUrl, sContent, "GET");
    }
    public static String postData(String sUrl,String sContent) throws IOException{
        return request(sUrl, sContent, "POST");
    }
	public static String request(String sUrl,String sContent, String type) throws IOException
	{
		
	    // Post请求的url，与get不同的是不需要带参数
        URL postUrl = new URL(sUrl);
        // 打开连接
        HttpURLConnection connection = (HttpURLConnection) postUrl.openConnection();
      
        // 设置是否向connection输出，因为这个是post请求，参数要放在
        // http正文内，因此需要设为true
        connection.setDoOutput(true);
        // Read from the connection. Default is true.
        connection.setDoInput(true);
        // 默认是 GET方式
        connection.setRequestMethod(type);
       
        // Post 请求不能使用缓存
        connection.setUseCaches(false);
       
        connection.setInstanceFollowRedirects(true);
       
        // 配置本次连接的Content-type，配置为application/x-www-form-urlencoded的
        // 意思是正文是urlencoded编码过的form参数，下面我们可以看到我们对正文内容使用URLEncoder.encode
        // 进行编码
        connection.setRequestProperty("Content-Type","application/x-www-form-urlencoded;charset=UTF-8");
        // 连接，从postUrl.openConnection()至此的配置必须要在connect之前完成，
        // 要注意的是connection.getOutputStream会隐含的进行connect。
        connection.connect();
        
        DataOutputStream out = new DataOutputStream(connection
                .getOutputStream());
        // The URL-encoded contend
        // 正文，正文内容其实跟get的URL中 '? '后的参数字符串一致
        out.write(sContent.getBytes("utf-8"));
        out.flush();
        out.close(); 
        
        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(),"utf-8"));
        String line;
        String sResult="";
        while ((line = reader.readLine()) != null){
        	sResult+=line;
        }
      
        reader.close();
        connection.disconnect();
       return sResult;
	}	
	
	private static ObjectMapper mapper=new ObjectMapper(); 
	
	//序列化
	public static String Serialize(Object obj) throws JsonProcessingException
	{		
	     return mapper.writeValueAsString(obj);
	}
	
	
	//反序列化为实体对象	
	public static <T> T DeSerialize(String sData, Class<T> obj) throws JsonMappingException, IOException
	{		
	     return  mapper.readValue(sData,obj);
	}
	
	  //反序列化为实体列表对象	
		public static <T> T DeSerialize(String sData,TypeReference<T> obj) throws JsonMappingException, IOException
		{		
		     return  mapper.readValue(sData,obj);
		}

    public static String PostData(String sUrl,String sContent) throws IOException
    {

        // Post请求的url，与get不同的是不需要带参数
        URL postUrl = new URL(sUrl);
        // 打开连接
        HttpURLConnection connection = (HttpURLConnection) postUrl.openConnection();

        // 设置是否向connection输出，因为这个是post请求，参数要放在
        // http正文内，因此需要设为true
        connection.setDoOutput(true);
        // Read from the connection. Default is true.
        connection.setDoInput(true);
        // 默认是 GET方式
        connection.setRequestMethod("POST");

        // Post 请求不能使用缓存
        connection.setUseCaches(false);

        connection.setInstanceFollowRedirects(true);

        // 配置本次连接的Content-type，配置为application/x-www-form-urlencoded的
        // 意思是正文是urlencoded编码过的form参数，下面我们可以看到我们对正文内容使用URLEncoder.encode
        // 进行编码
        connection.setRequestProperty("Content-Type","application/x-www-form-urlencoded;charset=UTF-8");
        // 连接，从postUrl.openConnection()至此的配置必须要在connect之前完成，
        // 要注意的是connection.getOutputStream会隐含的进行connect。
        connection.connect();

        DataOutputStream out = new DataOutputStream(connection
                .getOutputStream());
        // The URL-encoded contend
        // 正文，正文内容其实跟get的URL中 '? '后的参数字符串一致
        out.write(sContent.getBytes("utf-8"));
        out.flush();
        out.close();

        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(),"utf-8"));
        String line;
        String sResult="";
        while ((line = reader.readLine()) != null){
            sResult+=line;
        }

        reader.close();
        connection.disconnect();
        return sResult;
    }
}
