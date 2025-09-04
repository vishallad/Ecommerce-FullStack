package org.example.capstoneproject.dtos;

import lombok.Getter;
import lombok.Setter;
import org.example.capstoneproject.models.Category;
import org.example.capstoneproject.models.Product;

@Getter
@Setter
public class FakeStoreProductDto {
    private long id;
    private String title;
    private String description;
    private String image;
    private double price;
    private String category;


    public Product toProduct(){
        Product product = new Product();
        product.setId(id);
        product.setName(title);
        product.setDescription(description);
        product.setImageUrl(image);
        product.setPrice(price);

        Category category1 = new Category();
        category1.setName(category);
        product.setCategory(category1);
        return product;
    }

}
