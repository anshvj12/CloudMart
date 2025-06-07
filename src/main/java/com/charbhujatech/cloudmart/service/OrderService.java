package com.charbhujatech.cloudmart.service;

import com.charbhujatech.cloudmart.Model.Order;
import com.charbhujatech.cloudmart.dto.OrderResponseDTO;
import com.charbhujatech.cloudmart.dto.OrdersResponseDTO;
import com.charbhujatech.cloudmart.dto.ResponseDTO;
import com.charbhujatech.cloudmart.enums.OrderStatus;
import org.springframework.stereotype.Service;

@Service
public interface OrderService {

    ResponseDTO createOrder(Long userId);

    Boolean deleteOrder(Long userId, Long orderId);

    OrderResponseDTO getOrder(Long orderId);

    OrdersResponseDTO getAllOrder(Long userId, OrderStatus orderStatus, int page, String sortBy, Boolean ascending);

    ResponseDTO confirmedOrder(Long userId, Long orderId);

    ResponseDTO processingOrder(Long userId, Long orderId);

    ResponseDTO shippingOrder(Long userId, Long orderId);

    ResponseDTO outForDeliveryOrder(Long userId, Long orderId);

    ResponseDTO deliverredOrder(Long userId, Long orderId);

    ResponseDTO returnRequestOrder(Long userId, Long orderId);

    ResponseDTO returnedOrder(Long userId, Long orderId);

    ResponseDTO rejectedOrder(Long userId, Long orderId);

    ResponseDTO refundOrder(Long userId, Long orderId);

    void paymentOrder(Order order, Long userId, Long orderId);
}
