package com.wenli.springbootdemo.controller;

import com.wenli.springbootdemo.common.MyResponse;
import com.wenli.springbootdemo.common.PageParam;
import com.wenli.springbootdemo.model.OrderDetail;
import com.wenli.springbootdemo.model.Product;
import com.wenli.springbootdemo.service.OrderDetailService;
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
@Api(value = "OrderDetail模块接口", description = "这是一个订单表体模块的接口")
@RestController
@RequestMapping("orderDetail")
@CrossOrigin
public class OrderDetailController {

    @Autowired
    private OrderDetailService orderDetailService;

    @ApiOperation("查询所有订单表体")
    @PutMapping(value = "/getAllOrderDetail") // 这个查询需要带参数，GET请求无法实现（没有请求头）
    public Object getAllOrderDetail(@RequestBody PageParam<OrderDetail> pageParam){
        return MyResponse.success(orderDetailService.getAllOrderDetail(pageParam));
    }

    @GetMapping(value = "/getOrderDetailById/{id}")
    public Object getOrderDetailById(@PathVariable("id") int id){

        if (orderDetailService.getOrderDetailById(id) != null) {
            return MyResponse.success(orderDetailService.getOrderDetailById(id)); // 成功，将查到的user放到响应内容(content)中去
        }

        return MyResponse.error();
    }

    @PostMapping("/addOrderDetail")
    public Object addOrderDetail(@RequestBody @Valid OrderDetail orderDetail){

        Object object = orderDetailService.addOrderDetail(orderDetail);

        return MyResponse.success(object);
    }

    /**
     * @PutMapping 中value的{}里带上参数名，通过@PathVariable来接收该参数值并取名给后面的形参
     */
    @PutMapping("/deleteOrderDetailById/{id}")
    public Object deleteOrderDetailById(@PathVariable("id") int id){
        return MyResponse.success(orderDetailService.deleteOrderDetailById(id));
    }

    @PutMapping("/updateOrderDetail")
    public Object updateOrderDetail(@RequestBody  OrderDetail orderDetail){

        orderDetail.setProductName(null); // 商品名无法修改

        return MyResponse.success(orderDetailService.updateOrderDetail(orderDetail));
    }


}
