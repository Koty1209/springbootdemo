package com.wenli.springbootdemo.dao;

import com.wenli.springbootdemo.model.ShoppingCar;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ShoppingCarDao {

    /**
     * @param shoppingCar 实现多条件查询时需要条件参数，将shoppingCar对象作为参数传进去
     */
    List<ShoppingCar> getAllShoppingCar(ShoppingCar shoppingCar);

    ShoppingCar getShoppingCarById(int id);

    int addShoppingCar(ShoppingCar shoppingCar);

    int deleteShoppingCarById(int id);

    int updateShoppingCar(ShoppingCar shoppingCar);

    @Select("select * from shoppingcar where productName=#{productName}")
    ShoppingCar getShoppingCarByProductName(String productName);


}
