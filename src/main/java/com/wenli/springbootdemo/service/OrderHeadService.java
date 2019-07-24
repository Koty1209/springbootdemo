package com.wenli.springbootdemo.service;

import com.wenli.springbootdemo.common.PageParam;
import com.wenli.springbootdemo.model.OrderHead;
import com.wenli.springbootdemo.model.ShoppingCar;

import java.util.List;

public interface OrderHeadService {

    Object getAllOrderHead(PageParam<OrderHead> orderHead);

    OrderHead getOrderHeadById(int id);

    Object addOrderHead(OrderHead orderHead);

    boolean deleteOrderHeadById(int id);

    boolean updateOrderHead(OrderHead orderHead);

    OrderHead createOrderHead(List<ShoppingCar> shoppingCarList);
}
