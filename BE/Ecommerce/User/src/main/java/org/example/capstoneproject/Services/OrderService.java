package org.example.capstoneproject.Services;

import org.example.capstoneproject.Repositories.OrderRepository;
import org.example.capstoneproject.exceptions.ProductNotFoundException;
import org.example.capstoneproject.models.Order;
import org.example.capstoneproject.models.OrderStatus;
import org.example.capstoneproject.models.Product;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class OrderService {
    private OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public Order CreateOrder(long userId, Product product) {
        Order order = new Order();
        order.setOrderId(UUID.randomUUID().toString());
        order.setProduct(product);
        order.setStatus(OrderStatus.INITIATED);
        return orderRepository.save(order);
    }

    public Order UpdateOrderSuccess(String orderId) throws ProductNotFoundException {
        Optional<Order> order = orderRepository.getOrderByOrderId(orderId);
        if (order.isEmpty()) {
            throw new ProductNotFoundException("Order not found");
        }
        Order newOrder = new Order();
        Order orderResponse = order.get();

        newOrder.setProduct(orderResponse.getProduct());
        newOrder.setStatus(orderResponse.getStatus());
        newOrder.setOrderId(orderResponse.getOrderId());
        newOrder.setStatus(OrderStatus.SUCCEEDED);

        return orderRepository.save(newOrder);

    }

    public Optional<Order> getOrderByOrderId(String orderId) {
        Optional<Order> order = orderRepository.getOrderByOrderId(orderId);
        return order;
    }
}
