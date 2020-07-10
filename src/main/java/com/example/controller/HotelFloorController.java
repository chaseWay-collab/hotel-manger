package com.example.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.common.aop.annotation.LogAnnotation;
import com.example.common.utils.DataResult;
import com.example.entity.hotel.HotelFloor;
import com.example.service.HotelFloorService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @ClassName: HotelFloorController
 * @Description: 楼层控制类
 * @Author: [咖啡]
 * @Version: 1.0
 **/
@RestController
@Api(tags = "酒店管理-楼层列表")
@RequestMapping("/hotel")
public class HotelFloorController {

    @Autowired
    private HotelFloorService hotelFloorService;

    /**
     * 列表
     * @param vo
     * @return
     */
    @PostMapping("/floors")
    @ApiOperation(value = "分页获取楼层列表接口")
    @LogAnnotation(title = "楼层列表", action = "楼层信息")
    @RequiresPermissions("hotel:floor:list")
    public DataResult<IPage<HotelFloor>> pageInfo(@RequestBody HotelFloor vo){
        return DataResult.success(hotelFloorService.pageInfo(vo));
    }

    /**
     * 新增
     * @param vo
     * @return
     */
    @PostMapping("/floor")
    @ApiOperation(value = "新增楼层接口")
    @LogAnnotation(title = "楼层列表", action = "新增楼层")
    @RequiresPermissions("hotel:floor:add")
    public DataResult<HotelFloor> addFloor(@RequestBody @Valid HotelFloor vo) {
        //selectAll();
        return DataResult.success(hotelFloorService.addHotelFloor(vo));
    }

    /**
     * 删除
     * @param id
     * @return
     */
    @DeleteMapping("/floor/{id}")
    @ApiOperation(value = "删除楼层接口")
    @LogAnnotation(title = "楼层列表", action = "删除楼层")
    @RequiresPermissions("hotel:floor:deleted")
    public DataResult<HotelFloor> deleted(@PathVariable("id") String id) {
        hotelFloorService.deletedHotelFloor(id);
        return DataResult.success();
    }

    /**
     * 更新
     * @param vo
     * @return
     */
    @PutMapping("/floor")
    @ApiOperation(value = "编辑楼层接口")
    @LogAnnotation(title = "楼层列表", action = "编辑楼层")
    @RequiresPermissions("hotel:floor:update")
    public DataResult<HotelFloor> updateFloor(@RequestBody HotelFloor vo){
        if(StringUtils.isEmpty(vo.getFId())){
            return DataResult.fail("id不能为空");
        }
        hotelFloorService.updateHotelFloor(vo);
        return DataResult.success();
    }

    /**
     * 列表
     * @return
     */
    @DeleteMapping("/allFloor")
    @ApiOperation(value = "获取所有楼层")
    @LogAnnotation(title = "楼层列表", action = "选择所有楼层")
    //@RequiresPermissions("hotel:floor:getall")
    public DataResult<HotelFloor> selectAll() {
        hotelFloorService.selectAll();
        return DataResult.success();
    }

}
