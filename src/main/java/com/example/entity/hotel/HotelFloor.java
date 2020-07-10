package com.example.entity.hotel;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.example.vo.request.PageReqVO;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @ClassName: HotelFloor
 * @Description: 楼层
 * @Author: [咖啡]
 * @Version: 1.0
 **/
@Data
public class HotelFloor extends PageReqVO implements Serializable {

    @TableId(type = IdType.ASSIGN_UUID)
    private String fId;

    private String hoId;

    private String fMsg;

    private Integer fNo;

    private String fName;

    private Date createTime;

    private Date updateTime;

    //@TableField(exist = false)
    //private Integer status;

    @TableField(exist = false)
    private String startTime;

    @TableField(exist = false)
    private String endTime;

    @TableLogic
    private Integer deleted;

}