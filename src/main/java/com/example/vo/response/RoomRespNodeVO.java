package com.example.vo.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @ClassName: RoomRespNodeVO
 * @Description: RoomRespNodeVO
 * @Author: [咖啡]
 * @Version: 1.0
 **/
@Data
public class RoomRespNodeVO {

    @ApiModelProperty(value = "房间类型ID")
    private String id;

    @ApiModelProperty(value = "标题")
    private String title;

    @ApiModelProperty(value = "房间类型状态(1:入住中;2:维修中;3:空闲)")
    private String roomState;

    @ApiModelProperty(value = "房间类型名称")
    private String roomType;

    @ApiModelProperty(value = "是否展开 默认不展开(false)")
    private boolean spread;

    @ApiModelProperty(value = "子集")
    private List<?> children;

}
