package org.example.capstoneproject.dtos;

import lombok.*;
import org.example.capstoneproject.models.Product;

@Setter
@Getter
public class ProductResponseDto {

    private long id;
    private String name;
    private String description;
    private String imageUrl;
    private double price;
    private String category;

    public static ProductResponseDto from(Product product) {
        ProductResponseDto dto = new ProductResponseDto();
        dto.setId(product.getId());
        dto.setName(product.getName());
        dto.setDescription(product.getDescription());
        dto.setImageUrl(product.getImageUrl());
        dto.setPrice(product.getPrice());
        dto.setCategory(product.getCategory().getName());
        return dto;
    }

}
