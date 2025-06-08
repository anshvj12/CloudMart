package com.charbhujatech.cloudmart.service;

import com.charbhujatech.cloudmart.dto.PaymentRequestDTO;
import com.charbhujatech.cloudmart.dto.PaymentResponseDTO;
import com.charbhujatech.cloudmart.dto.PaymentResponseDTOS;
import com.charbhujatech.cloudmart.dto.ResponseDTO;
import org.springframework.stereotype.Service;

@Service
public interface PaymentService {

    ResponseDTO orderPayment(Long userId, Long orderId, PaymentRequestDTO paymentRequestDTO);

    PaymentResponseDTO getOrderPayment(Long userId, Long orderId, Long paymentId);

    PaymentResponseDTOS getUserPayment(Long userId, int page, String sortBy, boolean ascending);
}
