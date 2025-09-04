package org.example.paymentservice.servicies;

import org.springframework.stereotype.Service;

@Service
public class RazorPayGateway implements PaymentGateway {

    @Override
    public String genertePaymentLink(String ordereId,Long productId,Long amount,String productName) {
        return  "";
    };
}
