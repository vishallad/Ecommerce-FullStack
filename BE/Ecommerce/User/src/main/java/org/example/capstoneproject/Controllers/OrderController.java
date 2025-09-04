package org.example.capstoneproject.Controllers;

import org.example.capstoneproject.Services.OrderService;
import org.example.capstoneproject.Services.ProductService;
import org.example.capstoneproject.commons.ApplicationCommons;
import org.example.capstoneproject.dtos.CreateOrderDto;
import org.example.capstoneproject.dtos.CreateOrderSuccessDto;
import org.example.capstoneproject.exceptions.ProductNotFoundException;
import org.example.capstoneproject.models.Order;
import org.example.capstoneproject.models.OrdersProduct;
import org.example.capstoneproject.models.Product;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("api/order")
public class OrderController {

    ProductService productService;
    ApplicationCommons applicationCommons;
    OrderService orderService;

    public OrderController(ProductService productService, ApplicationCommons applicationCommons,OrderService orderService) {
        this.productService = productService;
        this.applicationCommons = applicationCommons;
        this.orderService = orderService;
    }

    @PostMapping("/create-order")
    public ResponseEntity<CreateOrderSuccessDto> createOrder(@RequestBody CreateOrderDto req , @RequestHeader("Authorization") String token) throws ProductNotFoundException {
//        applicationCommons.validateToken(token);

        Optional<Product> product = productService.getProductById(req.getProductId());
        if (product.isEmpty()) {
            throw new ProductNotFoundException("Product not found");
        }
        Order order = orderService.CreateOrder(req.getUserId(),product.get());
        CreateOrderSuccessDto  createOrderSuccessDto = new CreateOrderSuccessDto();
        createOrderSuccessDto.setOrderId(order.getOrderId());
        createOrderSuccessDto.setProductId(order.getProduct().getId());

        return new ResponseEntity<>(createOrderSuccessDto, HttpStatus.OK);
    }

    @PostMapping("/order-success-webhook/{orderId}")
    public ResponseEntity orderSuccess(@PathVariable String orderId , @RequestHeader("Authorization") String token) throws ProductNotFoundException {
//        applicationCommons.validateToken(token);
        try {
            orderService.UpdateOrderSuccess(orderId);
        }catch (ProductNotFoundException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/ordersProduct/{orderId}")
    public ResponseEntity<OrdersProduct> ordersProduct(@PathVariable String orderId ) throws ProductNotFoundException {

        Optional<Order> OrderOpt = orderService.getOrderByOrderId(orderId);
        if (OrderOpt.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }else{
            Order order = OrderOpt.get();
            OrdersProduct orderP = new OrdersProduct();
            orderP.setOrderId(orderId);
            orderP.setAmount(order.getProduct().getPrice());
            orderP.setProductId(order.getProduct().getId());
            orderP.setProductName(order.getProduct().getName());
            return new ResponseEntity<>(orderP, HttpStatus.OK);
        }

    }
}
