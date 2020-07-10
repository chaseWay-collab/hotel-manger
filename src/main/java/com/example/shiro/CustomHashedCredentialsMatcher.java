package com.example.shiro;

import com.example.exception.MyException;
import com.example.exception.code.BaseResponseCode;
import com.example.service.RedisService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.credential.SimpleCredentialsMatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;


/**
 * @ClassName: CustomHashedCredentialsMatcher
 * @Description: 认证
 * @Author: [咖啡]
 * @Version: 1.0
 **/
public class CustomHashedCredentialsMatcher extends SimpleCredentialsMatcher {

    @Lazy
    @Autowired
    private RedisService redisDB;
    @Value("${spring.redis.key.prefix.userToken}")  // "user:token:"
    private String USER_TOKEN_PREFIX;

    /**
     * 登录数据校验
     * @param token
     * @param info
     * @return
     */
    @Override
    public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
        String accessToken = (String) token.getPrincipal();
        if (!redisDB.exists(USER_TOKEN_PREFIX + accessToken)) {
            SecurityUtils.getSubject().logout();
            throw new MyException(BaseResponseCode.TOKEN_ERROR);  // 登录凭证已过期
        }
        return true;
    }
}
