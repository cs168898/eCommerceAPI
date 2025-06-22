package com.ecommerce.ecommerce_api.entity.associativeEntity;

import com.ecommerce.ecommerce_api.entity.Cart;
import com.ecommerce.ecommerce_api.entity.Product;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    // the ManyToOne annotation means that there is many CartItem to one cart
    @ManyToOne
    @JsonBackReference("cart-cartitem")
    private Cart cart;

    // reference the product id , because if the prices change in the future
    // and if we stored 'product' in the past , the price would not be reflecting
    // the most updated version
    @ManyToOne
    @JoinColumn(name = "product_id")
    @JsonBackReference("product-cartitem")
    private Product product;

    // the number of product in the cart
    private int quantity;
}

