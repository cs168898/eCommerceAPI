package com.ecommerce.ecommerce_api.controller;

import com.ecommerce.ecommerce_api.dto.AddToCartRequest;
import com.ecommerce.ecommerce_api.dto.ApiResponse;
import com.ecommerce.ecommerce_api.dto.CartDto;
import com.ecommerce.ecommerce_api.entity.Cart;
import com.ecommerce.ecommerce_api.entity.Users;
import com.ecommerce.ecommerce_api.entity.associativeEntity.CartItem;
import com.ecommerce.ecommerce_api.repository.cartContentRepository;
import com.ecommerce.ecommerce_api.repository.productRepository;
import com.ecommerce.ecommerce_api.repository.userRepository;
import com.ecommerce.ecommerce_api.service.cartService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/cart")
public class cartController {

    // dependency
    private final cartContentRepository cartContentRepository;

    private final userRepository userRepository;

    private final cartService cartService;

    @Autowired
    public cartController(cartContentRepository cartContentRepository,
                          userRepository userRepository,
                          cartService cartService
    ) {
        this.cartContentRepository = cartContentRepository;
        this.userRepository = userRepository;
        this.cartService = cartService;
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
    @PostMapping("/{userId}/add/{productId}")
    public ResponseEntity<String> addItem(@PathVariable("productId") Integer productId,
                                          @PathVariable("userId") Integer userId,
                                          @RequestBody AddToCartRequest addToCartRequest){
        ApiResponse<Void> response = cartService.addToCart(productId, userId, addToCartRequest);
        if (!response.isSuccess()) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(response.getMessage());
        }

        return ResponseEntity.ok(response.getMessage());
    }

}
