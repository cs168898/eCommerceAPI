package com.ecommerce.ecommerce_api.service.serviceImpl;

import com.ecommerce.ecommerce_api.dto.AddToCartRequest;
import com.ecommerce.ecommerce_api.dto.ApiResponse;
import com.ecommerce.ecommerce_api.dto.CartDto;
import com.ecommerce.ecommerce_api.entity.Cart;
import com.ecommerce.ecommerce_api.entity.Product;
import com.ecommerce.ecommerce_api.entity.Users;
import com.ecommerce.ecommerce_api.entity.associativeEntity.CartItem;
import com.ecommerce.ecommerce_api.repository.cartItemRepository;
import com.ecommerce.ecommerce_api.repository.productRepository;
import com.ecommerce.ecommerce_api.repository.userRepository;
import com.ecommerce.ecommerce_api.service.cartService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class cartServiceImpl implements cartService {

    @Autowired
    private userRepository userRepository;

    @Autowired
    private productRepository productRepository;

    @Autowired
    private cartItemRepository cartItemRepository;

    public ApiResponse<CartDto> findCartForUser(String email){

        Users user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found with email: " + email));

        CartDto cartDto = CartDto.toCartDto(user.getCart());

        return new ApiResponse<>(true, "Successfully obtained the user cart", cartDto);
    }

    @Transactional // transactional means that spring intercepts the call adn initiates a db transaction
    public ApiResponse<Void> addToCart(Integer productId, String email, AddToCartRequest addToCartRequest){

        Users user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found with email: " + email));

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("ProductId not found: " + productId));


        // get the users cart
        Cart cart = user.getCart();

        if (cart == null) {
            // return RuntimeException if for some reason the cart dont exist
            throw new RuntimeException("User with email: " + email + " does not have an associated cart.");
        }


        // check if the items already exist
        Optional<CartItem> existingItemOpt = cart.getItems().stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst();

        if(existingItemOpt.isPresent()){
            // if the item already exist in cart
            CartItem existingItem = existingItemOpt.get();
            existingItem.setQuantity(existingItem.getQuantity() + addToCartRequest.getQuantity());
        } else {
            // if the item is new
            CartItem cartItem = new CartItem();
            // set the cartItem
            cartItem.setCart(cart);
            cartItem.setProduct(product);
            cartItem.setQuantity(addToCartRequest.getQuantity());

            // STOPPED HERE AS OF 20/6/2025 2:54AM
            cartItemRepository.save(cartItem);

        }

        return new ApiResponse<>(true, "Product Added to cart");
    }

    @Transactional
    public ApiResponse<Void> removeFromCart(String email, Integer productId){

        Users user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found with email: " + email));

        Cart cart = user.getCart();

        // check if the items already exist
        Optional<CartItem> existingItemOpt = cart.getItems().stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst();

        if(existingItemOpt.isPresent()){
            // if the item exist
           CartItem itemToRemove = existingItemOpt.get();

           cart.getItems().remove(itemToRemove);

           cartItemRepository.delete((itemToRemove));

           return new ApiResponse<>(true, "The product has been successfully removed");
        } else{
            return new ApiResponse<>(false, "The productId" + productId + "does not exist in the cart");
        }
    }
}

