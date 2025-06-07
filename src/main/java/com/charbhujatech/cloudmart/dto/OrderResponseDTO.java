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

    protected double totalPrice;

    protected Date deliveryDate;

    protected User user;

    protected List<CardProductResponse> orderProducts;

    private OrderStatus orderStatus;

    private Date orderDate;

}
