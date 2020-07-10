package com.example.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.example.common.utils.Constants;
import com.example.entity.system.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Random;
import java.util.Set;

/**
 * @ClassName: HttpSessionService
 * @Description: session管理器
 * @Author: [咖啡]
 * @Version: 1.0
 **/
@Service
public class HttpSessionService {
    @Autowired
    private RedisService redisDB;
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private UserRoleService userRoleService;
    @Autowired
    private RolePermissionService rolePermissionService;
    @Autowired
    private PermissionService permissionService;
    @Autowired
    private RoleService roleService;

    @Value("${spring.redis.key.prefix.userToken}")  // user:token:
    private String USER_TOKEN_PREFIX;
    @Value("${spring.redis.key.expire.userToken}")  // 604800 # 7天 7*24*3600
    private int EXPIRE;
    @Value("${spring.redis.key.prefix.permissionRefresh}}")  // user:token:permissionRefresh:
    private String redisPermissionRefreshKey;
    @Value("${spring.redis.key.expire.permissionRefresh}")  // 604800 # 7天 7*24*3600
    private Long redisPermissionRefreshExpire;

    /**
     * 创建登录令牌token
     * @param user
     * @param roles
     * @param permissions
     * @return
     */
    public String createTokenAndUser(SysUser user, List<String> roles, Set<String> permissions){
        // 方便根据 id 找到 redis 的 key, 修改密码/退出登录, 方便使用
        String token = getRandomToken(8) + "#" + user.getId();
        System.out.println("[HttpSessionService](createTokenAndUser)(58)token: " + token);
        JSONObject sessionInfo = new JSONObject();
        sessionInfo.put(Constants.USERID_KEY, user.getId());
        sessionInfo.put(Constants.USERNAME_KEY, user.getUsername());
        sessionInfo.put(Constants.ROLES_KEY, roles);
        sessionInfo.put(Constants.PERMISSIONS_KEY, permissions);
        String key = USER_TOKEN_PREFIX + token;
        // 设置该用户已登录的token
        redisDB.setAndExpire(key, sessionInfo.toJSONString(), EXPIRE);

        // 登录后删除权限刷新标志
        redisDB.del(redisPermissionRefreshKey + user.getId());
        return token;
    }

    /**
     *  根据 token 获取 userId
     * @param token
     * @return
     */
    public static String getUserIdByToken(String token){
        if(StringUtils.isBlank(token) || !token.contains("#")){
            return "";
        }else {
            return token.substring(token.indexOf("#") + 1);
        }
    }

    /**
     *  获取参数中的 token
     * @return
     */
    public String getTokenFromHeader(){
        String token = request.getHeader(Constants.ACCESS_TOKEN);
        // 如果 header 中不存在 token, 则从参数中获取 token
        if(StringUtils.isBlank(token)){
            token = request.getParameter(Constants.ACCESS_TOKEN);
        }
        return token;
    }

    /**
     *  获取当前 session 信息
     * @return
     */
    public JSONObject getCurrentSession(){
        String token = getTokenFromHeader();
        if(token != null){
            if(redisDB.exists(USER_TOKEN_PREFIX + token)){
                String sessionInfoStr = redisDB.get(USER_TOKEN_PREFIX + token);
                JSONObject sessionInfo = JSON.parseObject(sessionInfoStr);
                return sessionInfo;
            }else {
                return null;
            }
        }else {
            return null;
        }
    }

