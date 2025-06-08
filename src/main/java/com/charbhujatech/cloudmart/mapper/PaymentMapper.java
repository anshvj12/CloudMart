package com.charbhujatech.cloudmart.mapper;

import com.charbhujatech.cloudmart.Model.Payment;
import com.charbhujatech.cloudmart.dto.PaymentResponseDTO;
import com.charbhujatech.cloudmart.dto.PaymentResponseDTOS;
import org.springframework.data.domain.Page;

import java.util.Iterator;

public class PaymentMapper {

    public static void mapToPaymentResponseDTO(Payment payment, PaymentResponseDTO paymentResponseDTO) {

        paymentResponseDTO.setId(payment.getPaymentId());
        if( payment.getMode() != null)
            paymentResponseDTO.setPaymentMode(payment.getMode());
        paymentResponseDTO.setAmount(payment.getAmount());
        if(payment.getPaymentDate() != null)
            paymentResponseDTO.setPaymentDate(payment.getPaymentDate());
        if(payment.getPaymentStatus() != null)
            paymentResponseDTO.setPaymentStatus(payment.getPaymentStatus());
    }

    public static void mapToPaymentResponseDTOS(Page<Payment> allPayment, PaymentResponseDTOS paymentResponseDTOS) {

        paymentResponseDTOS.setTotalElements(allPayment.getTotalElements());
        paymentResponseDTOS.setTotalPages(allPayment.getTotalPages());
        paymentResponseDTOS.setPageSize(allPayment.getSize());
        paymentResponseDTOS.setPageNumber(allPayment.getNumber());

        final Iterator<Payment> iterator = allPayment.stream().iterator();
        while(iterator.hasNext())
        {
            Payment payment = iterator.next();
            PaymentResponseDTO paymentResponseDTO = new PaymentResponseDTO();
            mapToPaymentResponseDTO(payment,paymentResponseDTO);
            paymentResponseDTOS.getResponseDTOList().add(paymentResponseDTO);
        }

    }
}
