package com.example.controller;

import com.example.common.aop.annotation.LogAnnotation;
import com.example.common.utils.DataResult;
import com.example.entity.system.SysDept;
import com.example.service.DeptService;
import com.example.vo.response.DeptRespNodeVO;
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
 * @ClassName: DeptController
 * @Description: DeptController
 * @Author: [咖啡]
 * @Version: 1.0
 **/
@RequestMapping("/sys")
@RestController
@Api(tags = "组织模块-机构管理")
public class DeptController {
    @Autowired
    private DeptService deptService;

    /**
     * 新增
     * @param vo 部门信息
     * @return SysDept
     */
    @PostMapping("/dept")
    @ApiOperation(value = "新增组织接口")
    @LogAnnotation(title = "机构管理", action = "新增组织")
    @RequiresPermissions("sys:dept:add")
    public DataResult<SysDept> addDept(@RequestBody @Valid SysDept vo) {
        return DataResult.success(deptService.addDept(vo));
    }

    /**
     * 删除
     * @param id 部门id
     * @return
     */
    @DeleteMapping("/dept/{id}")
    @ApiOperation(value = "删除组织接口")
    @LogAnnotation(title = "机构管理", action = "删除组织")
    @RequiresPermissions("sys:dept:deleted")
    public DataResult<SysDept> deleted(@PathVariable("id") String id) {
        deptService.deleted(id);
        return DataResult.success();
    }

    /**
     * 更新
     * @param vo
     * @return
     */
    @PutMapping("/dept")
    @ApiOperation(value = "更新组织信息接口")
    @LogAnnotation(title = "机构管理", action = "更新组织信息")
    @RequiresPermissions("sys:dept:update")
    public DataResult updateDept(@RequestBody SysDept vo) {
        if (StringUtils.isEmpty(vo.getId())) {
            return DataResult.fail("id不能为空");
        }
        deptService.updateDept(vo);
        return DataResult.success();
    }

    /**
     * 详情
     * @param id
     * @return
     */
    @GetMapping("/dept/{id}")
    @ApiOperation(value = "查询组织详情接口")
    @LogAnnotation(title = "机构管理", action = "查询组织详情")
    @RequiresPermissions("sys:dept:detail")
    public DataResult<SysDept> detailInfo(@PathVariable("id") String id) {
        return DataResult.success(deptService.detailInfo(id));
    }

    /**
     * 树
     * @param deptId
     * @return
     */
    @GetMapping("/dept/tree")
    @ApiOperation(value = "树型组织列表接口")
    @LogAnnotation(title = "机构管理", action = "树型组织列表")
    @RequiresPermissions(value = {"sys:user:update", "sys:user:add", "sys:dept:add", "sys:dept:update"}, logical = Logical.OR)
    public DataResult<List<DeptRespNodeVO>> getTree(@RequestParam(required = false) String deptId) {
        return DataResult.success(deptService.deptTreeList(deptId));
    }

    /**
     * 列表
     * @return
     */
    @GetMapping("/depts")
    @ApiOperation(value = "获取机构列表接口")
    @LogAnnotation(title = "机构管理", action = "获取所有组织机构")
    @RequiresPermissions("sys:dept:list")
    public DataResult<List<SysDept>> getDeptAll() {
        return DataResult.success(deptService.selectAll());    }
}
