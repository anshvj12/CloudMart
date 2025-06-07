package com.charbhujatech.cloudmart.Model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Getter
@Setter
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long reviewId;

    protected String reviewText;

    protected Date reviewDate;

    protected int rating;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE , CascadeType.REFRESH, CascadeType.DETACH})
    @JoinColumn( name = "user_id" , nullable = true)
    protected User user;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE , CascadeType.REFRESH, CascadeType.DETACH})
    @JoinColumn(name = "product_id" , nullable = true)
    protected Product product;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE , CascadeType.REFRESH, CascadeType.DETACH})
    @JoinColumn(name = "order_id" )
    protected Order order;
}
