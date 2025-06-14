package com.ecommerce.ecommerce_api.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty
    private String username;
    private String email;
    private char[] password;
    private Integer phoneNumber;
    private String shippingAddress;
    private String billingAddress;

    @OneToOne
    @JoinColumn(name="cart_id", nullable = false)
    private Cart cart;
}
