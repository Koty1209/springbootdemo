package com.wenli.springbootdemo.dao;

import com.wenli.springbootdemo.model.ProductType;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ProductTypeDao {

    /**
     * @param productType 实现多条件查询时需要条件参数，将user对象作为参数传进去
     */
    List<ProductType> getAllProductType(ProductType productType);

    ProductType getProductTypeById(int id);

    int addProductType(ProductType productType);

    int deleteProductTypeById(int id);

    int updateProductType(ProductType productType);

    @Select("select * from productType where productTypeName=#{productTypeName}")
    ProductType getProductTypeByName(String productTypeName);


}
