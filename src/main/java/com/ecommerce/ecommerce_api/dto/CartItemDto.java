package com.ecommerce.ecommerce_api.dto;

import com.ecommerce.ecommerce_api.entity.associativeEntity.CartItem;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CartItemDto {
    private Integer id;
    private Integer productId;
    private int quantity;

    // converts CartItem entity to CartItemDto
    public static CartItemDto toCartItemDto(CartItem cartItem) {
        CartItemDto dto = new CartItemDto();
        dto.setId(cartItem.getId());
        dto.setProductId(cartItem.getProduct().getId());
        dto.setQuantity(cartItem.getQuantity());
        return dto;
    }
}
