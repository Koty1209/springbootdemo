package com.wenli.springbootdemo.service;

import com.wenli.springbootdemo.common.PageParam;
import com.wenli.springbootdemo.model.Product;

public interface ProductService {

    Object getAllProduct(PageParam<Product> pageParam);

    Product getProductById(int id);

    Object addProduct(Product product);

    boolean deleteProductById(int id);

    boolean updateProduct(Product product);

    Product getProductByProductName(String productName);


    /**
     * static
     */

    boolean addProductViewNum(int id);

}
