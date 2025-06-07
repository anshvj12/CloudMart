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
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long productId;

    protected String productName;

    protected Double price;

    protected Integer availableQuantity;

    protected Date productDate;

    protected String productDescription;

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE , CascadeType.REFRESH, CascadeType.DETACH},
            mappedBy = "product",  fetch = FetchType.LAZY)
    protected Set<Images> productImage = new HashSet<>();

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE , CascadeType.REFRESH, CascadeType.DETACH}
            ,mappedBy = "product", fetch = FetchType.LAZY)
    protected Set<Review> reviews = new HashSet<>();

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE , CascadeType.REFRESH, CascadeType.DETACH},
            mappedBy = "product", fetch = FetchType.LAZY)
    protected Set<CardProduct> cardProducts = new HashSet<>();

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE , CascadeType.REFRESH, CascadeType.DETACH},
            mappedBy = "product", fetch = FetchType.LAZY)
    protected Set<OrderProduct> orderProducts = new HashSet<>();

}
