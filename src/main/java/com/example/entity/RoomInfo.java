package com.example.entity;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.example.entity.hotel.RoomType;
import com.example.vo.request.PageReqVO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @ClassName: Roominfo
 * @Description: 房间基本信息
 * @Author: [咖啡]
 * @Version: 1.0
 **/
@EqualsAndHashCode(callSuper = true)
@Data
public class RoomInfo extends PageReqVO implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.ASSIGN_UUID)
    private String rId;  // 房间信息ID

    private String rtId;  // 房间类型ID

    private String rNum;  // 房间号码

    private Integer rState;  // 房间状态

    private Integer rPdwd;  // 是否有窗

    private Integer fId;  // 楼层ID
    
    //@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    //@JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date rReg;  // 修改时间

    private Date createTime;

    private Date updateTime;

    private String descript;

    //@NotBlank(message = "父级不能为空")
    //private String pid;

    @TableField(exist = false)
    private RoomType roomType;

    @TableField(exist = false)
    private String rtName;
    
    //private Hotelf hotelf;

    @TableField(exist = false)
    private String startTime;

    @TableField(exist = false)
    private String endTime;

}