package com.ecommerce.ecommerce_api.service;

import com.ecommerce.ecommerce_api.dto.CartDto;
import com.ecommerce.ecommerce_api.dto.ProductRequest;
import com.ecommerce.ecommerce_api.dto.StripeResponse;
import org.springframework.stereotype.Service;


public interface StripeService {

    StripeResponse checkoutProducts(String email);

}
