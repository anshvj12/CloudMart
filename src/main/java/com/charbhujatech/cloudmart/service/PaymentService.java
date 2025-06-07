package com.charbhujatech.cloudmart.service;

import com.charbhujatech.cloudmart.dto.PaymentRequestDTO;
import com.charbhujatech.cloudmart.dto.ResponseDTO;
import org.springframework.stereotype.Service;

@Service
public interface PaymentService {

    ResponseDTO orderPayment(Long userId, Long orderId, PaymentRequestDTO paymentRequestDTO);

}
