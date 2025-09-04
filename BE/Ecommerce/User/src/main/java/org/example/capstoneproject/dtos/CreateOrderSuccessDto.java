package org.example.capstoneproject.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateOrderSuccessDto {
    private String orderId;
    private Long productId;
}
