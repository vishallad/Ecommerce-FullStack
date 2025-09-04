package org.example.paymentservice.servicies;

public interface PaymentGateway {

    String genertePaymentLink(String ordereId,Long productId,Long amount,String productName);

}
