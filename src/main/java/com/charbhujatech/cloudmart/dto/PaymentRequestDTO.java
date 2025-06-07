package com.charbhujatech.cloudmart.dto;

import com.charbhujatech.cloudmart.enums.PaymentMode;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class PaymentRequestDTO {

    private Double amount;

    private PaymentMode paymentMode;

}
