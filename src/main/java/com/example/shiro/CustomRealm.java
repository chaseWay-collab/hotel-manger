package com.example.shiro;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.common.utils.Constants;
import com.example.exception.MyException;
import com.example.exception.code.BaseResponseCode;
import com.example.service.PermissionService;
import com.example.service.RedisService;
import com.example.service.RoleService;
import com.example.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.util.StringUtils;

import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * @ClassName: CustomRealm
 * @Description: 自定义Realm
 * @Author: [咖啡]
 * @Version: 1.0
 **/
@Slf4j
public class CustomRealm extends AuthorizingRealm {
    @Autowired
    @Lazy
    private UserService userService;
    @Autowired
    @Lazy
    private PermissionService permissionService;
    @Autowired
    @Lazy
    private RoleService roleService;
    @Autowired
    private RedisService redisService;

    @Value("${spring.redis.key.prefix.permissionRefresh}")
    private String redisPermissionRefreshKey;

    @Value("${spring.redis.key.prefix.userToken}")
    private String USER_TOKEN_PREFIX;
    @Lazy
    @Autowired
    private RedisService redisDB;

    /**
     * 执行授权逻辑
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();

        String sessionInfoStr = redisDB.get(USER_TOKEN_PREFIX + principalCollection.getPrimaryPrincipal());
        if (StringUtils.isEmpty(sessionInfoStr)) {
            throw new MyException(BaseResponseCode.TOKEN_ERROR);
        }
        JSONObject redisSession = JSON.parseObject(sessionInfoStr);
        if (redisSession == null) {
            throw new MyException(BaseResponseCode.TOKEN_ERROR);
        }

        String userId = redisSession.getString(Constants.USERID_KEY);
        //如果修改了角色/权限， 那么刷新权限
        if (redisService.exists(redisPermissionRefreshKey + userId)) {

            List<String> roleNames = getRolesByUserId(userId);
            if (roleNames != null && !roleNames.isEmpty()) {
                redisSession.put(Constants.ROLES_KEY, roleNames);
                authorizationInfo.addRoles(roleNames);
            }
            Set<String> permissions = getPermissionsByUserId(userId);
            authorizationInfo.setStringPermissions(permissions);
            redisSession.put(Constants.PERMISSIONS_KEY, permissions);

            String redisTokenKey = USER_TOKEN_PREFIX + principalCollection.getPrimaryPrincipal();
            Long redisTokenKeyExpire = redisService.getExpire(redisTokenKey);
            //刷新token绑定的角色权限
            redisService.setAndExpire(redisTokenKey, redisSession.toJSONString(), redisTokenKeyExpire);
            //刷新后删除权限刷新标志
            redisService.del(redisPermissionRefreshKey + userId);
        } else {
            if (redisSession.get(Constants.ROLES_KEY) != null) {
                authorizationInfo.addRoles((Collection<String>) redisSession.get(Constants.ROLES_KEY));
            }
            if (redisSession.get(Constants.PERMISSIONS_KEY) != null) {
                authorizationInfo.addStringPermissions((Collection<String>) redisSession.get(Constants.PERMISSIONS_KEY));
            }
        }
        return authorizationInfo;
    }

    /**
     * 执行认证逻辑
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(authenticationToken.getPrincipal(), authenticationToken.getPrincipal(), getName());
        return simpleAuthenticationInfo;
    }

    private List<String> getRolesByUserId(String userId) {
        return roleService.getRoleNames(userId);
    }

    private Set<String> getPermissionsByUserId(String userId) {
        return permissionService.getPermissionsByUserId(userId);
    }
}
