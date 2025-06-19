package com.ecommerce.ecommerce_api.controller;

import com.ecommerce.ecommerce_api.dto.CartDto;
import com.ecommerce.ecommerce_api.entity.Cart;
import com.ecommerce.ecommerce_api.entity.Users;
import com.ecommerce.ecommerce_api.repository.cartContentRepository;
import com.ecommerce.ecommerce_api.repository.userRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/cart")
public class cartController {

    // dependency
    private final cartContentRepository cartContentRepository;

    private final userRepository userRepository;

    @Autowired
    public cartController(cartContentRepository cartContentRepository, userRepository userRepository) {
        this.cartContentRepository = cartContentRepository;
        this.userRepository = userRepository;
    }

    // find all the products that are inside the cart content repository
    @GetMapping("/{id}")
    public CartDto findAll(@PathVariable Integer id){

        Optional<Users> userOpt = userRepository.findById(id);

        if (userOpt.isPresent()) {
            Cart cart = userOpt.get().getCart();
            // convert the cart into its dto

            return CartDto.toCartDto(cart);
        } else {
            // else just return an empty CartDto
            return new CartDto();
        }

    }

    // add item to cart WORK IN PROGRESS
    @PostMapping("/add/{productId}")
    public boolean addItem(@PathVariable Integer productId){
        return false;
    }

}
