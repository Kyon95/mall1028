package com.geekaca.mall.controller.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class OrderDetailForeVO {
    @ApiModelProperty("创建时间")
    @JsonFormat(
            pattern = "yyyy-MM-dd HH:mm:ss",
            timezone = "GMT+8"
    )
    private Date createTime;
    @ApiModelProperty("订单ID")
    private Long orderId;
    @ApiModelProperty("订单号")
    private String orderNo;
    @ApiModelProperty("订单状态")
    private Byte orderStatus;
    @ApiModelProperty("订单支付方式")
    private Byte payType;
    @ApiModelProperty("订单价格")
    private Integer totalPrice;
    @ApiModelProperty("订单支付状态码")
    private Byte payStatus;
    @ApiModelProperty("订单项列表")
    private List<OrderItemVO> NewBeeMallOrderItemVOS;

}
