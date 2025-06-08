package com.charbhujatech.cloudmart.controller;

import com.charbhujatech.cloudmart.dto.PaymentRequestDTO;
import com.charbhujatech.cloudmart.dto.PaymentResponseDTO;
import com.charbhujatech.cloudmart.dto.PaymentResponseDTOS;
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

    @GetMapping("users/{userId}/orders/{orderId}/payments/{paymentId}")
    public ResponseEntity<PaymentResponseDTO> getOrderPayment( @PathVariable Long userId,
                                                               @PathVariable Long orderId,
                                                               @PathVariable Long paymentId)
    {
        PaymentResponseDTO paymentResponseDTO = paymentService.getOrderPayment(userId,orderId,paymentId);
        return new ResponseEntity<>(paymentResponseDTO,HttpStatus.OK);
    }

    @GetMapping("users/{userId}/payments")
    public ResponseEntity<PaymentResponseDTOS> getUserPayment(@PathVariable Long userId,
                                                              @RequestParam(defaultValue = "0") int page,
                                                              @RequestParam(defaultValue = "paymentDate") String sortBy,
                                                              @RequestParam(defaultValue = "false") boolean ascending)
    {
        PaymentResponseDTOS paymentResponseDTO = paymentService.getUserPayment(userId,page,sortBy,ascending);
        return new ResponseEntity<>(paymentResponseDTO,HttpStatus.OK);
    }

}
