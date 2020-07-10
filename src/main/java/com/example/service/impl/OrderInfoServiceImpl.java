package com.example.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.entity.hotel.OrderInfo;
import com.example.entity.hotel.RoomType;
import com.example.exception.MyException;
import com.example.exception.code.BaseResponseCode;
import com.example.mapper.OrderInfoMapper;
import com.example.mapper.RoomTypeMapper;
import com.example.service.OrderInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;

/**
 * @ClassName: OrderInfoServiceImpl
 * @Description: 订单实现类
 * @Author: [咖啡]
 * @Version: 1.0
 **/
@Transactional(rollbackFor = Exception.class)
@Service
@Slf4j
public class OrderInfoServiceImpl extends ServiceImpl<OrderInfoMapper, OrderInfo> implements OrderInfoService {
    @Autowired
    private OrderInfoMapper orderInfoMapper;
    @Autowired
    private RoomTypeMapper roomTypeMapper;

    /**
     * 获取分页
     * @param vo
     * @return
     */
    @Override
    public IPage<OrderInfo> pageInfo(OrderInfo vo) {
        Page<OrderInfo> page = new Page<>(vo.getPage(), vo.getLimit());
        QueryWrapper<OrderInfo> queryWrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(vo.getONo())) {
            queryWrapper.like("o_no", vo.getONo());  // 比较值是否相等
        }
        queryWrapper.orderByDesc("create_time");
        List<OrderInfo> orderInfos = orderInfoMapper.selectList(queryWrapper);
        //System.out.println("[OrderInfoServiceImpl]pageInfo(51)" + orderInfos);
        IPage<OrderInfo> iPage = orderInfoMapper.selectPage(page, queryWrapper);
        //System.out.println("[OrderInfoServiceImpl]pageInfo(53)" + iPage.getRecords());
        if(!iPage.getRecords().isEmpty()){
            for (OrderInfo orderInfo: iPage.getRecords()) {
                RoomType roomType = roomTypeMapper.selectById(orderInfo.getRtId());
                if (roomType!=null){
                    //System.out.println("" + roomType);
                    orderInfo.setRtName(roomType.getRtName());
                }
            }
        }
        return iPage;
    }

    /**
     * 新增
     * @param vo
     * @return
     */
    @Override
    public OrderInfo addOrderInfo(OrderInfo vo) {
        OrderInfo orderInfo = new OrderInfo();
        vo.setCreateTime(new Date());
        BeanUtils.copyProperties(vo, orderInfo);
        int cnt = orderInfoMapper.insert(orderInfo);
        if (cnt == 1){
            throw new MyException(BaseResponseCode.OPERATION_ERRO);
        }
        return orderInfo;
    }

    /**
     * 更新
     * @param vo
     */
    @Override
    public void updateOrderInfo(OrderInfo vo) {
        OrderInfo orderInfo = orderInfoMapper.selectById(vo.getOId());
        if (null == orderInfo){
            log.error("传入 的 id:{}不合法", vo.getOId());
            throw new MyException(BaseResponseCode.DATA_ERROR);
        }
        //System.out.println("[OrderInfoServiceImpl]updateOrderInfo(101): " + orderInfo);
        OrderInfo update = new OrderInfo();
        BeanUtils.copyProperties(vo, update);
        //System.out.println("[RoomServiceImpl]updateRoom(108): " + roomInfo);
        update.setUpdateTime(new Date());
        int count = orderInfoMapper.updateById(update);
        if (count != 1) {
            throw new MyException(BaseResponseCode.OPERATION_ERRO);
        }
    }

    /**
     * 详情
     * @param oId
     * @return
     */
    @Override
    public OrderInfo detailInfo(String oId) {
        OrderInfo orderInfo = orderInfoMapper.selectById(oId);
        if (orderInfo == null) {
            log.error("传入 的 id:{}不合法", oId);
            throw new MyException(BaseResponseCode.DATA_ERROR);
        }
        return orderInfo;
    }

    /**
     * 删除订单
     * @param oId
     */
    @Override
    public void deletedOrderInfo(String oId) {
        QueryWrapper<OrderInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("o_id", oId);
        orderInfoMapper.delete(queryWrapper);
    }

    @Override
    public List<OrderInfo> selectAll() {
        return orderInfoMapper.selectList(new QueryWrapper<>());
    }
}
