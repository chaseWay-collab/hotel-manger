package com.example.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.entity.hotel.OrderInfo;

import java.util.List;

/**
 * @ClassName: OrderInfoService
 * @Description: 订单接口
 * @Author: [咖啡]
 * @Version: 1.0
 **/
public interface OrderInfoService extends IService<OrderInfo> {

    IPage<OrderInfo> pageInfo(OrderInfo vo);

    OrderInfo addOrderInfo(OrderInfo vo);

    void updateOrderInfo(OrderInfo vo);

    OrderInfo detailInfo(String oId);

    void deletedOrderInfo(String oId);

    List<OrderInfo> selectAll();


}
