package com.charbhujatech.cloudmart.Model;

import com.charbhujatech.cloudmart.enums.Roles;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long userId;

    protected String firstName;

    protected String lastName;

    protected String password;

    @Column(name = "email", unique = true)
    protected String email;

    @Column(unique = true)
    protected String phone;

    @OneToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE , CascadeType.REFRESH, CascadeType.DETACH}
            , fetch = FetchType.LAZY, mappedBy = "user")
    protected Set<Review> review = new HashSet<>();

    @OneToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE , CascadeType.REFRESH, CascadeType.DETACH}
            , fetch = FetchType.LAZY, mappedBy = "user")
    protected Set<Address> addresses = new HashSet<>();

    private Roles roles;

}