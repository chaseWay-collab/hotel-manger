package com.example.base;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

import java.io.Serializable;
import java.util.Objects;

/**
 * @program: springboot-manger-test
 * @description: 实体父类
 * @author: [咖啡]
 **/
@Data
public abstract class BaseEntity<T extends Model<T>> extends Model<T> {

    private static final long serialVersionUID = -6756065964156207484L;
    /**
     *  实体编号, 唯一
     */
    protected Long id;

/*    public BaseEntity(Long id) {
        this();
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BaseEntity<?> that = (BaseEntity<?>) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }*/
}

