package com.example.vo.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @ClassName: RoomTypeRespNode
 * @Description:
 * @Author: [咖啡]
 * @Version: 1.0
 **/
@Data
public class RoomTypeRespNode {

    @ApiModelProperty(value = "id")
    private String id;
    @ApiModelProperty(value = "房间类型名称")
    private String rtName;

    @ApiModelProperty(value = "房型价格")
    private String rtPrice;

    @ApiModelProperty(value = "房型面积")
    private String rtSq;

    @ApiModelProperty(value = "设施服务简介")
    private String rtMsg;

    @ApiModelProperty(value = "该房型状态")
    private Integer rtState;

    @ApiModelProperty(value = "房型数量")
    private Integer rtNum;

}
