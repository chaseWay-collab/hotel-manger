package com.example.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.entity.system.SysDept;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @ClassName: SysDeptMapper
 * @Description: Mapper
 * @Author: [咖啡]
 * @Version: 1.0
 **/
@Repository
public interface SysDeptMapper extends BaseMapper<SysDept> {


    int updateRelationCode(@Param("oldStr") String oldStr, @Param("newStr") String newStr, @Param("relationCode") String relationCode);

}