package com.charbhujatech.cloudmart.dto;

import com.charbhujatech.cloudmart.Model.User;
import com.charbhujatech.cloudmart.enums.OrderStatus;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
public class OrderResponseDTO {

    private Long orderId;

    protected double totalPrice;

    protected Date deliveryDate;

    protected List<CardProductResponse> orderProducts;

    private OrderStatus orderStatus;

    private Date orderDate;

}
