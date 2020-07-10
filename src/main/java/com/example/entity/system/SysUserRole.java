package com.example.entity.system;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @ClassName: SysUserRole
 * @Description: SysUserRole
 * @Author: [咖啡]
 * @Version: 1.0
 **/
@Data
public class SysUserRole implements Serializable {
    @TableId(type = IdType.ASSIGN_UUID)
    private String id;

    private String userId;

    private String roleId;

    private Date createTime;


}