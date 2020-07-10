package com.example.base;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @program: springboot-manger-test
 * @description: 数据实体类
 * @author: [咖啡]
 **/
@Data
public abstract class DataEntity<T extends Model<T>> extends BaseEntity<T> {

    private static final long serialVersionUID = 4859840473753948774L;

    /**
     *  创建者
     */
    @TableField(value = "create_by", fill = FieldFill.INSERT)
    protected String createId;

    /**
     *  创建日期
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
    @TableField(value = "create_date", fill = FieldFill.INSERT)
    protected Date createDate;

    /**
     *  创建者
     */
    @TableField(value = "update_by", fill = FieldFill.UPDATE)
    protected String updateId;

    /**
     *  更新日期
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "UTC+8")
    @TableField(value = "update_date", fill = FieldFill.UPDATE)
    protected Date updateDate;

    /**
     *  删除标记(Y:正常, N:删除, A:审核)
     */
    @TableField(value = "del_flag")
    protected Boolean delFlag;

    /**
     *  备注
     */
    @TableField(insertStrategy = FieldStrategy.IGNORED)
    protected String remarks;

/*    public DataEntity() {
        super();
        this.delFlag = false;
    }

    public DataEntity(Long id) {
        super(id);
    }*/
}
