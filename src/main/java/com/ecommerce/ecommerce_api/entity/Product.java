package com.ecommerce.ecommerce_api.entity;


import com.ecommerce.ecommerce_api.entity.associativeEntity.CartItem;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Product{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty
    private String name;
    private String description;
    private BigDecimal price;
    // The total number of products in the shop
    private Integer quantity;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String url;

    // mapping the association between one product to many cart items. Only required for bidirectional access
    @OneToMany(mappedBy = "product")
    @JsonManagedReference("product-cartitem")
    private List<CartItem> cartItems;

}
