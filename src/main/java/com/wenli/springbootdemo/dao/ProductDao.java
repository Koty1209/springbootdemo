package com.wenli.springbootdemo.dao;

import com.wenli.springbootdemo.model.Product;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ProductDao {

    /**
     * @param product 实现多条件查询时需要条件参数，将product对象作为参数传进去
     */
    List<Product> getAllProduct(Product product);

    Product getProductById(int id);

    int addProduct(Product product);

    int deleteProductById(int id);

    int updateProduct(Product product);

    @Select("select * from product where productName=#{productName}")
    Product getProductByProductName(String productName);


}
