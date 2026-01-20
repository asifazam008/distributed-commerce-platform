package com.order_service.controller;

import com.order_service.dto.*;
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

    @PostMapping("/create")
    public Order create(@RequestBody CreateOrderRequest request) {
        return service.createOrder(request.getCustomerId());
    }
    // ================= ADD ITEM =================

    @PostMapping("/{orderId}/items")
    public Order addItem(@PathVariable UUID orderId,
                         @RequestBody AddItemRequest addItemRequest) {
        return service.addItem(
                orderId,
                addItemRequest.getProductId(),
                addItemRequest.getQuantity(),
                addItemRequest.getPrice()
        );
    }

    // ================= PAY ORDER =================

    @PostMapping("/{orderId}/pay")
    public Order pay(@PathVariable UUID orderId) {
        return service.markPaid(orderId);
    }

    // FIND ONE ORDER

    @PostMapping("/find")
    public Order findOrder(@RequestBody IdRequest request) {
        return service.findOrder(request.getId());
    }

    // FIND ALL ORDERS
    @PostMapping("/findAll")
    public List<Order> findAllOrders() {
        return service.findAllOrders();
    }

    // DELETE ITEM FROM ORDER

    @PostMapping("/items/delete")
    public Order deleteItem(@RequestBody DeleteItemRequest request) {

        return service.deleteItem(
                request.getOrderId(),
                request.getItemId()
        );
    }

    // ================= UPDATE ITEM =================

    @PutMapping("/{orderId}/items/{itemId}")
    public Order updateItem(
            @PathVariable UUID orderId,
            @PathVariable UUID itemId,
            @RequestBody AddItemRequest updateItemRequest) {

        return service.updateItem(
                orderId,
                itemId,
                updateItemRequest.getQuantity(),
                updateItemRequest.getPrice()
        );
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

    @PostMapping("/findByCustomer")
    public List<Order> findByCustomer(@RequestBody CustomerRequest request){
        return service.findByCustomer(request.getCustomerId());
    }

    // ================= FIND BY STATUS =================

    @PostMapping("/findByStatus")
    public List<Order> findByStatus(@RequestBody StatusRequest request) {
        return service.findByStatus(request.getStatus());
    }

    // ================= GET TOTAL AMOUNT ONLY =================

    @PostMapping("/total")
    public BigDecimal getTotal(@RequestBody IdRequest request){
        return service.getTotalAmount(request.getId());
    }

    @GetMapping("/health")
    public String health() {
        return "Order Service is UP";
    }
}
