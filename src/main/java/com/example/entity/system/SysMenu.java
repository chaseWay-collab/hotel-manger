package com.example.entity.system;

import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.TableField;
import com.example.base.TreeEntity;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

/**
 * @ClassName: SysLog
 * @Description: SysLog
 * @Author: [咖啡]
 * @Version: 1.0
 **/
@Data
public class SysMenu extends TreeEntity<SysMenu> {
	private static final long serialVersionUID = 6545851701598356361L;

	private String name;

	@Length(min = 0, max = 1000, message = "icon长度必须介于 1 和 1000 之间")
	private String icon;

	/**
	 * 	链接地址
	 */
	@TableField(insertStrategy = FieldStrategy.IGNORED, updateStrategy = FieldStrategy.IGNORED, whereStrategy = FieldStrategy.IGNORED)
	private String href;

	/**
	 * 	打开方式
	 */
	@TableField(insertStrategy = FieldStrategy.IGNORED, updateStrategy = FieldStrategy.IGNORED, whereStrategy = FieldStrategy.IGNORED)
	private String target;

	/**
	 * 	菜单是否显示
	 */
	@TableField(value = "is_show", insertStrategy = FieldStrategy.IGNORED, updateStrategy = FieldStrategy.IGNORED)
	private String isShow;

	/**
	 * 	类型(0表示菜单, 1表示按钮, -1表示目录)
	 */
	@TableField(insertStrategy = FieldStrategy.IGNORED, updateStrategy = FieldStrategy.IGNORED)
	private String isMenu;

	@TableField("bg_color")
	private String bgColor;

	/**
	 * 	权限标识
	 */
	@TableField(insertStrategy = FieldStrategy.IGNORED, updateStrategy = FieldStrategy.IGNORED)
	private String permission;

}

	
	
	
	
	
	
	