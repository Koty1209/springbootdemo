package com.wenli.springbootdemo.controller;

import com.wenli.springbootdemo.common.MyResponse;
import com.wenli.springbootdemo.common.PageParam;
import com.wenli.springbootdemo.model.Product;
import com.wenli.springbootdemo.service.ProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @program: springbootdemo
 * @description:
 * @author: Koty
 * @create: 2019-07-11 16:55
 **/
@Api(value = "product模块接口", description = "这是一个商品模块的接口")
@RestController
@RequestMapping("product")
@CrossOrigin
public class ProductController {

    @Autowired
    private ProductService productService;

    @ApiOperation("查询所有商品")
    @PutMapping(value = "/getAllProduct") // 这个查询需要带参数，GET请求无法实现（没有请求头）
    public Object getAllProduct(@RequestBody PageParam<Product> pageParam){
        return MyResponse.success(productService.getAllProduct(pageParam));
    }

    @GetMapping(value = "/getProductById/{id}")
    public Object getProductById(@PathVariable("id") int id){

        if (productService.getProductById(id) != null) {
            return MyResponse.success(productService.getProductById(id)); // 成功，将查到的user放到响应内容(content)中去
        }

        return MyResponse.error();
    }

    @PostMapping("/addProduct")
    public Object addProduct(@RequestBody @Valid Product product){

        Object object = productService.addProduct(product);

        return MyResponse.success(object);
    }

    /**
     * @PutMapping 中value的{}里带上参数名，通过@PathVariable来接收该参数值并取名给后面的形参
     */
    @PutMapping("/deleteProductById/{id}")
    public Object deleteProductById(@PathVariable("id") int id){
        return MyResponse.success(productService.deleteProductById(id));
    }

    @PutMapping("/updateProduct")
    public Object updateProduct(@RequestBody  Product product){

        product.setProductName(null); // 商品名无法修改

        return MyResponse.success(productService.updateProduct(product));
    }


    /**
     * static
     */

    @GetMapping("/addProductViewNum/{id}")
    public Object addProductViewNum(@PathVariable int id){
        return productService.addProductViewNum(id)
                ?MyResponse.success(null):MyResponse.error().msg("添加商品点击量失败");
    }

}
