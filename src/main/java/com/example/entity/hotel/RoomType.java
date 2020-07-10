package com.example.entity.hotel;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.example.vo.request.PageReqVO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;

/**
 * @ClassName: Roomtype
 * @Description: 房间类型
 * @Author: [咖啡]
 * @Version: 1.0
 **/
@EqualsAndHashCode(callSuper = true)
@Data
public class RoomType extends PageReqVO implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.ASSIGN_UUID)
    private String rtId;

    private String rtName;

    private Integer rtPdbr;

    private Integer rtPrice;

    private Integer rtPdwd;

    private String rtPic;

    private String rtSq;

    private Integer rtPcou;

    private String rtBtype;

    private String rtMsg;

    //private Integer hoId;

    private Integer rtState;

    private Integer rtNum;

    //private String rtHour;
    
    //@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    //@JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date rtReg;

    private String rtBath;

    private String rtMedia;

    private String uId;

    private Date createTime;

    private Date updateTime;

    private String pid;

}