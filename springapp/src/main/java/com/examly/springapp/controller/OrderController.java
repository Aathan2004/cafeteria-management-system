package com.examly.springapp.controller;

import com.examly.springapp.model.Order;
import com.examly.springapp.repository.OrderRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderRepository orderRepository;

    public OrderController(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @PostMapping("/add")
    public ResponseEntity<Order> addOrder(@RequestBody Map<String, Object> orderData) {
        Order order = new Order();
        order.setUsername((String) orderData.get("username"));
        order.setItemId(Long.valueOf(orderData.get("itemId").toString()));
        order.setItemName((String) orderData.get("itemName"));
        order.setPrice(Float.valueOf(orderData.get("price").toString()));
        order.setQuantity((Integer) orderData.get("quantity"));
        order.setStatus("Pending");
        order.setOrderTime(LocalDateTime.now());
        
        return ResponseEntity.ok(orderRepository.save(order));
    }

    @GetMapping("/user/{username}")
    public ResponseEntity<List<Order>> getUserOrders(@PathVariable String username) {
        return ResponseEntity.ok(orderRepository.findByUsername(username));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removeOrder(@PathVariable Long id) {
        orderRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}