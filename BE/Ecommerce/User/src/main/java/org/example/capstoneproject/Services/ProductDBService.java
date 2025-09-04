package org.example.capstoneproject.Services;

import org.example.capstoneproject.Repositories.CategoryRepository;
import org.example.capstoneproject.Repositories.ProductRepository;
import org.example.capstoneproject.exceptions.ProductNotFoundException;
import org.example.capstoneproject.models.Category;
import org.example.capstoneproject.models.Product;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Primary
public class ProductDBService implements ProductService {

    ProductRepository productRepository;
    CategoryRepository categoryRepository;

    public ProductDBService(ProductRepository productRepository,CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Optional<Product> getProductById(long id) {
        Optional<Product> product = productRepository.findById(id);
//        if(product.isEmpty()){
//            throw new ProductNotFoundException("Product not found with id "+id);
//        }
        return Optional.ofNullable(product.get());
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public Product createProduct(String name, String description, String imageUrl, double price, String category) {
        Product product = new Product();
        product.setName(name);
        product.setDescription(description);
        product.setImageUrl(imageUrl);
        product.setPrice(price);

        Category categoryObj = getCategoryByName(category);
        product.setCategory(categoryObj);

        return productRepository.save(product);
    }

    private Category getCategoryByName(String name) {
        Optional<Category> category = categoryRepository.findByName(name);
        if(category.isPresent()) {
            return category.get();
        }else{
            Category categoryObj = new Category();
            categoryObj.setName(name);
            return categoryRepository.save(categoryObj);
        }
    }

    public List<Category> getAllCategory() {
        List<Category> categories = categoryRepository.findAll();
        return categories;
    }
}
