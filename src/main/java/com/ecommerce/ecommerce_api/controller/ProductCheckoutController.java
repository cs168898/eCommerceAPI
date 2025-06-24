package com.ecommerce.ecommerce_api.controller;

import com.ecommerce.ecommerce_api.dto.ApiResponse;
import com.ecommerce.ecommerce_api.dto.CartDto;
import com.ecommerce.ecommerce_api.dto.ProductRequest;
import com.ecommerce.ecommerce_api.dto.StripeResponse;
import com.ecommerce.ecommerce_api.entity.Cart;
import com.ecommerce.ecommerce_api.service.StripeService;
import com.ecommerce.ecommerce_api.service.cartService;
import com.ecommerce.ecommerce_api.service.serviceImpl.StripeServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/api")
public class ProductCheckoutController {

    private final StripeService stripeService;

    private final cartService cartService;

    public ProductCheckoutController(StripeServiceImpl stripeService,
                                     cartService cartService){
        this.stripeService = stripeService;
        this.cartService = cartService;
    }

    @PostMapping("/checkout")
    public ResponseEntity<StripeResponse> checkoutProducts(Principal principal){

        String email = principal.getName();

        StripeResponse stripeResponse = stripeService.checkoutProducts( email );
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(stripeResponse);
    }
}
