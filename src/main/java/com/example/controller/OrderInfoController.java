package com.example.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.common.aop.annotation.LogAnnotation;
import com.example.common.utils.DataResult;
import com.example.entity.hotel.OrderInfo;
import com.example.service.OrderInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @ClassName: OrderInfoController
 * @Description: 订单
 * @Author: [咖啡]
 * @Version: 1.0
 **/
@RestController
@Api(tags = "酒店管理-订单列表")
@RequestMapping("/hotel")
public class OrderInfoController {

    @Autowired
    private OrderInfoService orderInfoService;

    /**
     * 列表
     * @param vo
     * @return
     */
    @PostMapping("/orders")
    @ApiOperation(value = "分页获取订单列表接口")
    @LogAnnotation(title = "订单列表", action = "订单信息")
    @RequiresPermissions("hotel:order:list")
    public DataResult<IPage<OrderInfo>> pageInfo(@RequestBody OrderInfo vo){
        return DataResult.success(orderInfoService.pageInfo(vo));
    }

    /**
     * 新增
     * @param vo
     * @return
     */
    @PostMapping("/order")
    @ApiOperation(value = "新增订单接口")
    @LogAnnotation(title = "订单列表", action = "新增订单")
    @RequiresPermissions("hotel:order:add")
    public DataResult<OrderInfo> addRole(@RequestBody @Valid OrderInfo vo) {
        //selectAll();
        return DataResult.success(orderInfoService.addOrderInfo(vo));
    }

    /**
     * 删除
     * @param id
     * @return
     */
    @DeleteMapping("/order/{id}")
    @ApiOperation(value = "删除订单接口")
    @LogAnnotation(title = "订单列表", action = "删除订单")
    @RequiresPermissions("hotel:order:deleted")
    public DataResult<OrderInfo> deleted(@PathVariable("id") String id) {
        orderInfoService.deletedOrderInfo(id);
        return DataResult.success();
    }

    /**
     * 更新
     * @param vo
     * @return
     */
    @PutMapping("/order")
    @ApiOperation(value = "更新信息接口")
    @LogAnnotation(title = "列表", action = "更新信息")
    @RequiresPermissions("hotel:order:update")
    public DataResult<OrderInfo> updateOrder(@RequestBody @Valid OrderInfo vo) {
        if (StringUtils.isEmpty(vo.getOId())){
            return DataResult.fail("id为空");
        }
        System.out.println("[OrderInfoController]updateOrder(69)" + vo.getOId());
        orderInfoService.updateOrderInfo(vo);
        return DataResult.success();
    }



}
