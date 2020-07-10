package com.example.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @ClassName: ZTreeVO
 * @Description: ZTreeVO
 * @Author: [咖啡]
 * @Version: 1.0
 **/
@Data
public class ZTreeVO implements Serializable {

    private static final long serialVersionUID = -4585356697322586622L;

    private Long id;
    @ApiModelProperty(value = "用户标识")
    private Long pid;

    private String name;

    private String url;

    private Boolean open = true;

    private  Boolean isParent;

    private String icon;

    private List<ZTreeVO> children;

    public ZTreeVO() {
    }
}
