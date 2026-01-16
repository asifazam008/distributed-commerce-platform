package com.order_service.controller;

import com.order_service.entity.Order;
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

    // FIND ONE ORDER
    @PostMapping("/find/{orderId}")
    public Order findOrder(@PathVariable UUID orderId) {
        return service.findOrder(orderId);
    }

    // FIND ALL ORDERS
    @PostMapping("/findAll")
    public List<Order> findAllOrders() {
        return service.findAllOrders();
    }

    // DELETE ITEM FROM ORDER
    @PostMapping("/{orderId}/items/{itemId}/delete")
    public Order deleteItem(@PathVariable UUID orderId,
                            @PathVariable UUID itemId) {
        return service.deleteItem(orderId, itemId);
    }

    // ================= UPDATE ITEM =================
    @PostMapping("/{orderId}/items/{itemId}/update")
    public Order updateItem(
            @PathVariable UUID orderId,
            @PathVariable UUID itemId,
            @RequestParam int qty,
            @RequestParam BigDecimal price) {

        return service.updateItem(orderId, itemId, qty, price);
    }

    // ================= CANCEL ORDER =================
    @PostMapping("/{orderId}/cancel")
    public Order cancelOrder(@PathVariable UUID orderId) {
        return service.cancelOrder(orderId);
    }

    // ================= CLEAR ALL ITEMS =================
    @PostMapping("/{orderId}/items/clear")
    public Order clearItems(@PathVariable UUID orderId) {
        return service.clearAllItems(orderId);
    }

    // ================= FIND BY CUSTOMER =================
    @PostMapping("/findByCustomer/{customerId}")
    public List<Order> findByCustomer(@PathVariable String customerId) {
        return service.findByCustomer(customerId);
    }

    // ================= FIND BY STATUS =================
    @PostMapping("/findByStatus/{status}")
    public List<Order> findByStatus(@PathVariable String status) {
        return service.findByStatus(status);
    }

    // ================= GET TOTAL AMOUNT ONLY =================
    @PostMapping("/{orderId}/total")
    public BigDecimal getTotal(@PathVariable UUID orderId) {
        return service.getTotalAmount(orderId);
    }
}
