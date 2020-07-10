package com.example.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.entity.RoomInfo;
import org.apache.ibatis.annotations.One;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

/**
 * @ClassName: RoomInfoMapper
 * @Description: RoomInfoMapper
 * @Author: [咖啡]
 * @Version: 1.0
 **/
@Repository
public interface RoomInfoMapper extends BaseMapper<RoomInfo> {

//    @Select("SELECT ri.*, rt.rt_name FROM room_info ri LEFT JOIN room_type rt ON ri.rt_id = rt.rt_id WHERE r_id=#{id}")
//    RoomInfo findRoomInfoById(String id);

}
