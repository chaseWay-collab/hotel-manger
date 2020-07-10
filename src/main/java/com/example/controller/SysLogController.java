package com.example.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.common.aop.annotation.LogAnnotation;
import com.example.common.utils.DataResult;
import com.example.entity.system.SysLog;
import com.example.service.LogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @ClassName: SysLogController
 * @Description: 日志管理
 * @Author: [咖啡]
 * @Version: 1.0
 **/
@RequestMapping("/sys")
@Api(tags = "系统模块-系统操作日志管理")
@RestController
public class SysLogController {

    @Autowired
    private LogService logService;

    /**
     * 列表
     * @param vo
     * @return
     */
    @PostMapping("/logs")
    @ApiOperation(value = "分页查询系统操作日志接口")
    @LogAnnotation(title = "系统操作日志管理", action = "分页查询系统操作日志")
    @RequiresPermissions("sys:log:list")
    public DataResult<IPage<SysLog>> pageInfo(@RequestBody SysLog vo) {
        return DataResult.success(logService.pageInfo(vo));
    }

    /**
     * 删除
     * @param logIds
     * @return
     */
    @DeleteMapping("/logs")
    @ApiOperation(value = "删除日志接口")
    @LogAnnotation(title = "系统操作日志管理", action = "删除系统操作日志")
    @RequiresPermissions("sys:log:deleted")
    public DataResult deleted(@RequestBody List<String> logIds) {
        logService.deleted(logIds);
        return DataResult.success();
    }
}
