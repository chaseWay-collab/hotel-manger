package com.example.entity.hotel;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.example.entity.hotel.Hotelinfo;
import com.example.vo.request.PageReqVO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * @ClassName: OrderInfo
 * @Description: 订单实体
 * @Author: [咖啡]
 * @Version: 1.0
 **/
@EqualsAndHashCode(callSuper = true)
@Data
public class OrderInfo extends PageReqVO implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId( type = IdType.ASSIGN_UUID)
    private String oId;  // 订单ID

    private String oP;  // 评语

    private String uId;  // 操作人ID

    private String oTel; // 相关联系电话

    private String oNo;  // 订单编号

    private String oHp;  // 酒店追评

    private String name;  // 名

    private String email;  // 邮箱

    private Date createTime;  // 订单创建时间

    private Date updateTime;  // 订单更新时间

    private Date endTime;  // 订单结束时间

    private Integer status;  // 订单状态

    private Integer userSta;  // 用户入住状态

    private Integer oRnum;  // 房间数量

    private Integer oPl;  // 对酒店评级

    private Integer oPrice;  // 订单支付金额

    private Date oUin;  // 入住时间

    private Date oUout;  // 退房时间

    private Integer oPw;  // 酒店卫生评级

    private Integer oPf;  // 酒店服务评级

    private String rtId;  // 房型ID

    @TableField(exist = false)
    private String rtName;  // 房型ID

    @TableField(exist = false)
    private Hotelinfo hoId; // 酒店ID

}
