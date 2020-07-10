package com.example.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.assertj.core.util.Lists;

import java.util.List;

/**
 * @ClassName: TreeMenu
 * @Description: TreeMenu
 * @Author: [咖啡]
 * @Version: 1.0
 **/
@Data
public class TreeMenu {

    private Long id;
    @ApiModelProperty(value = "用户标识")
    private Long pid;
    @ApiModelProperty(value = "标题")
    private String title;

    private String icon;
    @ApiModelProperty(value = "链接地址")
    private String href;
    @ApiModelProperty(value = "菜单是否展开")
    private Boolean spread;
    @ApiModelProperty(value = "子节点列表")
    private List<ShowMenu> children = Lists.newArrayList();

    public TreeMenu(Boolean spread) {
        this.spread = spread;
    }

    public TreeMenu(Long id, Long pid, String title, String icon, String href) {
        this.id = id;
        this.pid = pid;
        this.title = title;
        this.icon = icon;
        this.href = href;
    }
}
