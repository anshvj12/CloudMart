package com.charbhujatech.cloudmart.Model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class OrderProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long orderProductId;

    @ManyToOne( cascade = {CascadeType.PERSIST, CascadeType.MERGE , CascadeType.REFRESH, CascadeType.DETACH},
            fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id" , nullable = true )
    protected Order order;

    @ManyToOne( cascade = {CascadeType.PERSIST, CascadeType.MERGE , CascadeType.REFRESH, CascadeType.DETACH},
            fetch = FetchType.LAZY )
    @JoinColumn(name = "product_id" , nullable = true )
    protected Product product;

    protected int quantity;

}
