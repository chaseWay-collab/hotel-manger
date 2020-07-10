package com.example.common.utils;

/**
 * @ClassName: Constants
 * @Description: 常量
 * @Author: [咖啡]
 * @Version: 1.0
 **/
public class Constants {

    /**
     *  shiro 加密算法
     */
    public static final String HASH_ALGORITHM = "SHA-1";

    /**
     *  生成 Hash 值的迭代次数
     */
    public static final int HASH_INTERaTIONS = 1024;

    /**
     *  盐的长度
     */
    public static final int SALT_SIZE = 8;

    /**
     *  验证码
     */
    public static final String VALIDATE_CODE = "validateCode";

    /**
     *  语音提示验证码
     */
    public static final String ISVOICE = "ISVOICE";

    /**
     *  系统用户默认密码
     */
    public static final String DEFAULT_PASSWORD = "123456";

    /**
     *  允许跨域请求
     */
    public static final String ALLOWEDORIGINS = "http://localhost:63342";


    /*--------------------------------------------------------------*/

    public static final String PERMISSIONS_KEY = "permissions-key";
    public static final String USERID_KEY = "userid-key";
    public static final String USERNAME_KEY = "username-key";
    public static final String ROLES_KEY = "roles-key";

    /**
     * 正常token
     */
    public static final String ACCESS_TOKEN = "authorization";

    /**
     * 组织机构编码key
     */
    public static final String DEPT_CODE_KEY = "dept-code-key_";

    /**
     * 该角色拥有所有权限-不能删除
     */
    public static final String SUPER_ROLE_ID = "11b3b80c-4a0b-4a92-96ea-fdd4f7a4a7e9";


}
