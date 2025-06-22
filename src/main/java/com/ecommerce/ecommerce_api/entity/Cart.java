package com.ecommerce.ecommerce_api.entity;


import com.ecommerce.ecommerce_api.entity.associativeEntity.CartItem;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Cart{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    // the cascade is when u save a cart, it also persist
    // any new cartItem entities that have been added to the list
    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL)
    @JsonManagedReference("cart-cartitem")
    private List<CartItem> items;
    private BigDecimal totalPrice;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // it is to say that the other attribute/column/field in another table managing the foreign key
    @OneToOne(mappedBy = "cart")// <--- this "cart" refers to the field in Users.java
    @JsonBackReference
    private Users user;
}
