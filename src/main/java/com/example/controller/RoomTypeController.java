package com.example.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.common.aop.annotation.LogAnnotation;
import com.example.common.utils.DataResult;
import com.example.entity.RoomInfo;
import com.example.entity.hotel.RoomType;
import com.example.service.RoomTypeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @ClassName: RoomTypeController
 * @Description: 房型管理
 * @Author: [咖啡]
 * @Version: 1.0
 **/
@RestController
@Api(tags = "酒店管理-房型列表")
@RequestMapping("/hotel")
public class RoomTypeController {
    @Autowired
    private RoomTypeService roomTypeService;

    /**
     * 详情
     * @param id
     * @return
     */
    @GetMapping("/roomType/{id}")
    @ApiOperation(value = "查询房型详情")
    @LogAnnotation(title = "房型列表", action = "查询房型详情")
    @RequiresPermissions("hotel:roomType:detail")
    public DataResult<RoomType> detailInfo(@PathVariable("id") String id){
        return DataResult.success(roomTypeService.detailInfo(id));
    }

    /**
     * 列表
     * @param vo
     * @return
     */
    @PostMapping("/roomTypes")
    @ApiOperation(value = "分页获取房型信息接口")
    @LogAnnotation(title = "房型列表", action = "分页获取房型信息")
    @RequiresPermissions("hotel:roomType:list")
    public DataResult<IPage<RoomType>> pageInfo(@RequestBody RoomType vo){
        return DataResult.success(roomTypeService.pageInfo(vo));
    }

    /**
     * 新增
     * @param vo
     * @return
     */
    @PostMapping("/roomType")
    @ApiOperation(value = "新增房型接口")
    @LogAnnotation(title = "房型列表", action = "新增房型")
    @RequiresPermissions("hotel:roomType:add")
    public DataResult<RoomType> addRole(@RequestBody @Valid RoomType vo) {
        return DataResult.success(roomTypeService.addRoomType(vo));
    }

    /**
     * 删除
     * @param id
     * @return
     */
    @DeleteMapping("/roomType/{id}")
    @ApiOperation(value = "删除房型接口")
    @LogAnnotation(title = "房型列表", action = "删除房型")
    @RequiresPermissions("hotel:roomType:deleted")  // sys:room:deleted
    public DataResult<RoomInfo> deleted(@PathVariable("id") String id) {
        roomTypeService.deletedRoomType(id);
        return DataResult.success();
    }

    /**
     * 更新
     * @param vo
     * @return
     */
    @PutMapping("/roomType")
    @ApiOperation(value = "更新房间信息接口")
    @LogAnnotation(title = "房间列表", action = "更新房间信息")
    @RequiresPermissions("hotel:roomType:update")
    public DataResult<RoomInfo> updateRoom(@RequestBody @Valid RoomType vo) {
        if (StringUtils.isEmpty(vo.getRtId())){
            return DataResult.fail("id为空");
        }
        roomTypeService.updateRoomType(vo);
        return DataResult.success();
    }

}
