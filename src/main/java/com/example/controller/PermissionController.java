package com.example.controller;

import com.example.common.aop.annotation.LogAnnotation;
import com.example.common.utils.DataResult;
import com.example.entity.system.SysPermission;
import com.example.service.PermissionService;
import com.example.vo.response.PermissionRespNode;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @ClassName: PermissionController
 * @Description: 返回菜单视图
 * @Author: [咖啡]
 * @Version: 1.0
 **/
@RequestMapping("/sys")
@RestController
@Api(tags = "组织模块-菜单权限管理")
public class PermissionController {
    @Autowired
    private PermissionService permissionService;

    /**
     * 新增
     * @param vo
     * @return
     */
    @PostMapping("/permission")
    @ApiOperation(value = "新增菜单权限接口")
    @LogAnnotation(title = "菜单权限管理", action = "新增菜单权限")
    @RequiresPermissions("sys:permission:add")
    public DataResult addPermission(@RequestBody @Valid SysPermission vo) {
        return DataResult.success(permissionService.addPermission(vo));
    }

    /**
     * 删除
     * @param id
     * @return
     */
    @DeleteMapping("/permission/{id}")
    @ApiOperation(value = "删除菜单权限接口")
    @LogAnnotation(title = "菜单权限管理", action = "删除菜单权限")
    @RequiresPermissions("sys:permission:deleted")
    public DataResult deleted(@PathVariable("id") String id) {
        permissionService.deleted(id);
        return DataResult.success();
    }

    /**
     * 更新
     * @param vo
     * @return
     */
    @PutMapping("/permission")
    @ApiOperation(value = "更新菜单权限接口")
    @LogAnnotation(title = "菜单权限管理", action = "更新菜单权限")
    @RequiresPermissions("sys:permission:update")
    public DataResult updatePermission(@RequestBody @Valid SysPermission vo) {
        if (StringUtils.isEmpty(vo.getId())) {
            return DataResult.fail("id不能为空");
        }
        permissionService.updatePermission(vo);
        return DataResult.success();
    }

    /**
     * 详情
     * @param id
     * @return
     */
    @GetMapping("/permission/{id}")
    @ApiOperation(value = "查询菜单权限接口")
    @LogAnnotation(title = "菜单权限管理", action = "查询菜单权限")
    @RequiresPermissions("sys:permission:detail")
    public DataResult<SysPermission> detailInfo(@PathVariable("id") String id) {
        return DataResult.success(permissionService.detailInfo(id));

    }

    /**
     * 列表
     * @return
     */
    @GetMapping("/permissions")
    @ApiOperation(value = "获取所有菜单权限接口")
    @LogAnnotation(title = "菜单权限管理", action = "获取所有菜单权限")
    @RequiresPermissions("sys:permission:list")
    public DataResult<List<SysPermission>> getAllMenusPermission() {
        return DataResult.success(permissionService.selectAll());
    }

    /**
     * 权限树
     * @param permissionId
     * @return
     */
    @GetMapping("/permission/tree")
    @ApiOperation(value = "获取所有权限树接口")
    @LogAnnotation(title = "菜单权限管理", action = "获取所有目录菜单树")
    @RequiresPermissions(value = {"sys:permission:update", "sys:permission:add"}, logical = Logical.OR)  // 要求subject必须有的权限
    public DataResult<List<PermissionRespNode>> getAllMenusPermissionTree(@RequestParam(required = false) String permissionId) {
        return DataResult.success(permissionService.selectAllMenuByTree(permissionId));
    }

    /**
     * 角色树
     * @return
     */
    @GetMapping("/permission/tree/all")
    @ApiOperation(value = "获取所有目录菜单树接口")
    @LogAnnotation(title = "角色管理", action = "获取所有角色树")
    @RequiresPermissions(value = {"sys:role:update", "sys:role:add"}, logical = Logical.OR)
    public DataResult<List<PermissionRespNode>> getAllPermissionTree() {
        return DataResult.success(permissionService.selectAllByTree());
    }
}
