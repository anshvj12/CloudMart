package com.charbhujatech.cloudmart.dao;

import com.charbhujatech.cloudmart.Model.Order;
import com.charbhujatech.cloudmart.Model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PaymentRepository extends JpaRepository<Payment,Long> {

    Optional<Payment> findByOrder(Order order);

}