    /**
     * 获取当前session信息
     *
     * @return
     */
    public String getCurrentUsername() {
        String token = getTokenFromHeader();
        if (null != token) {
            if (redisDB.exists(USER_TOKEN_PREFIX + token)) {
                String sessionInfoStr = redisDB.get(USER_TOKEN_PREFIX + token);
                JSONObject sessionInfo = JSON.parseObject(sessionInfoStr);
                return sessionInfo.getString(Constants.USERNAME_KEY);
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    /**
     * 获取当前session信息
     *
     * @return
     */
    public String getCurrentUserId() {
        String token = getTokenFromHeader();
        if (null != token) {
            if (redisDB.exists(USER_TOKEN_PREFIX + token)) {
                String sessionInfoStr = redisDB.get(USER_TOKEN_PREFIX + token);
                JSONObject sessionInfo = JSON.parseObject(sessionInfoStr);
                return sessionInfo.getString(Constants.USERID_KEY);
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    /**
     * 使当前用户的token失效
     */
    public void abortUserByToken() {
        String token = getTokenFromHeader();
        redisDB.del(USER_TOKEN_PREFIX + token);
    }

    /**
     * 使所有用户的token失效
     */
    public void abortAllUserByToken() {
        String token = getTokenFromHeader();
        String userId = getUserIdByToken(token);
        redisDB.delKeys(USER_TOKEN_PREFIX + "*#" + userId);
    }

    /**
     * 使用户的token失效
     */
    public void abortUserById(String userId) {
        redisDB.delKeys(USER_TOKEN_PREFIX + "*#" + userId);
    }

    /**
     * 使多个用户的token失效
     */
    public void abortUserByUserIds(List<String> userIds) {
        if (CollectionUtils.isNotEmpty(userIds)) {
            for (String id : userIds) {
                redisDB.delKeys(USER_TOKEN_PREFIX + "*#" + id);
            }
        }
    }

    /**
     * 根据用户id， 刷新redis用户权限
     *
     * @param userId
     */
    public void refreshUerId(String userId) {
        Set<String> keys = redisDB.keys("#" + userId);
        //如果修改了角色/权限， 那么刷新权限
        for (String key : keys) {
            JSONObject redisSession = JSON.parseObject(redisDB.get(key));

            List<String> roleNames = getRolesByUserId(userId);
            if (roleNames != null && !roleNames.isEmpty()) {
                redisSession.put(Constants.ROLES_KEY, roleNames);
            }
            Set<String> permissions = getPermissionsByUserId(userId);
            redisSession.put(Constants.PERMISSIONS_KEY, permissions);

            Long redisTokenKeyExpire = redisDB.getExpire(key);
            //刷新token绑定的角色权限
            redisDB.setAndExpire(key, redisSession.toJSONString(), redisTokenKeyExpire);
        }
    }

    /**
     * 根据角色id， 刷新redis用户权限
     *
     * @param roleId
     */
    public void refreshRolePermission(String roleId) {
        List<String> userIds = userRoleService.getUserIdsByRoleId(roleId);
        if (!userIds.isEmpty()) {
            for (String userId : userIds) {
                redisDB.setAndExpire(redisPermissionRefreshKey + userId, userId, redisPermissionRefreshExpire);
            }
        }
    }

    /**
     *  根据用户 id, 刷新 redis 用户权限
     * @param userId
     */
    /*public void refreshUserId(String userId){
        Set<String> keys = redisDB.keys("#" + userId);
        // 如果修改了角色权限, 那么刷新权限
        for (String key : keys) {
            JSONObject redisSession = JSON.parseObject(redisDB.get(key));

            //List<String> roleNames = getRolesByUserId(userId);
        }
    }*/

    /**
     * 根据角色id， 刷新redis用户权限
     *
     * @param roleId
     */
    /*public void refreshRolePermission(String roleId) {
        List<String> userIds = userRoleService.getUserIdsByRoleId(roleId);
        if (!userIds.isEmpty()) {
            for (String userId : userIds) {
                redisDB.setAndExpire(redisPermissionRefreshKey + userId, userId, redisPermissionRefreshExpire);
            }

        }
    }*/

    /**
     * 根据权限id， 刷新redis用户权限
     *
     * @param permissionId
     */
    public void refreshPermission(String permissionId) {
        //根据权限id，更新redis权限
        List<String> roleIds = rolePermissionService.getRoleIds(permissionId);
        if (!roleIds.isEmpty()) {
            List<String> userIds = userRoleService.getUserIdsByRoleIds(roleIds);
            if (!userIds.isEmpty()) {
                for (String userId : userIds) {
                    redisDB.setAndExpire(redisPermissionRefreshKey + userId, userId, redisPermissionRefreshExpire);
                }

            }
        }
    }
    /**
     *  生成随机的token
     * @param length
     * @return
     */
    private String getRandomToken(int length) {
        Random random = new Random();
        StringBuilder randomStr = new StringBuilder();

        // 根据length生成相应长度的随机字符串
        for (int i=0; i<length; i++){
            String charOrNum = random.nextInt(2) % 2 == 0 ? "char" : "num";
            //System.out.println("[charOrNum] " + charOrNum);
            //System.out.println("i = " + i);
            // 输出字母还是数字
            if("char".equalsIgnoreCase(charOrNum)){
                // 输出大写字母还是小写
                int temp = random.nextInt(2) % 2 == 0 ? 65 : 97;
                //System.out.println("[temp] " + temp);
                randomStr.append((char)(random.nextInt(26) + temp));
                //System.out.println("[47] " + randomStr.toString());
            }else if ("num".equalsIgnoreCase(charOrNum)){
                randomStr.append(String.valueOf(random.nextInt(10)));
                //System.out.println("[50] " + randomStr.toString());
            }
        }
        return randomStr.toString();
    }

    private List<String> getRolesByUserId(String userId) {
        return roleService.getRoleNames(userId);
    }

    private Set<String> getPermissionsByUserId(String userId) {
        return permissionService.getPermissionsByUserId(userId);
    }

}
