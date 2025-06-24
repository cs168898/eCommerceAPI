package com.ecommerce.ecommerce_api.dto;

import com.ecommerce.ecommerce_api.entity.Cart;
import com.ecommerce.ecommerce_api.entity.associativeEntity.CartItem;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class CartDto {
    private Integer id;
    private BigDecimal totalPrice;
    private List<CartItemDto> items;
    private String currency;

    public static CartDto toCartDto(Cart cart) {
        CartDto cartDto = new CartDto();
        cartDto.setId(cart.getId());
        cartDto.setTotalPrice(cart.getTotalPrice());
        cartDto.setCurrency(cart.getCurrency());
        // Map each CartItem entity to CartItemDto
        if (cart.getItems() != null) {
            List<CartItemDto> itemDtos = cart.getItems()
                    .stream()
                    .map(CartItemDto::toCartItemDto)
                    .collect(Collectors.toList());
            cartDto.setItems(itemDtos);
        }
        return cartDto;
    }
}

