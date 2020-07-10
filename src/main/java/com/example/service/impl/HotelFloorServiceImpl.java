package com.example.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.entity.hotel.HotelFloor;
import com.example.exception.MyException;
import com.example.exception.code.BaseResponseCode;
import com.example.mapper.HotelFloorMapper;
import com.example.mapper.RoomInfoMapper;
import com.example.service.HotelFloorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;

/**
 * @ClassName: HotelFloorServiceImpl
 * @Description: 楼层服务实现类
 * @Author: [咖啡]
 * @Version: 1.0
 **/
@Transactional(rollbackFor = Exception.class)
@Service
@Slf4j
public class HotelFloorServiceImpl extends ServiceImpl<HotelFloorMapper, HotelFloor> implements HotelFloorService {
    @Autowired
    private HotelFloorMapper hotelFloorMapper;
    @Autowired
    private RoomInfoMapper roomInfoMapper;

    /**
     * 获取分页
     * @param vo
     * @return
     */
    @Override
    public IPage<HotelFloor> pageInfo(HotelFloor vo) {
        Page<HotelFloor> page = new Page<>(vo.getPage(), vo.getLimit());
        QueryWrapper<HotelFloor> queryWrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(vo.getFNo())) {
            queryWrapper.like("f_no", vo.getFNo());  // 比较值是否相等
        }
        if (!StringUtils.isEmpty(vo.getFName())) {
            queryWrapper.like("f_name", vo.getFName());
        }
        queryWrapper.orderByDesc("f_no");
        IPage<HotelFloor> iPage = hotelFloorMapper.selectPage(page, queryWrapper);
        return iPage;
    }

    /**
     * 新增
     * @param vo
     * @return
     */
    @Override
    public HotelFloor addHotelFloor(HotelFloor vo) {
        HotelFloor hotelFloor = new HotelFloor();
        vo.setCreateTime(new Date());
        BeanUtils.copyProperties(vo, hotelFloor);  // 对象中的属性值互拷
        int count = hotelFloorMapper.insert(hotelFloor);
        if (count != 1){
            throw new MyException(BaseResponseCode.OPERATION_ERRO);  // 操作失败
        }
        return hotelFloor;
    }

    /**
     * 更新
     * @param vo
     */
    @Override
    public void updateHotelFloor(HotelFloor vo) {
        HotelFloor hotelFloor = hotelFloorMapper.selectById(vo.getFId());
        if (null == hotelFloor){
            log.error("传入 的 id:{}不合法", vo.getFId());
            throw new MyException(BaseResponseCode.DATA_ERROR);
        }
        //System.out.println("[HotelFloorServiceImpl]updateHotelFloor(97): " + hotelFloor);
        HotelFloor update = new HotelFloor();
        BeanUtils.copyProperties(vo, update);
        update.setUpdateTime(new Date());
        int count = hotelFloorMapper.updateById(update);
        if (count != 1) {
            throw new MyException(BaseResponseCode.OPERATION_ERRO);
        }
    }

    /**
     * 详情
     * @param id
     * @return
     */
    @Override
    public HotelFloor detailInfo(String id) {
        //System.out.println("[HotelFloorServiceImpl]id: " + id);
        HotelFloor hotelFloor = hotelFloorMapper.selectById(id);
        //System.out.println("[HotelFloorServiceImpl]roominfo1: " + hotelFloor);
        if (hotelFloor == null) {
            log.error("传入 的 id:{}不合法", id);
            throw new MyException(BaseResponseCode.DATA_ERROR);
        }
        return hotelFloor;
    }

    /**
     *  删除
     * @param fId
     */
    @Override
    public void deletedHotelFloor(String fId) {
        QueryWrapper<HotelFloor> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("f_id", fId);
        hotelFloorMapper.delete(queryWrapper);
    }

    @Override
    public List<HotelFloor> selectAll() {
        return hotelFloorMapper.selectList(new QueryWrapper<>());
    }
}
