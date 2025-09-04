package org.example.capstoneproject.Services;

import org.example.capstoneproject.dtos.FakeProductRequestDto;
import org.example.capstoneproject.dtos.FakeStoreProductDto;
import org.example.capstoneproject.exceptions.ProductNotFoundException;
import org.example.capstoneproject.models.Category;
import org.example.capstoneproject.models.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class FakeServiceProductImpl implements  ProductService{

    RestTemplate restTemplate;
    RedisTemplate<String,Object> redisTemplate;


    public FakeServiceProductImpl(RestTemplate restTemplate, RedisTemplate<String,Object> redisTemplate) {
        this.restTemplate = restTemplate;
        this.redisTemplate = redisTemplate;
    }

//    @Override
    public Optional<Product> getProductBy(long id) throws ProductNotFoundException {

        // check from redis cache and serve or call fakestore API

        Product productFromCache = (Product) redisTemplate.opsForValue().get(String.valueOf(id));
        if(productFromCache != null) {
            return Optional.of(productFromCache);
        }
        FakeStoreProductDto  productDto  =  restTemplate.getForObject("https://fakestoreapi.com/products/"+id, FakeStoreProductDto.class);
        System.out.println("productDto" + productDto.toString());

        if(productDto == null){
            throw new ProductNotFoundException("this Product for id " + id + " does not exist");
        }

        Product productFromFakeStore = productDto.toProduct();
        redisTemplate.opsForValue().set(String.valueOf(id), productFromFakeStore);

        return Optional.of(productFromFakeStore);
    }

    @Override
    public Optional<Product> getProductById(long id) throws ProductNotFoundException {

        // check from redis cache and serve or call fakestore API

        Product productFromCache = (Product) redisTemplate.opsForValue().get(String.valueOf(id));
        if(productFromCache != null) {
            return Optional.of(productFromCache);
        }
        FakeStoreProductDto  productDto  =  restTemplate.getForObject("https://fakestoreapi.com/products/"+id, FakeStoreProductDto.class);
        System.out.println("productDto" + productDto.toString());

        if(productDto == null){
            throw new ProductNotFoundException("this Product for id " + id + " does not exist");
        }

        Product productFromFakeStore = productDto.toProduct();
        redisTemplate.opsForValue().set(String.valueOf(id), productFromFakeStore);

        return Optional.of(productFromFakeStore);
    }

    //    @Override
    public List<Product> getAllProducts(){
        FakeStoreProductDto[] products = restTemplate.getForObject("https://fakestoreapi.com/products",FakeStoreProductDto[].class);
        List<Product> finalProducts = new ArrayList<>();

        for(FakeStoreProductDto productDto : products){
            finalProducts.add(productDto.toProduct());
        }
        return finalProducts;
    };

//    @Override
    public Product createProduct(String name, String description, String imageUrl, double price, String category){

        FakeProductRequestDto fakeStoreproductRequestDto = new FakeProductRequestDto();
        fakeStoreproductRequestDto.setTitle(name);
        fakeStoreproductRequestDto.setDescription(description);
        fakeStoreproductRequestDto.setImage(imageUrl);
        fakeStoreproductRequestDto.setPrice(price);
        fakeStoreproductRequestDto.setCategory(category);

        FakeStoreProductDto res = restTemplate.postForObject("https://fakestoreapi.com/products",fakeStoreproductRequestDto,FakeStoreProductDto.class);
        return res.toProduct();
    };

    @Override
    public List<Category> getAllCategory(){
        return new ArrayList<>();
    }
}
