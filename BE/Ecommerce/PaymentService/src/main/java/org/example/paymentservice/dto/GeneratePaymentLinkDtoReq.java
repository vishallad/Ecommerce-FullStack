package org.example.paymentservice.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GeneratePaymentLinkDtoReq {
    String orderId;
    Long productId;
}
