package com.geekaca.mall.controller.admin;

import com.geekaca.mall.service.OrderService;
import com.geekaca.mall.utils.PageResult;
import com.geekaca.mall.utils.Result;
import com.geekaca.mall.utils.ResultGenerator;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/manage-api/v1")
public class AdminOrderController {
    @Autowired
    private OrderService orderService;

    @RequestMapping("/orders")
    public Result list(@RequestParam(required = false) @ApiParam("页码") Integer pageNumber,
                       @RequestParam(required = false) @ApiParam("每页条数") Integer pageSize,
                       @RequestParam(required = false) @ApiParam("订单号") String orderNo,
                       @RequestParam(required = false) @ApiParam("订单状态") Integer orderStatus){
        PageResult ordersList = orderService.getAdminOrderList(pageNumber, pageSize, orderStatus,orderNo);
        Result result = ResultGenerator.genSuccessResult();
        result.setData(ordersList);
        return result;
    }
}
