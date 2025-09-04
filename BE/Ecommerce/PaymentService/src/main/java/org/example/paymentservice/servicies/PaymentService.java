package org.example.paymentservice.servicies;

public interface PaymentService {
    String initiatePayment(String ordereId,Long productId) throws Exception;
}
