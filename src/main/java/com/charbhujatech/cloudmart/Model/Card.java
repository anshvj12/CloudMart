package com.charbhujatech.cloudmart.Model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
public class Card {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long cardId;

    protected double totalPrice;

    protected Date expectedDeliveryDate;

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE , CascadeType.REFRESH , CascadeType.DETACH})
    @JoinColumn( name = "user_id" , nullable = true)
    protected User user;

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.DETACH, CascadeType.MERGE , CascadeType.REFRESH}
    , mappedBy = "card" , fetch = FetchType.EAGER )
    protected Set<CardProduct> cardProducts = new HashSet<>();

}
