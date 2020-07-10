package com.example.entity.hotel;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @ClassName: Hotelinfo
 * @Description: 酒店信息
 * @Author: [咖啡]
 * @Version: 1.0
 **/
@Data
public class Hotelinfo {
    private Integer hoId;

    private Integer hoType;

    private Double hoClevel;

    private String hoTel;

    private String hoUsertel;

    private String hoAddress;

    private String hoIndexpic;

    private Date hoOtime;

    private String hoUsername;

    private String hoUserid;

    private String hoMsg;

    private Double hoAddressx;

    private Double hoAddressy;

    private String hoKeyword;

    private String hoAddresss;

    private String hoTraffic;

    private Date hoReg;

    private String hoAddkey;

    private String hoIn;

    private String hoOut;

    private Double hoPingw;

    private Double hoPingf;

    private Integer usId;

    private Integer uId;

    private String hoName;
    
    //private Userinfo userinfo;
    
    private List<RoomType> roomtype;
    
    private Integer lowPriceForHotelInfo;
    
    private Integer countOfHotelPing;

}