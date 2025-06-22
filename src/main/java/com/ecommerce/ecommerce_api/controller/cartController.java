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

import java.security.Principal;
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
    @GetMapping("/findAll")
    public ResponseEntity<?> findAll(Principal principal){

        String email = principal.getName();

        try {
            ApiResponse<CartDto> response = cartService.findCartForUser(email);

            if (!response.isSuccess()) {
                return ResponseEntity
                        .status(HttpStatus.BAD_REQUEST)
                        .body(response.getMessage());
            }

            return ResponseEntity.ok(response.getData());
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND) // e.g., 404 Not Found
                    .body(e.getMessage());
        }

    }

    // add item to cart WORK IN PROGRESS
    @PostMapping("/add/{productId}")
    public ResponseEntity<String> addItem(@PathVariable("productId") Integer productId,
                                          Principal principal,
                                          @RequestBody AddToCartRequest addToCartRequest){
        // this will check if the user is authenticated or not

        String email = principal.getName();
        try{
            ApiResponse<Void> response = cartService.addToCart(productId, email, addToCartRequest);
            if (!response.isSuccess()) {
                return ResponseEntity
                        .status(HttpStatus.BAD_REQUEST)
                        .body(response.getMessage());
            }

            return ResponseEntity.ok(response.getMessage());
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND) // e.g., 404 Not Found
                    .body(e.getMessage());
        }

    }

    @PostMapping("/remove/{productId}")
    public ResponseEntity<String> removeFromCart(Principal principal, @PathVariable Integer productId){

        String email = principal.getName();

        try {
            ApiResponse<Void> response = cartService.removeFromCart(email, productId);

            if(!response.isSuccess()){
                return ResponseEntity
                        .status(HttpStatus.BAD_REQUEST)
                        .body(response.getMessage());
            }

            return ResponseEntity.ok(response.getMessage());
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND) // e.g., 404 Not Found
                    .body(e.getMessage());
        }
    }
}
