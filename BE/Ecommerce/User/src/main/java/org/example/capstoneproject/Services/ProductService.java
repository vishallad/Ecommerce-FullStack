package org.example.capstoneproject.Services;

import org.example.capstoneproject.exceptions.ProductNotFoundException;
import org.example.capstoneproject.models.Category;
import org.example.capstoneproject.models.Product;

import java.util.List;
import java.util.Optional;

public interface ProductService {
    Optional<Product> getProductById(long id) throws ProductNotFoundException;
    List<Product> getAllProducts();
    Product createProduct(String name, String description, String imageUrl, double price, String category);


    List<Category> getAllCategory();
}
