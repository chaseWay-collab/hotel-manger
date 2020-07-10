package com.example.entity.system;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.example.vo.request.PageReqVO;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @ClassName: SysUser
 * @Description: SysUser
 * @Author: [咖啡]
 * @Version: 1.0
 **/
@Data
public class SysUser extends PageReqVO implements Serializable {

    @TableId(type = IdType.ASSIGN_UUID)
    private String id;

    private String username;

    private String salt;

    private String password;

    private String phone;

    private String deptId;

    @TableField(exist = false)
    private String deptName;

    private String realName;

    private String nickName;

    private String email;

    private Integer status;

    private Integer sex;

    @TableLogic  // 逻辑删除
    private Integer deleted;

    private String createId;

    private String updateId;

    private Integer createWhere;

    private Date createTime;

    private Date updateTime;

    @TableField(exist = false)
    private String startTime;

    @TableField(exist = false)
    private String endTime;
}