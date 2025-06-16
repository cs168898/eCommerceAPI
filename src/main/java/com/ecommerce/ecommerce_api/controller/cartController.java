package com.ecommerce.ecommerce_api.controller;

import com.ecommerce.ecommerce_api.entity.Cart;
import com.ecommerce.ecommerce_api.entity.Product;
import com.ecommerce.ecommerce_api.repository.cartContentRepository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/cart")
public class cartController {

    // dependency
    private final cartContentRepository repository;

    public cartController(cartContentRepository repository) {
        this.repository = repository;
    }

    // find all the products that are inside the cart content repository
    public List<Cart> findAll(){
        return repository.findAll();
    }

}
