package com.charbhujatech.cloudmart.Model;

import com.charbhujatech.cloudmart.enums.AddressType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long addressId;

    protected String firstLine;

    protected String secondLine;

    protected String landmarks;

    protected String postalCode;

    protected String city;

    protected String state;

    protected String country;

    protected AddressType addressType;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE , CascadeType.REFRESH, CascadeType.DETACH})
    @JoinColumn(name = "user_id" , nullable = true)
    protected User user;

}
