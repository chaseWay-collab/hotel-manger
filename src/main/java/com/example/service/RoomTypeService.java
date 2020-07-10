package com.example.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.entity.hotel.RoomType;

import java.util.List;

/**
 * @ClassName: RoomTypeService
 * @Description:
 * @Author: [咖啡]
 * @Version: 1.0
 **/
public interface RoomTypeService {

    List<RoomType> selectAll();

    RoomType detailInfo(String id);

    IPage<RoomType> pageInfo(RoomType vo);

    RoomType addRoomType(RoomType vo);

    void deletedRoomType(String id);

    void updateRoomType(RoomType vo);
}
