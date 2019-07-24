package com.wenli.springbootdemo.service;

import com.wenli.springbootdemo.common.PageParam;
import com.wenli.springbootdemo.model.OrderDetail;

public interface OrderDetailService {

    Object getAllOrderDetail(PageParam<OrderDetail> pageParam);

    OrderDetail getOrderDetailById(int id);

    Object addOrderDetail(OrderDetail orderDetail);

    boolean deleteOrderDetailById(int id);

    boolean updateOrderDetail(OrderDetail orderDetail);


}
