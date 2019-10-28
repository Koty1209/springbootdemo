package com.wenli.springbootdemo.dao;

import com.wenli.springbootdemo.model.Logistics;
import com.wenli.springbootdemo.model.Product;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface LogisticsDao {

    /**
     * @param logistics 实现多条件查询时需要条件参数，将Logistics对象作为参数传进去
     */
    List<Logistics> getAllLogistics(Logistics logistics);

    Logistics getLogisticsById(int id);

    int addLogistics(Logistics logistics);

    int deleteLogisticsById(int id);

    int updateLogistics(Logistics logistics);

    @Select("select * from logistics where receiverId=#{receiverId}")
    List<Logistics> getLogisticsByUserId(int receiverId);


}
