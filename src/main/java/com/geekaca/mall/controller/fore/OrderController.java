package com.geekaca.mall.controller.fore;

import com.geekaca.mall.service.OrderService;
import com.geekaca.mall.utils.PageResult;
import com.geekaca.mall.utils.Result;
import com.geekaca.mall.utils.ResultGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
}
