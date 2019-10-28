package com.wenli.springbootdemo.service;

import com.wenli.springbootdemo.common.PageParam;
import com.wenli.springbootdemo.model.Logistics;
import com.wenli.springbootdemo.model.OrderDetail;
import com.wenli.springbootdemo.model.Product;

import java.util.List;

public interface LogisticsService {

    Object getAllLogistics(PageParam<Logistics> pageParam);

    Logistics getLogisticsById(int id);

    Object addLogistics(Logistics logistics);

    boolean deleteLogisticsById(int id);

    boolean updateLogistics(Logistics logistics);

    List<Logistics> getLogisticsByReceiverId(int receiverId);

    List<Logistics> createLogistics(List<OrderDetail> orderDetails);

}
