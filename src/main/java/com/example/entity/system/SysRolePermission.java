package com.example.entity.system;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @ClassName: SysRolePermission
 * @Description: SysRolePermission
 * @Author: [咖啡]
 * @Version: 1.0
 **/
@Data
public class SysRolePermission implements Serializable {
    private static final long serialVersionUID = 767845609990865111L;
    @TableId(type = IdType.ASSIGN_UUID)
    private String id;

    private String roleId;

    private String permissionId;

    private Date createTime;

}