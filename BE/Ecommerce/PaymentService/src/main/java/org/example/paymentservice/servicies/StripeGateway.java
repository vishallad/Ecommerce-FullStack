package org.example.paymentservice.servicies;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentLink;
import com.stripe.model.Price;
import com.stripe.param.PaymentLinkCreateParams;
import com.stripe.param.PriceCreateParams;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public class StripeGateway implements PaymentGateway {

    private String apiKey = "sk_test_51RAXC04WnuquZ2Y8f4w2SxBfTBqHaqbzQcOciGbwEijrW3D71pTjUtXCZ5QOU9nHYK6EHe3CirXRUH53pojQAx5Y00zzPJt2XB";

    @Override
    public String genertePaymentLink(String orderId,Long productId,Long amount,String ProductName){
        try{
            Stripe.apiKey = this.apiKey;
            PaymentLinkCreateParams params =
                    PaymentLinkCreateParams.builder()
                            .addLineItem(
                                    PaymentLinkCreateParams.LineItem.builder()
                                            .setPrice(getPrice(amount,ProductName).getId())
                                            .setQuantity(1L)
                                            .build()
                            )
                            .setPaymentIntentData(
                                    PaymentLinkCreateParams.PaymentIntentData.builder()
                                            .putMetadata("orderId", orderId)
                                            .putMetadata("productId", productId.toString())
                                            .build()
                            )
                            .setAfterCompletion(PaymentLinkCreateParams.AfterCompletion.builder()
                                    .setType(PaymentLinkCreateParams.AfterCompletion.Type.REDIRECT)
                                    .setRedirect(PaymentLinkCreateParams.AfterCompletion.Redirect.builder()
                                            .setUrl("http://localhost:3000/?payment=success&pid=" + productId).build()).
                                    build()
                            )
                            .build();

            PaymentLink paymentLink = PaymentLink.create(params);
            return paymentLink.getUrl();

        }catch(StripeException ex){
            throw new RuntimeException(ex.getMessage());
        }
    };

    private Price getPrice(Long amount,String ProductName){
        try{
            PriceCreateParams params =
                    PriceCreateParams.builder()
                            .setCurrency("inr")
                            .setUnitAmount(amount)
                            .setProductData(
                                    PriceCreateParams
                                            .ProductData
                                            .builder()
                                            .setName(ProductName)
                                            .build()
                            )
                            .build();

            Price price = Price.create(params);
            return price;
        }catch (StripeException exception){
            throw new RuntimeException(exception.getMessage());
        }
    }
}
