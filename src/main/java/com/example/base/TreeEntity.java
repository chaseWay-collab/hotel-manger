package com.example.base;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

import java.util.List;

/**
 * @program: springboot-manger-test
 * @description: 数据实体类
 * @author: [咖啡]
 **/
@Data
public abstract class TreeEntity <T extends Model<T>> extends DataEntity<T> {
    private static final long serialVersionUID = 1313442925549914423L;

    /**
     *  父节点
     */
    @TableField(value = "parent_id")
    protected String parentId;

    /**
     *  节点层次 (第一层, 第二次, 第三层)
     */
    protected Integer level;

    /**
     *  路径
     */
    @TableField(value = "parent_ids")
    protected String parentIds;

    /**
     *  排序
     */
    protected Integer sort;

    @TableField(exist = false)
    protected List<T> children;

    @TableField(exist = false)
    protected T parentTree;

/*    public TreeEntity() {
    super();
    this.sort = 30;
}

    public TreeEntity(Long id){
        super(id);
    }*/
}
