package com.ecommerce.ecommerce_api.service;

import com.ecommerce.ecommerce_api.dto.AddToCartRequest;
import com.ecommerce.ecommerce_api.dto.ApiResponse;
import com.ecommerce.ecommerce_api.dto.CartDto;

public interface cartService {


    public ApiResponse<CartDto> findCartForUser(String email);

    public ApiResponse<Void> addToCart(Integer productId, String email, AddToCartRequest addToCartRequest);

    public ApiResponse<Void> removeFromCart(String email, Integer productId);
}
