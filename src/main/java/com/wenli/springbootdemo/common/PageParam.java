package com.wenli.springbootdemo.common;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @program: springbootdemo
 * @description: pagehelper的分页功能，以注解的形式实现分页查询
 * @author: Koty
 * @create: 2019-07-12 11:49
 **/
@ApiModel(value = "PageParam", description = "分页条件参数")
@Data
public class PageParam<Model> {

    @ApiModelProperty(value = "条件查询的对象", name = "model")
    private Model model;

    @ApiModelProperty(value = "排序参数", name = "orderParams")
    private String[] orderParams;

    @ApiModelProperty(value = "页码", name = "pageNum")
    private int pageNum;

    @ApiModelProperty(value = "每页记录条数", name = "pageSize")
    private int pageSize;

}
