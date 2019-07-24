package com.wenli.springbootdemo.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

/**
 * @program: springbootdemo
 * @description:
 * @author: Koty
 * @create: 2019-07-22 10:04
 **/
@ApiModel(value = "productType", description = "商品类别实体类")
@Data
public class ProductType implements Serializable {

    @ApiModelProperty(value = "商品分类id", name = "id")
    private int id;

    @NotEmpty(message = "商品类别名不能为空")
    @ApiModelProperty(value = "商品类别名", name = "productTypeName")
    private String productTypeName;

    @ApiModelProperty(value = "点击量", name = "viewNum")
    private int viewNum;

    @ApiModelProperty(value = "商品分类图片", name = "typeImg")
    private String typeImg;

}