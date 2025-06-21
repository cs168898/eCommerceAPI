package com.ecommerce.ecommerce_api.entity.associativeEntity;

import com.ecommerce.ecommerce_api.entity.Cart;
import com.ecommerce.ecommerce_api.entity.Product;
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
    private Cart cart;

    // reference the product id , because if the prices change in the future
    // and if we stored 'product' in the past , the price would not be reflecting
    // the most updated version
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    // the number of product in the cart
    private int quantity;
}

