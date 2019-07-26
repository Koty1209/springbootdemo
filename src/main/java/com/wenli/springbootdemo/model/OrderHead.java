package com.wenli.springbootdemo.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @program: springbootdemo
 * @description:
 * @author: Koty
 * @create: 2019-07-11 11:00
 **/
@ApiModel(value = "orderHead", description = "订单表头实体类")
@Data
public class OrderHead implements Serializable { // Serializable序列化，redis以序列化的型式存储数据，实体类需要继承该接口来实现序列化功能

    @ApiModelProperty(value = "订单表头id", name = "id")
    private int id;

    @ApiModelProperty(value = "订单商品总件数", name = "totalProductNum")
    private int totalProductNum;

    @ApiModelProperty(value = "订单第一件商品的名称", name = "firstProductName")
    private String firstProductName;

    @ApiModelProperty(value = "订单第一件商品的图片", name = "firstProductImg")
    private String firstProductImg;

    @ApiModelProperty(value = "订单商品总价（含运费）", name = "totalPrice")
    private double totalPrice;

    @ApiModelProperty(value = "用户id", name = "userId")
    private int userId;

    @ApiModelProperty(value = "订单创建时间", name = "createTime")
    private Date createTime;

    @ApiModelProperty(value = "订单状态", name = "status")
    private String status;
    @ApiModelProperty(value = "订单总折扣", name = "discount")
    private double discount;

    @ApiModelProperty(value = "订单总秒杀折扣", name = "killDiscount")
    private double killDiscount;

    @ApiModelProperty(value = "订单第一件商品的发货地址", name = "firstDeliveryAddress")
    private String firstDeliveryAddress;


}
