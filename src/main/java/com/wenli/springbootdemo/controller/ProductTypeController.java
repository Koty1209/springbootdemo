package com.wenli.springbootdemo.controller;

import com.wenli.springbootdemo.common.MyResponse;
import com.wenli.springbootdemo.common.PageParam;
import com.wenli.springbootdemo.model.ProductType;
import com.wenli.springbootdemo.service.ProductTypeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @program: springbootdemo
 * @description:
 * @author: Koty
 * @create: 2019-07-11 16:55
 **/
@Api(value = "productType模块接口", description = "这是一个商品类别模块的接口")
@Slf4j
@RestController
@RequestMapping("productType")
@CrossOrigin
public class ProductTypeController {

    @Autowired
    private ProductTypeService productTypeService;

    @ApiOperation("查询所有商品类别")
    @PutMapping(value = "/getAllProductType") // 这个查询需要带参数，GET请求无法实现（没有请求头）
    public Object getAllProductType(@RequestBody PageParam<ProductType> pageParam){
        return MyResponse.success(productTypeService.getAllProductType(pageParam));
    }

    @GetMapping(value = "/getProductTypeById/{id}")
    public Object getProductTypeById(@PathVariable("id") int id){
        if (productTypeService.getProductTypeById(id) != null) {
            return MyResponse.success(productTypeService.getProductTypeById(id)); // 成功，将查到的productType放到响应内容(content)中去
        }

        return MyResponse.error().msg("未查询到数据");
    }

    @PostMapping("/addProductType")
    public Object addProductType(@RequestBody @Valid ProductType productType){

        Object object = productTypeService.addProductType(productType);

        return MyResponse.success(object);
    }

    /**
     * @PutMapping 中value的{}里带上参数名，通过@PathVariable来接收该参数值并取名给后面的形参
     */
    @PutMapping("/deleteProductTypeById/{id}")
    public Object deleteProductTypeById(@PathVariable("id") int id){
        return MyResponse.success(productTypeService.deleteProductTypeById(id));
    }

    @PutMapping("/updateProductType")
    public Object updateProductType(@RequestBody  ProductType productType){

        productType.setProductTypeName(null); // 商品类别名无法修改
        log.info(productType.getProductTypeName());

        return MyResponse.success(productTypeService.updateProductType(productType));
    }


    /**
     * static
     */

    @GetMapping("/addProductTypeViewNum/{typeId}")
    public Object addProductTypeViewNum(@PathVariable int typeId){
        return productTypeService.addProductTypeViewNum(typeId)
                ?MyResponse.success(null):MyResponse.error().msg("添加商品分类点击量失败");
    }

}
