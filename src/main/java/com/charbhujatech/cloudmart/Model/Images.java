package com.charbhujatech.cloudmart.Model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Images {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long imageId;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE , CascadeType.REFRESH, CascadeType.DETACH})
    @JoinColumn( name = "product_id", nullable = true)
    protected Product product;

    protected String imageUrl;
}
