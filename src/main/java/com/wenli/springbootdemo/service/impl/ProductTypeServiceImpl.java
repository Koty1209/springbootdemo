package com.wenli.springbootdemo.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wenli.springbootdemo.common.HttpCode;
import com.wenli.springbootdemo.common.MyException;
import com.wenli.springbootdemo.common.PageParam;
import com.wenli.springbootdemo.dao.ProductTypeDao;
import com.wenli.springbootdemo.model.ProductType;
import com.wenli.springbootdemo.service.ProductTypeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * @program: springbootdemo
 * @description:
 * @author: Koty
 * @create: 2019-07-11 19:19
 **/
@Slf4j
@Service
public class ProductTypeServiceImpl implements ProductTypeService {

    @Autowired
    ProductTypeDao productTypeDao;

    @Cacheable(key = "#p0", value = "productTypes")
    @Override
    public Object getAllProductType(PageParam<ProductType> pageParam) {

        log.info("走的是数据库");

        /**
         * 1.设置分页的页面属性
         * pagehelper插件中的开始页设置，这里传了一个（第几页）pageNum，和一个（页尺寸）pageSize
         */
        PageHelper.startPage(pageParam.getPageNum(),pageParam.getPageSize());

        /**
         * 2.实现（多）排序
         * 根据排序参数实现排序功能，用遍历实现多条件排序（需要排序的参数可能不止一个：id、username...）
         * 按照排序参数数组中的索引顺序依次实现排序需求
         */
        for (int i = 0; i < pageParam.getOrderParams().length; i++) {
            // 调用pagehelper的排序方法对排序参数数组依次排序
            PageHelper.orderBy(pageParam.getOrderParams()[i]);
        }

        List<ProductType> productTypeList = productTypeDao.getAllProductType(pageParam.getModel());
        // 将userdao中查出来的userlist赋给userpageinfo并返回
        PageInfo<ProductType> productTypePageInfo = new PageInfo<>(productTypeList);

        return productTypePageInfo;
    }



    /**
     * @Cacheable(key = "#p0", value = "productTypes")
     * 当缓存中没有想要的值（key）时，才调用下面的方法,
     * #p（取参数）0（下面方法中的第1个）及id  赋值给key
     * value 的值为下面方法返回值   todo(是不是对的)
     */
    @Cacheable(key = "#p0", value = "productTypes")
    @Override
    public ProductType getProductTypeById(int id) {
        log.info("走的是数据库");
        return productTypeDao.getProductTypeById(id);
    }


    @CachePut(key = "#p0.id", value = "productTypes")
    @Override
    public Object addProductType(ProductType productType) {

        log.info("走的是数据库");
        productTypeDao.addProductType(productType); // 添加到数据库后会返回一个最新的id
        return productTypeDao.getProductTypeById(productType.getId()); // 根据id的值得到整个对象并返回
    }

    @CacheEvict(key = "#p0", value = "productTypes")
    @Override
    public boolean deleteProductTypeById(int id) {

        log.info("走的是数据库");

        return productTypeDao.deleteProductTypeById(id) == 1;
    }

    @Override
    public boolean updateProductType(ProductType productType) {

        if(StringUtils.isEmpty(productType.getId())){
            throw new MyException(HttpCode.ERROR).msg("通过id修改商品类别时，id不能为空");
        }

        return productTypeDao.updateProductType(productType) == 1;
    }

    @Override
    public ProductType getProductTypeByName(String productTypename) {
        return productTypeDao.getProductTypeByName(productTypename);
    }


    /**
     * static
     */

    @Override
    public boolean addProductTypeViewNum(int typeId) {

        int oldViewNum = productTypeDao.getProductTypeById(typeId).getViewNum(); // 获取原点击量

        ProductType productType = new ProductType();
        productType.setViewNum(++oldViewNum); // 点击量加一
        productType.setId(typeId);

        return productTypeDao.updateProductType(productType) == 1;
    }

}
