package com.example.common.utils;

import java.util.UUID;

/**
 * @ClassName: PasswordUtils
 * @Description: 加密工具类
 * @Author: [咖啡]
 * @Version: 1.0
 **/
public class PasswordUtils {

    /**
     *  匹配密码    IDEA会对语句进行简洁性检查
     * @param salt  盐
     * @param rawPass   明文
     * @param encPass   密文
     * @return  是否匹配
     */
    public static boolean ismatch(String salt, String rawPass, String encPass){
        return !new PasswordEncoder(salt).matches(encPass, rawPass);
    }

    /**
     *  将明文密码加密
     * @param rawPass   明文
     * @param salt  盐
     * @return  密文
     */
    public static String encode(String rawPass, String salt){
        return new PasswordEncoder(salt).encode(rawPass);
    }

    /**
     *  设置加密盐
     * @return 盐
     */
    public static String getSalt(){
        return UUID.randomUUID().toString().replaceAll("-","").substring(0, 20);
    }

}
