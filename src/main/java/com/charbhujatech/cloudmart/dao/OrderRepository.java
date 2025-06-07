package com.charbhujatech.cloudmart.dao;

import com.charbhujatech.cloudmart.Model.Order;
import com.charbhujatech.cloudmart.Model.User;
import com.charbhujatech.cloudmart.enums.OrderStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query("DELETE Order o WHERE o.orderId = :orderId")
    void deleteByOrderId(@Param(value="orderId") Long orderId);

    Page<Order> findByUser(User user, PageRequest pageable);

    Page<Order> findByOrderStatusAndUser(OrderStatus orderStatus, User user, PageRequest pageable);
}
