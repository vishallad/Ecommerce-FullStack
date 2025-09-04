package org.example.capstoneproject.dtos;

import lombok.Getter;
import lombok.Setter;
import org.example.capstoneproject.models.Product;

@Getter
@Setter
public class CreateOrderDto {
    private Long userId;
    private Long productId;
}
