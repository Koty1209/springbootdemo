package com.wenli.springbootdemo.controller;

import com.wenli.springbootdemo.common.MyResponse;
import com.wenli.springbootdemo.common.PageParam;
import com.wenli.springbootdemo.model.Logistics;
import com.wenli.springbootdemo.model.OrderDetail;
import com.wenli.springbootdemo.model.Product;
import com.wenli.springbootdemo.service.LogisticsService;
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
@Api(value = "logistics模块接口", description = "这是一个物流模块的接口")
@RestController
@RequestMapping("logistics")
@CrossOrigin
public class LogisticsController {

    @Autowired
    private LogisticsService logisticsService;

    @ApiOperation("查询所有物流")
    @PutMapping(value = "/getAllLogistics") // 这个查询需要带参数，GET请求无法实现（没有请求头）
    public Object getAllLogistics(@RequestBody PageParam<Logistics> pageParam){
        return MyResponse.success(logisticsService.getAllLogistics(pageParam));
    }

    @GetMapping(value = "/getLogisticsById/{id}")
    public Object getLogisticsById(@PathVariable("id") int id){

        if (logisticsService.getLogisticsById(id) != null) {
            return MyResponse.success(logisticsService.getLogisticsById(id)); // 成功，将查到的Logistics放到响应内容(content)中去
        }

        return MyResponse.error();
    }

    @PostMapping("/addLogistics")
    public Object addLogistics(@RequestBody @Valid Logistics logistics){

        Object object = logisticsService.addLogistics(logistics);

        return MyResponse.success(object);
    }

    @PostMapping("/createLogistics")
    public Object createLogistics(@RequestBody List<OrderDetail> orderDetails){
        List<Logistics> logisticsList = logisticsService.createLogistics(orderDetails);

        return MyResponse.success(logisticsList);
    }

    /**
     * @PutMapping 中value的{}里带上参数名，通过@PathVariable来接收该参数值并取名给后面的形参
     */
    @PutMapping("/deleteLogisticsById/{id}")
    public Object deleteLogisticsById(@PathVariable("id") int id){
        return MyResponse.success(logisticsService.deleteLogisticsById(id));
    }

    @PutMapping("/updateLogistics")
    public Object updateLogistics(@RequestBody  Logistics logistics){

        return MyResponse.success(logisticsService.updateLogistics(logistics));
    }

    // get Logistics by receiverId
    @GetMapping("/getLogisticsByReceiverId/{receiverId}")
    public Object getLogisticsByReceiverId(@PathVariable("receiverId") int receiverId){
        if (logisticsService.getLogisticsByReceiverId(receiverId) != null) {
            return MyResponse.success(logisticsService.getLogisticsByReceiverId(receiverId)); // 成功，将查到的Logistics放到响应内容(content)中去
        }

        return MyResponse.error();
    }

}
