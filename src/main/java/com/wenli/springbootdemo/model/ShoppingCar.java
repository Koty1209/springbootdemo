package com.wenli.springbootdemo.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.Date;

/**
 * @program: springbootdemo
 * @description:
 * @author: Koty
 * @create: 2019-07-11 11:00
 **/
@ApiModel(value = "shoppingCar", description = "购物车实体类")
@Data
public class ShoppingCar implements Serializable { // Serializable序列化，redis以序列化的型式存储数据，实体类需要继承该接口来实现序列化功能

    @ApiModelProperty(value = "购物车商品id", name = "id")
    private int id;

    @ApiModelProperty(value = "商品id", name = "productId")
    private int productId;

    @ApiModelProperty(value = "用户id", name = "userId")
    private int userId;

    @NotEmpty(message = "商品名不能为空")
    @ApiModelProperty(value = "商品名称", name = "productName")
    private String productName;

    @ApiModelProperty(value = "正常情况时的价格", name = "normalPrice")
    private double normalPrice;

    @ApiModelProperty(value = "折扣价格", name = "discount")
    private double discount;

    @ApiModelProperty(value = "是否参与折扣活动 2参加 1不参加", name = "isInDiscount")
    private int isInDiscount;

    @ApiModelProperty(value = "商品类别id", name = "typeId")
    private int typeId;

    @ApiModelProperty(value = "上架时间", name = "createTime")
    private Date createTime;

    @ApiModelProperty(value = "是否参与秒杀活动 2参加 1不参加", name = "isInKill")
    private int isInKill;

    @ApiModelProperty(value = "秒杀的折扣", name = "killDiscount")
    private double killDiscount;

    @ApiModelProperty(value = "商品图片", name = "productImg")
    private String productImg;

    @ApiModelProperty(value = "浏览量", name = "viewNum")
    private int viewNum;

    @ApiModelProperty(value = "库存量", name = "deserveNum")
    private int deserveNum;

    @ApiModelProperty(value = "商品描述", name = "describe")
    private String describe;

    @ApiModelProperty(value = "下单数", name = "orderCount")
    private int orderCount;

    @ApiModelProperty(value = "运费", name = "freight")
    private double freight;

    @ApiModelProperty(value = "发货地址", name = "deliveryAddress")
    private String deliveryAddress;

    @ApiModelProperty(value = "商品购买件数", name = "productNum")
    private int productNum;


}
