package com.wenli.springbootdemo.service;

import com.wenli.springbootdemo.common.PageParam;
import com.wenli.springbootdemo.model.Product;
import com.wenli.springbootdemo.model.ShoppingCar;

public interface ShoppingCarService {

    Object getAllShoppingCar(PageParam<ShoppingCar> pageParam);

    ShoppingCar getShoppingCarById(int id);

    Object addShoppingCar(ShoppingCar shoppingCar);

    boolean deleteShoppingCarById(int id);

    boolean updateShoppingCar(ShoppingCar shoppingCar);

    ShoppingCar getShoppingCarByProductName(String productName);


    /**
     * static
     */

    boolean addShoppingCarViewNum(int id);

}
