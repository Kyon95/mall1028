package com.geekaca.mall.controller.admin;

import com.geekaca.mall.controller.admin.param.BatchIdParam;
import com.geekaca.mall.controller.vo.OrderDetailVO;
import com.geekaca.mall.service.OrderService;
import com.geekaca.mall.utils.PageResult;
import com.geekaca.mall.utils.Result;
import com.geekaca.mall.utils.ResultGenerator;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/orders/{orderId}")
    public Result orderDetailPage (@ApiParam("订单号") @PathVariable("orderId") Long orderId){
        OrderDetailVO orderDetailByOrderId = orderService.getOrderDetailByOrderId(orderId);
        Result result = ResultGenerator.genSuccessResult();
        result.setData(orderDetailByOrderId);
        return result;
    }

    @RequestMapping("/orders/checkDone")
    public Result checkDone(@RequestBody BatchIdParam batchIdParam){
        Long[] ids = batchIdParam.getIds();
        Boolean isCheckDone = orderService.checkDone(ids);
        if (isCheckDone == true) {
            return  ResultGenerator.genSuccessResult("成功");
        }else {
            return ResultGenerator.genFailResult("失败");
        }
    }

    @RequestMapping("/orders/checkOut")
    public Result checkOut(@RequestBody BatchIdParam batchIdParam){
        Long[] ids = batchIdParam.getIds();
        Boolean isCheckOut = orderService.checkOut(ids);
        if (isCheckOut == true){
            return  ResultGenerator.genSuccessResult("成功");
        }else {
            return ResultGenerator.genFailResult("失败");
        }
    }

    @RequestMapping("/orders/close")
    public Result closeOrder(@RequestBody BatchIdParam batchIdParam){
        Long[] ids = batchIdParam.getIds();
        Boolean isCloseOrder = orderService.closeOrder(ids);
        if (isCloseOrder == true){
            return  ResultGenerator.genSuccessResult("成功");
        }else {
            return ResultGenerator.genFailResult("失败");
        }
    }


}
