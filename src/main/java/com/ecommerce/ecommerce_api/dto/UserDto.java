package com.ecommerce.ecommerce_api.dto;

import com.ecommerce.ecommerce_api.entity.Users;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class UserDto {
    private Integer id;
    private String username;
    private String email;
    private String phoneNumber;
    private String shippingAddress;
    private String billingAddress;
    private CartDto cart; // create a CartDto too, to avoid recursion
    private boolean enabled;

    public static UserDto toUserDto(Users user) {

        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setUsername(user.getUsername());
        userDto.setEmail(user.getEmail());
        userDto.setPhoneNumber(user.getPhoneNumber());
        userDto.setShippingAddress(user.getShippingAddress());
        userDto.setBillingAddress(user.getBillingAddress());

        if (user.getCart() != null) {

            CartDto cartDto = CartDto.toCartDto(user.getCart());
            userDto.setCart(cartDto);
        }
        return userDto;
    }

}
