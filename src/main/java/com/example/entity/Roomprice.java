package com.example.entity;

import java.util.Date;

import com.example.entity.hotel.RoomType;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * @ClassName: Roomprice
 * @Description: 房间价格
 * @Author: [咖啡]
 * @Version: 1.0
 **/
@Data
public class Roomprice {
    private Integer rpId;

    private Integer rtId;

    @DateTimeFormat(pattern="yyyy-MM-dd")
    @JSONField(format="yyyy-MM-dd")
    private Date rpStime;
    
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @JSONField(format="yyyy-MM-dd")
    private Date rpEtime;

    private Integer rpState;

    private Integer rpPrice;

    private String rpMsg;
    
    private RoomType roomtype;

}