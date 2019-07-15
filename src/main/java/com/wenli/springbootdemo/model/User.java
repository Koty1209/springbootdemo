package com.wenli.springbootdemo.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

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

    @ApiModelProperty(value = "用户名", name = "username")
    private String username;

    @ApiModelProperty(value = "用户密码", name = "password")
    private String password;

    @ApiModelProperty(value = "用户身份id", name = "roleId")
    private String roleId;

}
