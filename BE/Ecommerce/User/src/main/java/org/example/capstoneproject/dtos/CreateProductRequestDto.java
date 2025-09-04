package org.example.capstoneproject.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateProductRequestDto {
    private String name;
    private String description;
    private String imageUrl;
    private double price;
    private String category;
}
