package org.example.capstoneproject.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrdersProduct {
    String orderId;
    Long productId;
    Double amount;
    String productName;
}
