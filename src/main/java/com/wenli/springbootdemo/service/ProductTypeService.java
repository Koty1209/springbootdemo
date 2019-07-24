package com.wenli.springbootdemo.service;

import com.wenli.springbootdemo.common.PageParam;
import com.wenli.springbootdemo.model.ProductType;

public interface ProductTypeService {

    Object getAllProductType(PageParam<ProductType> pageParam);

    ProductType getProductTypeById(int id);

    Object addProductType(ProductType productType);

    boolean deleteProductTypeById(int id);

    boolean updateProductType(ProductType productType);

    ProductType getProductTypeByName(String productTypeName);


    /**
     * static
     */

    boolean addProductTypeViewNum(int typeId);

}
