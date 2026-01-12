package com.order_service.service;

import com.order_service.entity.*;
import com.order_service.repository.OrderItemRepository;
import com.order_service.repository.OrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class OrderService {

    private final OrderRepository repository;
    private final OrderItemRepository itemRepository;

    public OrderService(OrderRepository repository, OrderItemRepository itemRepository) {
        this.repository = repository;
        this.itemRepository = itemRepository;
    }

    // ========================== FIND ALL ITEMS ==========================

    public List<OrderItem> getAllItems(UUID orderId) {
        Order order = repository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        return order.getItems();
    }

    // ========================== CREATE ORDER ==========================

    public Order createOrder(String customerId) {
        Order order = new Order();
        order.setCustomerId(customerId);
        order.setStatus(OrderStatus.CREATED);
        order.setCreatedAt(LocalDateTime.now());
        order.setTotalAmount(BigDecimal.ZERO);
        return repository.save(order);
    }

    // ========================== ADD ITEM ==========================

    public Order addItem(UUID orderId, String productId, int qty, BigDecimal price) {
        Order order = repository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        OrderItem item = new OrderItem();
        item.setProductId(productId);
        item.setQuantity(qty);
        item.setPrice(price);
        item.setOrder(order);

        order.getItems().add(item);

        BigDecimal total = order.getTotalAmount()
                .add(price.multiply(BigDecimal.valueOf(qty)));

        order.setTotalAmount(total);
        order.setUpdatedAt(LocalDateTime.now());

        return repository.save(order);
    }

    // ========================== MARK PAID ==========================

    public Order markPaid(UUID orderId) {
        Order order = repository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        order.setStatus(OrderStatus.PAID);
        order.setUpdatedAt(LocalDateTime.now());
        return repository.save(order);
    }

    // ==========================
// FIND ONE ORDER
// ==========================
    public Order findOrder(UUID orderId) {
        return repository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));
    }

    // ==========================
// FIND ALL ORDERS
// ==========================
    public List<Order> findAllOrders() {
        return repository.findAll();
    }

    // ==========================
// DELETE ITEM FROM ORDER
// ==========================
    public Order deleteItem(UUID orderId, UUID itemId) {
        Order order = repository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        OrderItem item = itemRepository.findById(itemId)
                .orElseThrow(() -> new RuntimeException("Item not found"));

        order.getItems().remove(item);
        itemRepository.delete(item);

        BigDecimal newTotal = order.getItems().stream()
                .map(i -> i.getPrice().multiply(BigDecimal.valueOf(i.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        order.setTotalAmount(newTotal);
        order.setUpdatedAt(LocalDateTime.now());

        return repository.save(order);
    }
}
