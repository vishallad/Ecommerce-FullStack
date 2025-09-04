package org.example.paymentservice.servicies;

import org.example.paymentservice.dto.OrderProduct;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class PaymentServiceImpl implements PaymentService {

    PaymentGatewaySelector paymentGatewaySelector;

    public PaymentServiceImpl(PaymentGatewaySelector paymentGatewaySelector) {
        this.paymentGatewaySelector = paymentGatewaySelector;
    }

    @Override
    public String initiatePayment(String ordereId,Long productId) throws Exception {

        RestTemplate restTemplate = new RestTemplate();
        try {
            ResponseEntity<OrderProduct> order = restTemplate.getForEntity("http://localhost:8081/api/order/ordersProduct/"+ordereId,OrderProduct.class);

            if (order.getStatusCode().is4xxClientError()) {
                throw new Exception("Order Not Found");
            }
            return paymentGatewaySelector
                    .get()
                    .genertePaymentLink(ordereId, productId,order.getBody().getAmount().longValue(),order.getBody().getProductName());
        }
        catch (Exception e){
             throw new Exception("Invalid Request" + e.getMessage());
        }

    };

}
