package com.wenli.springbootdemo.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * @program: springbootdemo
 * @description:
 * @author: Koty
 * @create: 2019-07-11 11:00
 **/
@ApiModel(value = "user", description = "用户实体类")
@Data
public class User implements Serializable {// todo 调用getUserById 传id不继承Serializable报错：500，为什么继承Serializable

    @ApiModelProperty(value = "用户id", name = "id")
    private int id;

    @Size(max=12,min=6,message = "用户名长度必须是6-12位")
    @NotEmpty(message = "用户名不能为空")
    @ApiModelProperty(value = "用户名", name = "username")
    private String username;

    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-zA-Z])(.{8,16})$",message = "密码必须是包含数字和字母的8到16位")
    @ApiModelProperty(value = "用户密码", name = "password")
    private String password;

    @ApiModelProperty(value = "用户身份id", name = "roleId")
    @Null(message = "不允许直接修改用户身份")
    private String roleId;

    @ApiModelProperty(value = "邮箱", name = "email")
    @Email(message = "邮箱格式不正确")
    private String email;

    @ApiModelProperty(value = "是否被激活", name = "isActive")
    private int isActive;

    @ApiModelProperty(value = "收获地址", name = "deliveryAddress")
    private String deliveryAddress;

    @ApiModelProperty(value = "用户头像", name = "headImg")
    private String headImg;

}
