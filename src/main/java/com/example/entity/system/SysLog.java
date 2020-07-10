package com.example.entity.system;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.example.vo.request.PageReqVO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * @ClassName: SysLog
 * @Description: SysLog
 * @Author: [咖啡]
 * @Version: 1.0
 **/
@EqualsAndHashCode(callSuper = true)
@Data
public class SysLog extends PageReqVO implements Serializable {
    private static final long serialVersionUID = 234506718683021993L;
    @TableId(type = IdType.ASSIGN_UUID)
    private String id;

    private String userId;

    private String username;

    private String operation;

    private Integer time;

    private String method;

    private String params;

    private String ip;

    private Date createTime;

    @TableField(exist = false)
    private String startTime;

    @TableField(exist = false)
    private String endTime;

}
