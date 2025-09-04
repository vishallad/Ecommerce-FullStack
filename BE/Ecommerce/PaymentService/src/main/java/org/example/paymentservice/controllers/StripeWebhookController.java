package org.example.paymentservice.controllers;

import com.stripe.model.Event;
import com.stripe.model.PaymentIntent;
import com.stripe.model.PaymentLink;
import com.stripe.net.ApiResource;
import org.example.paymentservice.dto.OrderProduct;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("api/stripeWebhook")
public class StripeWebhookController {

    @PostMapping()
    public void webhook(@RequestBody String payload) {
        RestTemplate restTemplate = new RestTemplate();
        try {
            Event event = ApiResource.GSON.fromJson(payload, Event.class);
             if("payment_intent.succeeded".equals(event.getType())) {
                PaymentIntent paymentIntent = (PaymentIntent) event.getDataObjectDeserializer().getObject().orElse(null);
                if(paymentIntent != null) {
                System.out.println("payment_intent.succeeded OrderId => " + paymentIntent.getMetadata().get("orderId"));
                System.out.println("ProductId => " + paymentIntent.getMetadata().get("productId"));
                    ResponseEntity order = restTemplate.postForEntity("http://localhost:8080/order/order-success-webhook/"+paymentIntent.getMetadata().get("orderId"), null, String.class);
                    }


            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
