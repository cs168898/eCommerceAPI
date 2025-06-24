package com.ecommerce.ecommerce_api.dto;

import com.ecommerce.ecommerce_api.entity.Cart;
import com.ecommerce.ecommerce_api.entity.associativeEntity.CartItem;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductRequest {
    private Long amount;
    private Long quantity;
    private String name;
    private String currency;
}
