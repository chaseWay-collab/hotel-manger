package com.example.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.common.aop.annotation.LogAnnotation;
import com.example.common.utils.DataResult;
import com.example.common.utils.ImageCodeUtil;
import com.example.entity.system.SysUser;
import com.example.exception.code.BaseResponseCode;
import com.example.service.HttpSessionService;
import com.example.service.UserService;
import com.example.vo.request.*;
import com.example.vo.response.LoginRespVO;
import com.example.vo.response.UserOwnRoleRespVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;

/**
 * @ClassName: UserController
 * @Description: 用户管理
 * @Author: [咖啡]
 * @Version: 1.0
 **/
@RestController
@Api(tags = "组织模块-用户管理")
@RequestMapping("/sys")
@Slf4j
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private HttpSessionService httpSessionService;

    /**
     * 登录
     * @param vo
     * @return
     */
    @PostMapping(value = "/user/login")
    @ApiOperation(value = "用户登录接口")
    public DataResult<LoginRespVO> login(@RequestBody @Valid LoginReqVO vo) {
        DataResult<LoginRespVO> result = DataResult.success();
        result.setData(userService.login(vo));
        return result;
    }

    /**
     * 注册
     * @param vo
     * @return
     */
    @PostMapping("/user/register")
    @ApiOperation(value = "用户注册接口")
    public DataResult<String> register(@RequestBody @Valid RegisterReqVO vo) {
        DataResult<String> result = DataResult.success();
        result.setData(userService.register(vo));
        return result;
    }

    /**
     * 验证token
     * @return
     */
    @GetMapping("/user/unLogin")
    @ApiOperation(value = "引导客户端去登录")
    public DataResult<Object> unLogin() {
        DataResult<Object> result = DataResult.getResult(BaseResponseCode.TOKEN_ERROR);
        return result;
    }

    /**
     * 更新 /user
     * @param vo
     * @param request
     * @return
     */
    @PutMapping("/user")
    @ApiOperation(value = "更新用户信息接口")
    @LogAnnotation(title = "用户管理", action = "更新用户信息")
    @RequiresPermissions("sys:user:update")
    public DataResult updateUserInfo(@RequestBody @Valid UserUpdateReqVO vo, HttpServletRequest request) {
        String userId = httpSessionService.getCurrentUserId();
        userService.updateUserInfo(vo, userId);
        return DataResult.success();
    }

    /**
     * 更新 /user/info
     * @param vo
     * @param request
     * @return
     */
    @PutMapping("/user/info")
    @ApiOperation(value = "更新用户信息接口")
    @LogAnnotation(title = "用户管理", action = "更新用户信息")
    public DataResult updateUserInfoById(@RequestBody @Valid UserUpdateReqVO vo, HttpServletRequest request) {
        String userId = httpSessionService.getCurrentUserId();
        vo.setId(userId);
        userService.updateUserInfoMy(vo, userId);
        return DataResult.success();
    }

    /**
     * 详情 /user/{id}
     * @param id
     * @return
     */
    @GetMapping("/user/{id}")
    @ApiOperation(value = "查询用户详情接口")
    @LogAnnotation(title = "用户管理", action = "查询用户详情")
    @RequiresPermissions("sys:user:detail")
    public DataResult<SysUser> detailInfo(@PathVariable("id") String id) {
        DataResult<SysUser> result = DataResult.success();
        result.setData(userService.detailInfo(id));
        return result;
    }

    /**
     * 详情 /user
     * @param request
     * @return
     */
    @GetMapping("/user")
    @ApiOperation(value = "查询用户详情接口")
    @LogAnnotation(title = "用户管理", action = "查询用户详情")
    public DataResult<SysUser> youSelfInfo(HttpServletRequest request) {
        String userId = httpSessionService.getCurrentUserId();
        DataResult<SysUser> result = DataResult.success();
        result.setData(userService.detailInfo(userId));
        return result;
    }

    /**
     * 列表
     * @param vo
     * @return
     */
    @PostMapping("/users")
    @ApiOperation(value = "分页获取用户列表接口")
    @RequiresPermissions("sys:user:list")
    @LogAnnotation(title = "用户管理", action = "分页获取用户列表")
    public DataResult<IPage<SysUser>> pageInfo(@RequestBody SysUser vo) {
        return DataResult.success(userService.pageInfo(vo));
    }

    /**
     * 新增
     * @param vo
     * @return
     */
    @PostMapping("/user")
    @ApiOperation(value = "新增用户接口")
    @RequiresPermissions("sys:user:add")
    @LogAnnotation(title = "用户管理", action = "新增用户")
    public DataResult addUser(@RequestBody @Valid UserAddReqVO vo) {
        userService.addUser(vo);
        return DataResult.success();
    }

    /**
     * 退出
     * @param request
     * @return
     */
    @GetMapping("/user/logout")
    @ApiOperation(value = "退出接口")
    @LogAnnotation(title = "用户管理", action = "退出")
    public DataResult logout(HttpServletRequest request) {
        userService.logout();
        return DataResult.success();
    }

    /**
     * 修改密码
     * @param vo
     * @param request
     * @return
     */
    @PutMapping("/user/pwd")
    @ApiOperation(value = "修改密码接口")
    @LogAnnotation(title = "用户管理", action = "更新密码")
    public DataResult updatePwd(@RequestBody UpdatePasswordReqVO vo, HttpServletRequest request) {
        String userId = httpSessionService.getCurrentUserId();
        userService.updatePwd(vo, userId);
        return DataResult.success();
    }

    /**
     * 删除
     * @param userIds
     * @param request
     * @return
     */
    @DeleteMapping("/user")
    @ApiOperation(value = "删除用户接口")
    @LogAnnotation(title = "用户管理", action = "删除用户")
    @RequiresPermissions("sys:user:deleted")
    public DataResult deletedUser(@RequestBody @ApiParam(value = "用户id集合") List<String> userIds, HttpServletRequest request) {
        String userId = httpSessionService.getCurrentUserId();
        userService.deletedUsers(userIds, userId);
        return DataResult.success();
    }

    /**
     * 获取所有角色
     * @param userId
     * @return
     */
    @GetMapping("/user/roles/{userId}")
    @ApiOperation(value = "赋予角色-获取所有角色接口")
    @LogAnnotation(title = "用户管理", action = "赋予角色-获取所有角色接口")
    @RequiresPermissions("sys:user:role:detail")
    public DataResult<UserOwnRoleRespVO> getUserOwnRole(@PathVariable("userId") String userId) {
        DataResult<UserOwnRoleRespVO> result = DataResult.success();
        result.setData(userService.getUserOwnRole(userId));
        return result;
    }

    /**
     * 赋予角色
     * @param userId
     * @param roleIds
     * @return
     */
    @PutMapping("/user/roles/{userId}")
    @ApiOperation(value = "赋予角色-用户赋予角色接口")
    @LogAnnotation(title = "用户管理", action = "赋予角色-用户赋予角色接口")
    @RequiresPermissions("sys:user:update:role")
    public DataResult<UserOwnRoleRespVO> setUserOwnRole(@PathVariable("userId") String userId, @RequestBody List<String> roleIds) {
        DataResult result = DataResult.success();
        userService.setUserOwnRole(userId, roleIds);
        return result;
    }

    /**
     * 验证码
     * @param request
     * @param response
     */
    @ApiOperation(value = "生成验证码")
    @GetMapping(value = "/getVerify")
    public void getVerify(HttpServletRequest request, HttpServletResponse response) {
        response.setContentType("image/jpeg");//设置相应类型,告诉浏览器输出的内容为图片
        response.setHeader("Pragma", "No-cache");//设置响应头信息，告诉浏览器不要缓存此内容
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expire", 0);
        try {
            ImageCodeUtil randomValidateCode = new ImageCodeUtil();
            randomValidateCode.getRandcode(request, response);//输出验证码图片方法
        } catch (Exception e) {
            log.error("生成验证码失败");
        }
    }

    /**
     * 校验验证码
     * @param imageCode
     * @param session
     * @return
     */
    @ApiOperation(value = "校验验证码")
    @PostMapping(value = "/checkVerify")
    public DataResult checkVerify(@RequestParam String imageCode, HttpSession session) {
        //从session中获取随机数
        Object random = session.getAttribute(ImageCodeUtil.IMAGE_RANDOM_CODEKEY);
        if (random != null && String.valueOf(random).equals(imageCode)) {
            return DataResult.success();
        }
        return DataResult.fail("验证码输入有误");
    }

}
