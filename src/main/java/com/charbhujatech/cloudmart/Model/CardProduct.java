package com.charbhujatech.cloudmart.Model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class CardProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long cardProductId;

    @ManyToOne( cascade = {CascadeType.PERSIST, CascadeType.MERGE , CascadeType.REFRESH, CascadeType.DETACH},
    fetch = FetchType.LAZY)
    @JoinColumn(name = "card_id" ,nullable = true   )
    protected Card card;

    @ManyToOne( cascade = {CascadeType.PERSIST, CascadeType.MERGE , CascadeType.REFRESH, CascadeType.DETACH},
    fetch = FetchType.LAZY )
    @JoinColumn(name = "product_id" , nullable = true )
    protected Product product;

    protected int quantity;

}
