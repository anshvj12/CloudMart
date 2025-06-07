package com.charbhujatech.cloudmart.Model;

import com.charbhujatech.cloudmart.enums.PaymentMode;
import com.charbhujatech.cloudmart.enums.PaymentStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Getter @Setter
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long paymentId;

    @ManyToOne(cascade = {CascadeType.DETACH,CascadeType.MERGE,CascadeType.REFRESH,CascadeType.PERSIST})
    @JoinColumn(name = "user_id")
    private User user;

    @OneToOne(cascade = {CascadeType.DETACH,CascadeType.MERGE,CascadeType.REFRESH,CascadeType.PERSIST})
    @JoinColumn(name = "order_id")
    private Order order;

    private Double amount;

    private PaymentMode mode;

    private PaymentStatus paymentStatus;

    private Date paymentDate;
}
