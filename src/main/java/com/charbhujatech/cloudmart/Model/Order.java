package com.charbhujatech.cloudmart.Model;

import com.charbhujatech.cloudmart.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;

    private double totalPrice;

    private Date deliveryDate;

    private Date orderDate;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE , CascadeType.REFRESH , CascadeType.DETACH}
            , fetch = FetchType.LAZY)
    @JoinColumn( name = "user_id" , nullable = true)
    private User user;

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.DETACH, CascadeType.MERGE , CascadeType.REFRESH}
            , mappedBy = "order" , fetch = FetchType.LAZY )
    private Set<OrderProduct> orderProducts = new HashSet<>();

    private OrderStatus orderStatus;

}
