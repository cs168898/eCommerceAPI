package com.ecommerce.ecommerce_api.dto;

import com.ecommerce.ecommerce_api.entity.Product;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
@Getter
@Setter

public class ProductDto {
    private Integer id;
    private String name;
    private String description;
    private BigDecimal price;
    private Integer quantity;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String url;
}
