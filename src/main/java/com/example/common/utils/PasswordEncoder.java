package com.example.common.utils;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

/**
 * @ClassName: PasswordEncoder
 * @Description: 加密
 * @Author: [咖啡]
 * @Version: 1.0
 **/
public class PasswordEncoder {

    private final static  String[] hexDigits = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d",
            "e", "f"};

    private final static String MD5 = "MD5";
    private final static String SHA = "SHA";

    private Object salt;  // 盐
    private String algorithm;  // 算法

    public PasswordEncoder(String salt) {
        this(salt, MD5);
    }

    public PasswordEncoder(Object salt, String algorithm) {
        this.salt = salt;
        this.algorithm = algorithm;
    }

    /**
     *  密码加密
     * @param rawPass   要加密的密码
     * @return  返回加密后的字符串
     */
    public String encode(String rawPass){
        String result = null;
        try {
            MessageDigest md = MessageDigest.getInstance(algorithm);
            // 加密后的字符串
            result = byteArrayToHexString(md.digest(mergePasswordAndSalt(rawPass).getBytes(StandardCharsets.UTF_8)));
            //System.out.println("[43]" + result);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 合并密码和盐
     * @param password
     * @return
     */
    private String mergePasswordAndSalt(String password) {
        if(password == null){
            password = "";
        }
        if(salt == null || "".equals(salt)){
            //System.out.println("57" + password);
            return password;
        }else {
            //System.out.println("[58]" + salt.toString());
            //System.out.println("61");
            return password + "{" + salt.toString() + "}";
        }
    }

    /**
     *  转换字节数组为 16 进制字符串
     * @param dg    字节数组
     * @return  16进制字符串
     */
    private String byteArrayToHexString(byte[] dg) {
        StringBuilder resultSb = new StringBuilder();
        //System.out.println("[69]" + Arrays.toString(dg));
        for (byte b : dg) {
            resultSb.append(byteToHexString(b));
        }
        return resultSb.toString();
    }

    /**
     *  将字节转换为16进制
     * @param b 字节
     * @return  16进制数
     */
    private static String byteToHexString(byte b) {
        //System.out.println("[80]"+b);
        int n = b;
        if(n < 0){
            n = 256 + n;
        }
        int d1 = n / 16;
        int d2 = n % 16;
        //System.out.println("[89]" + hexDigits[d1] + "; " + hexDigits[d2]);
        return hexDigits[d1] + hexDigits[d2];
    }

    /**
     *  密码验证匹配
     * @param encPass   密文
     * @param rawPass   明文
     * @return  true or false
     */
    public boolean matches(String encPass, String rawPass) {
        String pass1 = "" + encPass;
        String pass2 = encode( rawPass );
        return pass1.equals(pass2);
    }



//    public static void main(String[] args) {
//        String encode = new PasswordEncoder("").encode("1");
//        System.out.println("加密:" + encode);
//    }

}
