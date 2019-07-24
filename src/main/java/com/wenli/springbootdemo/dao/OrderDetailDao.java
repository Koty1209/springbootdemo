package com.wenli.springbootdemo.dao;

import com.wenli.springbootdemo.model.OrderDetail;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface OrderDetailDao {

    /**
     * @param orderDetail 实现多条件查询时需要条件参数，将orderDetail对象作为参数传进去
     */
    List<OrderDetail> getAllOrderDetail(OrderDetail orderDetail);

    OrderDetail getOrderDetailById(int id);

    int addOrderDetail(OrderDetail orderDetail);

    int deleteOrderDetailById(int id);

    int updateOrderDetail(OrderDetail orderDetail);


}
