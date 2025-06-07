package com.charbhujatech.cloudmart.controller;

import com.charbhujatech.cloudmart.dto.PaymentRequestDTO;
import com.charbhujatech.cloudmart.dto.ResponseDTO;
import com.charbhujatech.cloudmart.service.PaymentService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@AllArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping("users/{userId}/orders/{orderId}/payments")
    public ResponseEntity<ResponseDTO> orderPayment(@PathVariable Long userId, @PathVariable Long orderId,
                                                   @RequestBody PaymentRequestDTO paymentRequestDTO)
    {
        ResponseDTO responseDTO = paymentService.orderPayment(userId,orderId,paymentRequestDTO);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

}
