package com.wenli.springbootdemo.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @program: springbootdemo
 * @description: 物流信息类
 * @author: Koty
 * @create: 2019-07-11 11:00
 **/
@ApiModel(value = "logistics", description = "物流信息实体类")
@Data
public class Logistics implements Serializable { // Serializable序列化，redis以序列化的型式存储数据，实体类需要继承该接口来实现序列化功能

    @ApiModelProperty(value = "物流id", name = "id")
    private int id;

    @ApiModelProperty(value = "订单号（及订单详情号orderDetailId）", name = "orderId")
    private int orderId;

    @ApiModelProperty(value = "发货时间", name = "deliveryTime")
    private Date deliveryTime;

    @ApiModelProperty(value = "商品id", name = "productId")
    private int productId;

    @ApiModelProperty(value = "商品名称", name = "productName")
    private String productName;

    @ApiModelProperty(value = "商品图片", name = "productImg")
    private String productImg;

    @ApiModelProperty(value = "购买的商品数量", name = "productNum")
    private int productNum;

    @ApiModelProperty(value = "用户支付的价格（商品总价格）", name = "totalPrice")
    private double totalPrice;

    @ApiModelProperty(value = "用户id（收货人id）", name = "receiverId")
    private int receiverId;

    @ApiModelProperty(value = "收货人名称（用户名）", name = "receiverName")
    private String receiverName;

    @ApiModelProperty(value = "收货地址", name = "shippingAddress")
    private String shippingAddress;

    @ApiModelProperty(value = "发货地址", name = "deliveryAddress")
    private String deliveryAddress;

    @ApiModelProperty(value = "收货时间", name = "shippingTime")
    private Date shippingTime;

    @ApiModelProperty(value = "收货人邮件（用户邮件）", name = "receiverEmail")
    @Email(message = "邮箱格式不正确")
    private String receiverEmail;

}
