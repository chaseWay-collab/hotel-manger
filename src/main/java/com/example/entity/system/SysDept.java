package com.example.entity.system;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Date;

/**
 * @ClassName: SysDept
 * @Description: SysDept
 * @Author: [咖啡]
 * @Version: 1.0
 **/
@Data
public class SysDept implements Serializable {
    @TableId(type = IdType.ASSIGN_UUID)
    private String id;

    private String deptNo;

    @NotBlank(message = "机构名称不能为空")
    private String name;

    @NotBlank(message = "父级不能为空")
    private String pid;

    @TableField(exist = false)
    private String pidName;

    private Integer status;

    private String relationCode;

    private String deptManagerId;

    private String managerName;

    private String phone;

    private Date createTime;

    private Date updateTime;

    @TableLogic
    private Integer deleted;

}