package com.charbhujatech.cloudmart.dto;

import com.charbhujatech.cloudmart.enums.PaymentMode;
import com.charbhujatech.cloudmart.enums.PaymentStatus;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter @Setter
public class PaymentResponseDTO {

    private Long id;

    private Double amount;

    private PaymentMode paymentMode;

    private PaymentStatus paymentStatus;

    private Date paymentDate;
}
