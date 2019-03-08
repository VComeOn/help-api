package com.thinkgem.jeesite.tools;

import com.thinkgem.jeesite.common.security.Digests;
import com.thinkgem.jeesite.common.utils.Encodes;

/**
 * @Author mrhuang
 * @Date 2017/3/15 0015 15:35
 */
public class MD5 {
    public static boolean validatePasswordMD5(String plainPassword, String password) {
        String plain = Encodes.unescapeHtml(plainPassword);
        byte[] hashPassword = Digests.md5(plain.getBytes());
        System.out.println(Encodes.encodeHex(hashPassword).toUpperCase());
        return password.equals(Encodes.encodeHex(hashPassword).toUpperCase());
    }

    public static void main(String[] args) {
        validatePasswordMD5("113656","113656");
        System.out.println(entryptPasswordMD5("113656"));
    }

    public static String entryptPasswordMD5(String plainPassword) {
        String plain = Encodes.unescapeHtml(plainPassword);
        byte[] hashPassword = Digests.md5(plain.getBytes());
        return Encodes.encodeHex(hashPassword).toUpperCase();
    }
}
