package com.wenli.springbootdemo.controller;

import com.wenli.springbootdemo.common.MyResponse;
import com.wenli.springbootdemo.common.PageParam;
import com.wenli.springbootdemo.model.OrderHead;
import com.wenli.springbootdemo.model.Product;
import com.wenli.springbootdemo.model.ShoppingCar;
import com.wenli.springbootdemo.service.OrderHeadService;
import com.wenli.springbootdemo.service.ProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @program: springbootdemo
 * @description:
 * @author: Koty
 * @create: 2019-07-11 16:55
 **/
@Api(value = "OrderHead模块接口", description = "这是一个订单表头模块的接口")
@RestController
@RequestMapping("orderHead")
@CrossOrigin
public class OrderHeadController {

    @Autowired
    private OrderHeadService orderHeadService;

    @ApiOperation("查询所有订单表头")
    @PutMapping(value = "/getAllOrderHead") // 这个查询需要带参数，GET请求无法实现（没有请求头）
    public Object getAllOrderHead(@RequestBody PageParam<OrderHead> pageParam){
        return MyResponse.success(orderHeadService.getAllOrderHead(pageParam));
    }

    @GetMapping(value = "/getOrderHeadById/{id}")
    public Object getOrderHeadById(@PathVariable("id") int id){

        if (orderHeadService.getOrderHeadById(id) != null) {
            return MyResponse.success(orderHeadService.getOrderHeadById(id)); // 成功，将查到的内容放到响应内容(content)中去
        }

        return MyResponse.error();
    }

    @PostMapping("/addOrderHead")
    public Object addOrderHead(@RequestBody @Valid OrderHead orderHead){

        Object object = orderHeadService.addOrderHead(orderHead);

        return MyResponse.success(object);
    }

    /**
     * @PutMapping 中value的{}里带上参数名，通过@PathVariable来接收该参数值并取名给后面的形参
     */
    @PutMapping("/deleteOrderHeadById/{id}")
    public Object deleteOrderHeadById(@PathVariable("id") int id){
        return MyResponse.success(orderHeadService.deleteOrderHeadById(id));
    }

    @PutMapping("/updateOrderHead")
    public Object updateOrderHead(@RequestBody  OrderHead orderHead){
        return MyResponse.success(orderHeadService.updateOrderHead(orderHead));
    }

    /**
     * 订单支付方法
     * @param shoppingCarList 为要执行支付操作的购物车（商品）集合
     * @return
     */
    @PostMapping("/pay")
    public Object pay(@RequestBody List<ShoppingCar> shoppingCarList){

        OrderHead orderHead = orderHeadService.createOrderHead(shoppingCarList);

        return MyResponse.success(orderHead);
    }

}
