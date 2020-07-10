package com.example.vo.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * @ClassName: UserRoleOperationReqVO
 * @Description: UserRoleOperationReqVO
 * @Author: [咖啡]
 * @Version: 1.0
 **/
@Data
public class UserRoleOperationReqVO {
    @ApiModelProperty(value = "用户id")
    @NotBlank(message = "用户id不能为空")
    private String userId;
    @ApiModelProperty(value = "角色id集合")
    @NotEmpty(message = "角色id集合不能为空")
    private List<String> roleIds;
}
