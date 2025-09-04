package org.example.capstoneproject.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FakeProductRequestDto {
    private String title;
    private String description;
    private String image;
    private double price;
    private String category;
}
