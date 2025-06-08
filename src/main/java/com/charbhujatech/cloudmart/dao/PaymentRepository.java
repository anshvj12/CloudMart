package com.charbhujatech.cloudmart.dao;

import com.charbhujatech.cloudmart.Model.Order;
import com.charbhujatech.cloudmart.Model.Payment;
import com.charbhujatech.cloudmart.Model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PaymentRepository extends JpaRepository<Payment,Long> {

    Optional<Payment> findByOrder(Order order);

    Page<Payment> findByUser(User user, PageRequest pageable);
}
