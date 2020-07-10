package com.example.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.common.aop.annotation.LogAnnotation;
import com.example.common.utils.DataResult;
import com.example.entity.RoomInfo;
import com.example.service.RoomService;
import com.example.vo.response.RoomRespNodeVO;
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
 * @ClassName: RoomController
 * @Description: 房间管理
 * @Author: [咖啡]
 * @Version: 1.0
 **/
@RestController
@Api(tags = "酒店管理-房间列表")
@RequestMapping("/sys")
public class RoomController {
    @Autowired
    private RoomService roomService;

    /**
     * 详情
     * @param id
     * @return
     */
    @GetMapping("/room/{id}")
    @ApiOperation(value = "查询房间详情")
    @LogAnnotation(title = "房间列表", action = "查询房间详情")
    @RequiresPermissions("sys:room:detail")
    public DataResult<RoomInfo> detailInfo(@PathVariable("id") String id){
        return DataResult.success(roomService.detailInfo(id));
    }

    /**
     * 列表
     * @param vo
     * @return
     */
    @PostMapping("/rooms")
    @ApiOperation(value = "分页获取房间信息接口")
    @LogAnnotation(title = "房间列表", action = "分页获取房间信息")
    @RequiresPermissions("sys:room:list")
    public DataResult<IPage<RoomInfo>> pageInfo(@RequestBody RoomInfo vo){
        return DataResult.success(roomService.pageInfo(vo));
    }

    /**
     * 新增
     * @param vo
     * @return
     */
    @PostMapping("/room")
    @ApiOperation(value = "新增房间接口")
    @LogAnnotation(title = "房间列表", action = "新增房间")
    @RequiresPermissions("sys:room:add")
    public DataResult<RoomInfo> addRole(@RequestBody @Valid RoomInfo vo) {
        return DataResult.success(roomService.addRoom(vo));
    }

    /**
     * 删除
     * @param id
     * @return
     */
    @DeleteMapping("/room/{id}")
    @ApiOperation(value = "删除房间接口")
    @LogAnnotation(title = "房间列表", action = "删除房间")
    @RequiresPermissions("sys:room:deleted")  // sys:room:deleted
    public DataResult<RoomInfo> deleted(@PathVariable("id") String id) {
        roomService.deletedRoom(id);
        return DataResult.success();
    }

    /**
     * 更新
     * @param vo
     * @return
     */
    @PutMapping("/room")
    @ApiOperation(value = "更新房间信息接口")
    @LogAnnotation(title = "房间列表", action = "更新房间信息")
    @RequiresPermissions("sys:room:update")
    public DataResult<RoomInfo> updateRoom(@RequestBody @Valid RoomInfo vo) {
        if (StringUtils.isEmpty(vo.getRId())){
            return DataResult.fail("id为空");
        }
        System.out.println("[RoomCtrl]updateRoom(81)" + vo.getRId());
        roomService.updateRoom(vo);
        return DataResult.success();
    }

    /**
     * 房型列表
     * @param rtId id
     * @return
     */
    @GetMapping("/roomType/tree")
    @ApiOperation(value = "房间类型列表接口")
    @LogAnnotation(title = "房间列表", action = "房间类型列表")
    @RequiresPermissions(value = {"sys:room:update", "sys:room:add"}, logical = Logical.OR)
    public DataResult<List<RoomRespNodeVO>> getTree(@RequestParam(required = false) String rtId) {
        System.out.println("[RoomController]getTree(86): " + rtId);
        DataResult<List<RoomRespNodeVO>> success = DataResult.success(roomService.roomTreeList(rtId));
        //System.out.println("[RoomController]getTree(92): " + success);
        return DataResult.success(roomService.roomTreeList(rtId));
    }

}
