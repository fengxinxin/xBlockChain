package com.iolo.xblockchain.util;

import java.security.MessageDigest;

/**
 * Created with IntelliJ IDEA.
 * User: iOLO
 * Date: 2018/3/4
 * Time: 13:34
 */
public class StringUtil {
    //使用 Sha256 算法加密一个字符串，返回计算结果
    public static String applySha256(String input) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            //对输入使用 sha256 算法
            byte[] hash = digest.digest(input.getBytes("UTF-8"));
            // 它会包含16进制的 hash 值
            StringBuffer hexString = new StringBuffer();
            for (int i = 0; i < hash.length; i++) {
                String hex = Integer.toHexString(0xff & hash[i]);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
