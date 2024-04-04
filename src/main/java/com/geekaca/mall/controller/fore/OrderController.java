package com.geekaca.mall.controller.fore;

import com.geekaca.mall.domain.Order;
import com.geekaca.mall.service.OrderService;
import com.geekaca.mall.utils.PageResult;
import com.geekaca.mall.utils.Result;
import com.geekaca.mall.utils.ResultGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class OrderController {
    @Autowired
    OrderService orderService;

    @GetMapping("/order")
    public Result getOrder(@RequestParam(required = false) Integer pageNumber,
                           @RequestParam(required = false) Integer pageSize,
                           @RequestParam(required = false) Integer status) {
        if (pageNumber == null) {
            pageNumber = 1;
        }
        if (pageSize == null) {
            pageSize = 5;
        }
        PageResult pageResult = orderService.getOrders(pageNumber, pageSize, status);
        return ResultGenerator.genSuccessResult(pageResult);
    }


    @GetMapping("/order/{orderNo}")
    public Result orderDtail(@PathVariable("orderNo") String  orderNo){
        Order order = orderService.getOrderDetail(orderNo);
        if(order!=null){
            return ResultGenerator.genSuccessResult(order);
        }
        return ResultGenerator.genFailResult("订单不存在");
    }
}
