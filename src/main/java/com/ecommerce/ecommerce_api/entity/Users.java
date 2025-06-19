package com.ecommerce.ecommerce_api.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "users")
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty
    private String username;

    @NotEmpty
    @Column(unique = true)
    private String email;

    @NotEmpty
    private String password;

    private String phoneNumber;
    private String shippingAddress;
    private String billingAddress;
    private LocalDateTime createdAt = LocalDateTime.now();
    private String roles = "ROLE_USER";

    // create a column called cart_id and cascade it to cart table
    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name="cart_id", nullable = false)
    @JsonManagedReference
    private Cart cart;
}
