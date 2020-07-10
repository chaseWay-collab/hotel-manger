package com.example.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.entity.hotel.RoomType;
import com.example.exception.MyException;
import com.example.exception.code.BaseResponseCode;
import com.example.mapper.RoomTypeMapper;
import com.example.service.RoomTypeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;

/**
 * @ClassName: RoomTypeServiceImpl
 * @Description:
 * @Author: [咖啡]
 * @Version: 1.0
 **/
@Transactional(rollbackFor = Exception.class)
@Service
@Slf4j
public class RoomTypeServiceImpl implements RoomTypeService {
    @Autowired
    private RoomTypeMapper roomTypeMapper;

    @Override
    public List<RoomType> selectAll() {
        List<RoomType> list = roomTypeMapper.selectList(new QueryWrapper<>());
        for (RoomType roomType : list) {
            System.out.println("[RoomTypeServiceImpl]selectAll(28): " + roomType);
        }
        return list;
    }

    /**
     * 房型详情
     * @param id
     * @return
     */
    @Override
    public RoomType detailInfo(String id) {
        RoomType roomType = roomTypeMapper.selectById(id);
        if (roomType == null){
            log.error("传入 的 id:{}不合法", id);
            throw new MyException(BaseResponseCode.DATA_ERROR);
        }
        return roomType;
    }

    /**
     * 房型分页列表
     * @param vo
     * @return
     */
    @Override
    public IPage<RoomType> pageInfo(RoomType vo) {
        Page<RoomType> page = new Page<>(vo.getPage(), vo.getLimit());
        QueryWrapper<RoomType> queryWrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(vo.getRtName())){
            queryWrapper.like("rt_name", vo.getRtName());
        }
        if (!StringUtils.isEmpty(vo.getRtPrice())){
            queryWrapper.like("rt_price", vo.getRtPrice());
        }
        if (!StringUtils.isEmpty(vo.getRtBtype())){
            queryWrapper.like("rt_btype", vo.getRtBtype());
        }
        queryWrapper.orderByDesc("create_time");
        IPage<RoomType> iPage = roomTypeMapper.selectPage(page, queryWrapper);
        return iPage;
    }

    /**
     * 新增房型
     * @param vo
     * @return
     */
    @Override
    public RoomType addRoomType(RoomType vo) {
        RoomType roomType = new RoomType();
        vo.setCreateTime(new Date());
        vo.setRtPic("/images/rooms/8.jpg");
        BeanUtils.copyProperties(vo, roomType);
        int count = roomTypeMapper.insert(roomType);
        if (count != 1){
            throw new MyException(BaseResponseCode.OPERATION_ERRO);  // 操作失败
        }
        return roomType;
    }

    /**
     * 删除房型
     * @param id
     */
    @Override
    public void deletedRoomType(String id) {
        QueryWrapper<RoomType> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("rt_id", id);
        roomTypeMapper.deleteById(queryWrapper);
    }

    /**
     * 更新房型信息
     * @param vo
     */
    @Override
    public void updateRoomType(RoomType vo) {
        System.out.println("[RoomTypeServiceIMpl]updateRoomType(118)vo: " + vo);
        if(null == vo.getRtId()){
            log.error("传入 的 id:{}不合法", vo.getRtId());
            throw new MyException(BaseResponseCode.DATA_ERROR);
        }
        RoomType update = new RoomType();
        BeanUtils.copyProperties(vo, update);
        update.setUpdateTime(new Date());
        int count = roomTypeMapper.updateById(update);
        if (count != 1) {
            throw new MyException(BaseResponseCode.OPERATION_ERRO);
        }
    }

}
