package com.ecommerce.ecommerce_api.service;

import com.ecommerce.ecommerce_api.dto.AddToCartRequest;
import com.ecommerce.ecommerce_api.dto.ApiResponse;

public interface cartService {
    public ApiResponse<Void> addToCart(Integer userId, Integer productId, AddToCartRequest addToCartRequest);

}
