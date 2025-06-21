package com.ecommerce.ecommerce_api.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddToCartRequest {

    private Integer quantity;

    // we can add any other field we want in the future
}
