package com.order_service.controller;

import com.order_service.entity.Order;
import com.order_service.entity.OrderItem;
import com.order_service.service.OrderService;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService service;

    public OrderController(OrderService service) {
        this.service = service;
    }

    // ================= CREATE ORDER =================

    @PostMapping
    public Order create(@RequestParam String customerId) {
        return service.createOrder(customerId);
    }

    // ================= ADD ITEM =================

    @PostMapping("/{id}/items")
    public Order addItem(@PathVariable UUID id,
                         @RequestParam String productId,
                         @RequestParam int qty,
                         @RequestParam BigDecimal price) {
        return service.addItem(id, productId, qty, price);
    }

    // ================= PAY ORDER =================

    @PostMapping("/{id}/pay")
    public Order pay(@PathVariable UUID id) {
        return service.markPaid(id);
    }

    // ==========================
// FIND ONE ORDER
// ==========================
    @PostMapping("/find/{orderId}")
    public Order findOrder(@PathVariable UUID orderId) {
        return service.findOrder(orderId);
    }

    // ==========================
// FIND ALL ORDERS
// ==========================
    @PostMapping("/findAll")
    public List<Order> findAllOrders() {
        return service.findAllOrders();
    }

    // ==========================
// DELETE ITEM FROM ORDER
// ==========================
    @PostMapping("/{orderId}/items/{itemId}/delete")
    public Order deleteItem(@PathVariable UUID orderId,
                            @PathVariable UUID itemId) {
        return service.deleteItem(orderId, itemId);
    }
}
