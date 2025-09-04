package org.example.paymentservice.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderProduct {
    String orderId;
    Long productId;
    Double amount;
    String productName;
}
