package com.order_service.controller;

import com.order_service.entity.Order;
import com.order_service.service.OrderService;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.UUID;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService service;

    public OrderController(OrderService service) {
        this.service = service;
    }

    @PostMapping
    public Order create(@RequestParam String customerId) {
        return service.createOrder(customerId);
    }

    @PostMapping("/{id}/items")
    public Order addItem(@PathVariable UUID id,
                         @RequestParam String productId,
                         @RequestParam int qty,
                         @RequestParam BigDecimal price) {
        return service.addItem(id, productId, qty, price);
    }

    @PostMapping("/{id}/pay")
    public Order pay(@PathVariable UUID id) {
        return service.markPaid(id);
    }
}