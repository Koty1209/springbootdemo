package com.wenli.springbootdemo.controller;

import com.wenli.springbootdemo.common.MyResponse;
import com.wenli.springbootdemo.common.PageParam;
import com.wenli.springbootdemo.model.ShoppingCar;
import com.wenli.springbootdemo.service.ShoppingCarService;
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
@Api(value = "shoppingCar模块接口", description = "这是一个商品模块的接口")
@RestController
@RequestMapping("shoppingCar")
@CrossOrigin
public class ShoppingCarController {

    @Autowired
    private ShoppingCarService ShoppingCarService;

    @ApiOperation("查询所有购物车商品")
    @PutMapping(value = "/getAllShoppingCar") // 这个查询需要带参数，GET请求无法实现（没有请求头）
    public Object getAllShoppingCar(@RequestBody PageParam<ShoppingCar> pageParam){
        return MyResponse.success(ShoppingCarService.getAllShoppingCar(pageParam));
    }

    @GetMapping(value = "/getShoppingCarById/{id}")
    public Object getShoppingCarById(@PathVariable("id") int id){

        if (ShoppingCarService.getShoppingCarById(id) != null) {
            return MyResponse.success(ShoppingCarService.getShoppingCarById(id)); // 成功，将查到的shoppingCar放到响应内容(content)中去
        }

        return MyResponse.error();
    }

    @PostMapping("/addShoppingCar")
    public Object addShoppingCar(@RequestBody @Valid ShoppingCar ShoppingCar){

        Object object = ShoppingCarService.addShoppingCar(ShoppingCar);

        return MyResponse.success(object);
    }

    /**
     * @PutMapping 中value的{}里带上参数名，通过@PathVariable来接收该参数值并取名给后面的形参
     */
    @PutMapping("/deleteShoppingCarById/{id}")
    public Object deleteShoppingCarById(@PathVariable("id") int id){
        return MyResponse.success(ShoppingCarService.deleteShoppingCarById(id));
    }

    @PutMapping("/updateShoppingCar")
    public Object updateShoppingCar(@RequestBody  ShoppingCar ShoppingCar){

        ShoppingCar.setProductName(null); // 商品名无法修改

        return MyResponse.success(ShoppingCarService.updateShoppingCar(ShoppingCar));
    }


    /**
     * static
     */

    @GetMapping("/addShoppingCarViewNum/{id}")
    public Object addShoppingCarViewNum(@PathVariable int id){
        return ShoppingCarService.addShoppingCarViewNum(id)
                ?MyResponse.success(null):MyResponse.error().msg("添加购物车商品点击量失败");
    }

}
