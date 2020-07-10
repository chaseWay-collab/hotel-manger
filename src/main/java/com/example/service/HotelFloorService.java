package com.example.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.entity.hotel.HotelFloor;

import java.util.List;

/**
 * @ClassName: HotelFloorService
 * @Description: 楼层服务接口
 * @Author: [咖啡]
 * @Version: 1.0
 **/
public interface HotelFloorService extends IService<HotelFloor> {

    IPage<HotelFloor> pageInfo(HotelFloor vo);

    HotelFloor addHotelFloor(HotelFloor vo);

    void updateHotelFloor(HotelFloor vo);

    HotelFloor detailInfo(String id);

    void deletedHotelFloor(String id);

    List<HotelFloor> selectAll();

}
