package com.ecommerce.ecommerce_api.service.serviceImpl;

import com.ecommerce.ecommerce_api.dto.ProductRequest;
import com.ecommerce.ecommerce_api.dto.StripeResponse;
import com.ecommerce.ecommerce_api.service.StripeService;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class StripeServiceImpl implements StripeService {

    @Value("${stripe.secretKey}")
    private String secretKey;
    //stripe -API
    //-> productName, amount, quantity, currency  ===== this are the args stripe expects
    //-> return sessionId and checkout url

    public StripeResponse checkoutProducts(ProductRequest productRequest){
        Stripe.apiKey=secretKey;
        SessionCreateParams.LineItem.PriceData.ProductData productData = SessionCreateParams.LineItem.PriceData.ProductData.builder()
                .setName(productRequest.getName()).build();

        //set the price for
        SessionCreateParams.LineItem.PriceData priceData = SessionCreateParams.LineItem.PriceData.builder()
                .setCurrency(productRequest.getCurrency() == null ? "USD" : productRequest.getCurrency())
                .setUnitAmount(productRequest.getAmount())
                .setProductData(productData)
                .build();

        SessionCreateParams.LineItem lineItem = SessionCreateParams.LineItem.builder()
                .setQuantity(productRequest.getQuantity())
                .setPriceData(priceData)
                .build();

        SessionCreateParams params = SessionCreateParams.builder()
                .setMode(SessionCreateParams.Mode.PAYMENT)
                .setSuccessUrl("http://localhost:8080/success")
                .setCancelUrl("http://localhost:8080/success")
                .addLineItem(lineItem)
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
