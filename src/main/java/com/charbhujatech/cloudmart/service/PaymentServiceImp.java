package com.charbhujatech.cloudmart.service;

import com.charbhujatech.cloudmart.Model.Order;
import com.charbhujatech.cloudmart.Model.Payment;
import com.charbhujatech.cloudmart.Model.User;
import com.charbhujatech.cloudmart.dao.OrderRepository;
import com.charbhujatech.cloudmart.dao.PaymentRepository;
import com.charbhujatech.cloudmart.dao.UserRepository;
import com.charbhujatech.cloudmart.dto.PaymentRequestDTO;
import com.charbhujatech.cloudmart.dto.ResponseDTO;
import com.charbhujatech.cloudmart.exception.ResourceNotFoundException;
import com.charbhujatech.cloudmart.util.ConstantsString;
import com.charbhujatech.cloudmart.enums.PaymentStatus;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
@AllArgsConstructor
public class PaymentServiceImp implements PaymentService{

    private final OrderRepository orderRepository;

    private final UserRepository userRepository;

    private final PaymentRepository paymentRepository;

    private final OrderService orderService;

    /**
     * @param userId
     * @param orderId
     * @param paymentRequestDTO
     * @return
     */
    @Override
    @Transactional
    public ResponseDTO orderPayment(Long userId, Long orderId, PaymentRequestDTO paymentRequestDTO) {

        User user = userRepository.findById(userId).orElseThrow(
                () -> new ResourceNotFoundException(ConstantsString.USER_NOT_FOUND));

        Order order = orderRepository.findById(orderId).orElseThrow(
                () -> new ResourceNotFoundException(ConstantsString.ORDER_NOT_FOUND));

        Payment payment = new Payment();
        payment.setPaymentDate(new Date());
        payment.setMode(paymentRequestDTO.getPaymentMode());
        payment.setAmount(payment.getAmount());
        payment.setPaymentStatus(PaymentStatus.SUCCESS);
        payment.setOrder(order);
        payment.setUser(user);

        final Payment savedPayment = paymentRepository.saveAndFlush(payment);

        orderService.paymentOrder(order,userId,orderId);

        return new ResponseDTO(HttpStatus.OK.toString(), ConstantsString.PAYMENT_SUCCESS);
    }

}
