package com.example.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.assertj.core.util.Lists;

import java.io.Serializable;
import java.util.List;

/**
 * @ClassName: ShowMenu
 * @Description: ShowMenu
 * @Author: [咖啡]
 * @Version: 1.0
 **/
@Data
public class ShowMenu implements Serializable {

    private static final long serialVersionUID = -8843598666371157629L;

    private Long id;
    @ApiModelProperty(value = "用户标识")
    private Long pid;
    @ApiModelProperty(value = "标题")
    private String title;

    private String icon;
    @ApiModelProperty(value = "链接地址")
    private String href;
    @ApiModelProperty(value = "菜单是否展开")
    private Boolean spread = false;
    @ApiModelProperty(value = "子节点列表")
    private List<ShowMenu> children = Lists.newArrayList();

}
