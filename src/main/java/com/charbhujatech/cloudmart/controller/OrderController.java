package com.charbhujatech.cloudmart.controller;

import com.charbhujatech.cloudmart.dto.OrderResponseDTO;
import com.charbhujatech.cloudmart.dto.OrdersResponseDTO;
import com.charbhujatech.cloudmart.dto.ResponseDTO;
import com.charbhujatech.cloudmart.service.OrderService;
import com.charbhujatech.cloudmart.util.ConstantsString;
import com.charbhujatech.cloudmart.enums.OrderStatus;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@AllArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping("users/{userId}/orders")
    public ResponseEntity<ResponseDTO> createOrder(@PathVariable Long userId) {
        final ResponseDTO order = orderService.createOrder(userId);
        return new ResponseEntity<>(order, HttpStatus.OK);
    }

    @DeleteMapping("users/{userId}/orders/{orderId}")
    public ResponseEntity<ResponseDTO> deleteOrder(@PathVariable Long userId, @PathVariable Long orderId)
    {
        Boolean deleteStatus = orderService.deleteOrder(userId,orderId);
        return deleteStatus ?
                new ResponseEntity<>(new ResponseDTO(HttpStatus.OK.toString(), ConstantsString.ORDER_DELETED),HttpStatus.OK)
                : new ResponseEntity<>(new ResponseDTO(HttpStatus.INTERNAL_SERVER_ERROR.toString(), ConstantsString.ORDER_DELETED),HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping("users/{userId}/orders/{orderId}")
    public ResponseEntity<OrderResponseDTO> getOrder (@PathVariable Long userId, @PathVariable Long orderId)
    {
        OrderResponseDTO responseDTO = orderService.getOrder(orderId);
        return new ResponseEntity<>(responseDTO,HttpStatus.OK);
    }

    @GetMapping("users/{userId}/orders")
    public ResponseEntity<OrdersResponseDTO> getAllOrder(@PathVariable Long userId,
                                                         @RequestParam(required = false) OrderStatus orderStatus,
                                                         @RequestParam(defaultValue = "0") int page,
                                                         @RequestParam(defaultValue = "orderDate") String sortBy,
                                                         @RequestParam(defaultValue = "false") boolean ascending)
    {
         OrdersResponseDTO ordersResponseDTO = orderService.getAllOrder(userId,orderStatus,page,sortBy,ascending);
         return new ResponseEntity<>(ordersResponseDTO,HttpStatus.OK);
    }

    @PatchMapping("users/{userId}/orders/{orderId}/confirmed")
    public ResponseEntity<ResponseDTO> confirmedOrder(@PathVariable Long userId,
                                                      @PathVariable Long orderId)
    {
        ResponseDTO responseDTO = orderService.confirmedOrder(userId,orderId);
        return new ResponseEntity<>(responseDTO,HttpStatus.OK);
    }

    @PatchMapping("users/{userId}/orders/{orderId}/processing")
    public ResponseEntity<ResponseDTO> processingOrder(@PathVariable Long userId,
                                                      @PathVariable Long orderId)
    {
        ResponseDTO responseDTO = orderService.processingOrder(userId,orderId);
        return new ResponseEntity<>(responseDTO,HttpStatus.OK);
    }

    @PatchMapping("users/{userId}/orders/{orderId}/shipped")
    public ResponseEntity<ResponseDTO> shippingingOrder(@PathVariable Long userId,
                                                       @PathVariable Long orderId)
    {
        ResponseDTO responseDTO = orderService.shippingOrder(userId,orderId);
        return new ResponseEntity<>(responseDTO,HttpStatus.OK);
    }

    @PatchMapping("users/{userId}/orders/{orderId}/outForDelivery")
    public ResponseEntity<ResponseDTO> outForDeliveryOrder(@PathVariable Long userId,
                                                        @PathVariable Long orderId)
    {
        ResponseDTO responseDTO = orderService.outForDeliveryOrder(userId,orderId);
        return new ResponseEntity<>(responseDTO,HttpStatus.OK);
    }

    @PatchMapping("users/{userId}/orders/{orderId}/delivered")
    public ResponseEntity<ResponseDTO> deliveredOrder(@PathVariable Long userId,
                                                           @PathVariable Long orderId)
    {
        ResponseDTO responseDTO = orderService.deliverredOrder(userId,orderId);
        return new ResponseEntity<>(responseDTO,HttpStatus.OK);
    }

    @PatchMapping("users/{userId}/orders/{orderId}/returnRequest")
    public ResponseEntity<ResponseDTO> returnRequestOrder(@PathVariable Long userId,
                                                           @PathVariable Long orderId)
    {
        ResponseDTO responseDTO = orderService.returnRequestOrder(userId,orderId);
        return new ResponseEntity<>(responseDTO,HttpStatus.OK);
    }

    @PatchMapping("users/{userId}/orders/{orderId}/returned")
    public ResponseEntity<ResponseDTO> returnedOrder(@PathVariable Long userId,
                                                           @PathVariable Long orderId)
    {
        ResponseDTO responseDTO = orderService.returnedOrder(userId,orderId);
        return new ResponseEntity<>(responseDTO,HttpStatus.OK);
    }

    @PatchMapping("users/{userId}/orders/{orderId}/rejected")
    public ResponseEntity<ResponseDTO> rejectedOrder(@PathVariable Long userId,
                                                           @PathVariable Long orderId)
    {
        ResponseDTO responseDTO = orderService.rejectedOrder(userId,orderId);
        return new ResponseEntity<>(responseDTO,HttpStatus.OK);
    }

    @PatchMapping("users/{userId}/orders/{orderId}/refund")
    public ResponseEntity<ResponseDTO> refundOrder(@PathVariable Long userId,
                                                     @PathVariable Long orderId)
    {
        ResponseDTO responseDTO = orderService.refundOrder(userId,orderId);
        return new ResponseEntity<>(responseDTO,HttpStatus.OK);
    }

}
