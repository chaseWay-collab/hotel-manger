package com.example.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.common.aop.annotation.LogAnnotation;
import com.example.common.utils.Constants;
import com.example.common.utils.DataResult;
import com.example.entity.SysRole;
import com.example.service.RoleService;
import com.example.vo.request.RoleAddReqVO;
import com.example.vo.request.RoleUpdateReqVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * @ClassName: RoleController
 * @Description: 角色管理
 * @Author: [咖啡]
 * @Version: 1.0
 **/
@RequestMapping("/sys")
@RestController
@Api(tags = "组织模块-角色管理")
public class RoleController {
    @Autowired
    private RoleService roleService;

    /**
     * 新增
     * @param vo
     * @return
     */
    @PostMapping("/role")
    @ApiOperation(value = "新增角色接口")
    @LogAnnotation(title = "角色管理", action = "新增角色")
    @RequiresPermissions("sys:role:add")
    public DataResult<SysRole> addRole(@RequestBody @Valid RoleAddReqVO vo) {
        return DataResult.success(roleService.addRole(vo));
    }

    /**
     * 删除
     * @param id
     * @return
     */
    @DeleteMapping("/role/{id}")
    @ApiOperation(value = "删除角色接口")
    @LogAnnotation(title = "角色管理", action = "删除角色")
    @RequiresPermissions("sys:role:deleted")
    public DataResult deleted(@PathVariable("id") String id) {
        roleService.deletedRole(id);
        return DataResult.success();
    }

    /**
     * 更新
     * @param vo
     * @param request
     * @return
     */
    @PutMapping("/role")
    @ApiOperation(value = "更新角色信息接口")
    @LogAnnotation(title = "角色管理", action = "更新角色信息")
    @RequiresPermissions("sys:role:update")
    public DataResult updateDept(@RequestBody @Valid RoleUpdateReqVO vo, HttpServletRequest request) {
        roleService.updateRole(vo, request.getHeader(Constants.ACCESS_TOKEN));
        return DataResult.success();
    }

    /**
     * 详情
     * @param id
     * @return
     */
    @GetMapping("/role/{id}")
    @ApiOperation(value = "查询角色详情接口")
    @LogAnnotation(title = "角色管理", action = "查询角色详情")
    @RequiresPermissions("sys:role:detail")
    public DataResult<SysRole> detailInfo(@PathVariable("id") String id) {
        return DataResult.success(roleService.detailInfo(id));
    }

    /**
     * 列表
     * @param vo
     * @return
     */
    @PostMapping("/roles")
    @ApiOperation(value = "分页获取角色信息接口")
    @LogAnnotation(title = "角色管理", action = "分页获取角色信息")
    @RequiresPermissions("sys:role:list")
    public DataResult<IPage<SysRole>> pageInfo(@RequestBody SysRole vo) {
        return DataResult.success(roleService.pageInfo(vo));
    }

}
