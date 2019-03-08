package com.thinkgem.jeesite.modules.openApi;

import com.thinkgem.jeesite.common.utils.StringUtils;

import java.security.MessageDigest;

/**
 * @Description:
 * @Date: 2018/1/30
 * @Author: wcf
 */
public class OpenApiUtils {
    private static final String secretKey = "ZSSZOXX5WB2FQ03Z6KHLGBRNT2788MMSMJOQWPCRUX4N529RX8PIIBXY5YFU412D";

    /**
     * 验证签名是否正确，签名规则，md5(secretKey + t + method)
     * @param t
     * @param method
     * @param sign
     * @return
     */
    public static boolean validSign(Long t, String method, String sign){
        if(t == null || StringUtils.isEmpty(method)){
            return false;
        }
        String md5_sign = DoMD5(secretKey + t + method, null);
        return md5_sign.equals(sign);
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
}
