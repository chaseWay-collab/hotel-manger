package com.example.vo.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @ClassName: LoginReqVO
 * @Description: LoginReqVO
 * @Author: [咖啡]
 * @Version: 1.0
 **/
@Data
public class LoginReqVO {

    @ApiModelProperty("账号")
    @NotBlank(message = "账号不能为空")
    private String username;

    @ApiModelProperty("用户密码")
    @NotBlank(message = "密码不能为空")
    private String password;
}
