package com.ecommerce.ecommerce_api.service.serviceImpl;

import com.ecommerce.ecommerce_api.dto.CartDto;
import com.ecommerce.ecommerce_api.dto.CartItemDto;
import com.ecommerce.ecommerce_api.dto.ProductRequest;
import com.ecommerce.ecommerce_api.dto.StripeResponse;
import com.ecommerce.ecommerce_api.entity.Cart;
import com.ecommerce.ecommerce_api.entity.Users;
import com.ecommerce.ecommerce_api.entity.associativeEntity.CartItem;
import com.ecommerce.ecommerce_api.repository.userRepository;
import com.ecommerce.ecommerce_api.service.StripeService;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class StripeServiceImpl implements StripeService {

    @Value("${stripe.secretKey}")
    private String secretKey;
    //stripe -API
    //-> productName, amount, quantity, currency  ===== this are the args stripe expects
    //-> return sessionId and checkout url

    @Autowired
    private userRepository userRepository;


    public StripeResponse checkoutProducts(String email){
        Stripe.apiKey=secretKey;

        Users user =  userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found with email: " + email));

        Cart cart = user.getCart();

        List<SessionCreateParams.LineItem> lineItems = new ArrayList<>();

        for (CartItem item : cart.getItems()){
            // get the name of the product to set as the productData
            SessionCreateParams.LineItem.PriceData.ProductData productData = SessionCreateParams.LineItem.PriceData.ProductData.builder()
                    .setName(item.getProduct().getName())
                    .build();

            //set the price for line item
            SessionCreateParams.LineItem.PriceData priceData = SessionCreateParams.LineItem.PriceData.builder()
                    .setCurrency(cart.getCurrency() == null ? "USD" : cart.getCurrency())
                    .setUnitAmount(item.getProduct().getPrice().multiply(new BigDecimal("100")).longValue())
                    .setProductData(productData)
                    .build();

            // set the line item
            SessionCreateParams.LineItem lineItem = SessionCreateParams.LineItem.builder()
                    .setQuantity((long) item.getQuantity())
                    .setPriceData(priceData)
                    .build();

            lineItems.add(lineItem);

        }

        // set the parameters
        SessionCreateParams params = SessionCreateParams.builder()
                .setMode(SessionCreateParams.Mode.PAYMENT)
                .setSuccessUrl("http://localhost:8080/success")
                .setCancelUrl("http://localhost:8080/success")
                .addAllLineItem(lineItems)
                .build();

        Session session=null;

        try{
            session = Session.create(params);
        } catch (StripeException ex){
            System.out.println(ex.getMessage());
        }
        assert session != null;

        return StripeResponse.builder()
            .status("SUCCESS")
            .message("payment session created")
            .sessionId((session.getId()))
            .SessionUrl(session.getUrl())
            .build();
    }
}
