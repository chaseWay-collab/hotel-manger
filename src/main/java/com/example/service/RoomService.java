package com.example.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.entity.RoomInfo;
import com.example.vo.response.RoomRespNodeVO;

import java.util.List;

/**
 * @ClassName: RoomService
 * @Description: RoomService
 * @Author: [咖啡]
 * @Version: 1.0
 **/
public interface RoomService {

    RoomInfo addRoom(RoomInfo vo);

    IPage<RoomInfo> pageInfo(RoomInfo vo);

    void updateRoom(RoomInfo vo);

    RoomInfo detailInfo(String id);

    void deletedRoom(String id);

    List<RoomInfo> getRoomListByType(List<String> typeIds);

    List<RoomInfo> selectAll();

    List<RoomRespNodeVO> roomTreeList(String id);
}
