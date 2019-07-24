package com.wenli.springbootdemo.dao;

import com.wenli.springbootdemo.model.OrderHead;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface OrderHeadDao {

    /**
     * @param OrderHead 实现多条件查询时需要条件参数，将OrderHead对象作为参数传进去
     */
    List<OrderHead> getAllOrderHead(OrderHead OrderHead);

    OrderHead getOrderHeadById(int id);

    int addOrderHead(OrderHead orderHead);

    int deleteOrderHeadById(int id);

    int updateOrderHead(OrderHead orderHead);

}
