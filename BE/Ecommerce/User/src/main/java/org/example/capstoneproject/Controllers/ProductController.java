package org.example.capstoneproject.Controllers;

import org.example.capstoneproject.Services.OrderService;
import org.example.capstoneproject.Services.ProductService;
import org.example.capstoneproject.Services.SearchService;
import org.example.capstoneproject.commons.ApplicationCommons;
import org.example.capstoneproject.dtos.*;
import org.example.capstoneproject.exceptions.ProductNotFoundException;
import org.example.capstoneproject.models.Category;
import org.example.capstoneproject.models.Order;
import org.example.capstoneproject.models.OrderStatus;
import org.example.capstoneproject.models.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("api/product")
public class ProductController {

    ProductService productService;
    ApplicationCommons applicationCommons;
    OrderService orderService;
    SearchService searchService;


    public ProductController(ProductService productService, ApplicationCommons applicationCommons,OrderService orderService, SearchService searchService) {
         this.productService = productService;
         this.applicationCommons = applicationCommons;
         this.orderService = orderService;
         this.searchService = searchService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponseDto> getProductById(@PathVariable long id,
                                                             @RequestHeader("Authorization") String token) throws ProductNotFoundException {

//        System.out.println("getProductById" + token);
        applicationCommons.validateToken(token);

        ProductResponseDto productResponseDto = new ProductResponseDto();
        Optional<Product> product = productService.getProductById(id);
        if (product.isPresent()) {
            productResponseDto = ProductResponseDto.from(product.get());
        }
        ResponseEntity<ProductResponseDto> responseEntity = new ResponseEntity<>(productResponseDto, HttpStatus.OK);

        return responseEntity;
    }



    @GetMapping("/all")
    public List<ProductResponseDto> getAllProducts() {
        List<Product> products = productService.getAllProducts();
        List<ProductResponseDto> finalProductList = new ArrayList<>();

        for (Product product : products) {
            finalProductList.add(ProductResponseDto.from(product));
        }

        return finalProductList;

    }

    @PostMapping()
    public ProductResponseDto createProduct(@RequestBody CreateProductRequestDto createProductRequestDto) {
        Product createdProduct = productService.createProduct(createProductRequestDto.getName(),createProductRequestDto.getDescription(),createProductRequestDto.getImageUrl(),createProductRequestDto.getPrice(),createProductRequestDto.getCategory());
        return ProductResponseDto.from(createdProduct);
    }


    @PostMapping("/search")
    public Page<ProductResponseDto> search(@RequestBody SearchRequestDto requestDto) {

        Page<Product> productPage = searchService.search(requestDto.getName(), requestDto.getCategoryId() , requestDto.getPageNo(), requestDto.getPageSize(), requestDto.getSortParam());
        List<ProductResponseDto> productResponseDtos = new ArrayList<>();
        List<Product> products = productPage.getContent();

        for(Product product : products){
            ProductResponseDto finalProduct = ProductResponseDto.from(product);
            productResponseDtos.add(finalProduct);
        }

        return new PageImpl<>(productResponseDtos, productPage.getPageable(),productPage.getTotalElements());

    }

    @GetMapping("/categories")
    public List<Category> getAllCategories() {
        return productService.getAllCategory();
    }

    @ExceptionHandler(NullPointerException.class)
    public ErrorDto handleNullPointerExceptions(){
        ErrorDto errorDto = new ErrorDto();
        errorDto.setMessage("Null Pointer Exception");
        errorDto.setStatus("Failure");
        return errorDto;
    }
}
