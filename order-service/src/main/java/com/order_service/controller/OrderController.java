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

//    @PostMapping
//    public Order create(@RequestParam String customerId) {
//        return service.createOrder(customerId);
//    }

    @PostMapping("/create")
    public Order create(@RequestBody CreateOrderRequest request) {
        return service.createOrder(request.getCustomerId());
    }
    // ================= ADD ITEM =================

//    @PostMapping("/{id}/items")
//    public Order addItem(@PathVariable UUID id,
//                         @RequestParam String productId,
//                         @RequestParam int qty,
//                         @RequestParam BigDecimal price) {
//        return service.addItem(id, productId, qty, price);
//    }

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

//    @PostMapping("/{id}/pay")
//    public Order pay(@PathVariable UUID id) {
//        return service.markPaid(id);
//    }

    @PostMapping("/{orderId}/pay")
    public Order pay(@PathVariable UUID orderId) {
        return service.markPaid(orderId);
    }

    // FIND ONE ORDER
//    @PostMapping("/find/{orderId}")
//    public Order findOrder(@PathVariable UUID orderId) {
//        return service.findOrder(orderId);
//    }

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
//    @PostMapping("/{orderId}/items/{itemId}/delete")
//    public Order deleteItem(@PathVariable UUID orderId,
//                            @PathVariable UUID itemId) {
//        return service.deleteItem(orderId, itemId);
//    }

    @PostMapping("/items/delete")
    public Order deleteItem(@RequestBody DeleteItemRequest request) {

        return service.deleteItem(
                request.getOrderId(),
                request.getItemId()
        );
    }

    // ================= UPDATE ITEM =================
//    @PostMapping("/{orderId}/items/{itemId}/update")
//    public Order updateItem(
//            @PathVariable UUID orderId,
//            @PathVariable UUID itemId,
//            @RequestParam int qty,
//            @RequestParam BigDecimal price) {
//
//        return service.updateItem(orderId, itemId, qty, price);
//    }

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
//    @PostMapping("/findByCustomer/{customerId}")
//    public List<Order> findByCustomer(@PathVariable String customerId) {
//        return service.findByCustomer(customerId);
//    }
    @PostMapping("/findByCustomer")
    public List<Order> findByCustomer(@RequestBody CustomerRequest request){
        return service.findByCustomer(request.getCustomerId());
    }

    // ================= FIND BY STATUS =================
//    @PostMapping("/findByStatus/{status}")
//    public List<Order> findByStatus(@PathVariable String status) {
//        return service.findByStatus(status);
//    }

    @PostMapping("/findByStatus")
    public List<Order> findByStatus(@RequestBody StatusRequest request) {
        return service.findByStatus(request.getStatus());
    }

    // ================= GET TOTAL AMOUNT ONLY =================
//    @PostMapping("/{orderId}/total")
//    public BigDecimal getTotal(@PathVariable UUID orderId) {
//        return service.getTotalAmount(orderId);
//    }

    @PostMapping("/total")
    public BigDecimal getTotal(@RequestBody IdRequest request){
        return service.getTotalAmount(request.getId());
    }
}
