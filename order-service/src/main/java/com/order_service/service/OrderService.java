package com.order_service.service;

import com.order_service.entity.*;
import com.order_service.repository.OrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
@Transactional
public class OrderService {

    private final OrderRepository repository;

    public OrderService(OrderRepository repository) {
        this.repository = repository;
    }

    // ==========================
    // CREATE ORDER
    // ==========================
    public Order createOrder(String customerId) {

        Order order = new Order();
//        order.setId(UUID.randomUUID().toString());
        order.setCustomerId(customerId);
        order.setStatus(OrderStatus.CREATED);
        order.setCreatedAt(LocalDateTime.now());
        order.setTotalAmount(BigDecimal.ZERO);

        return repository.save(order);
    }

    // ==========================
    // ADD ITEM
    // ==========================
    public Order addItem(UUID orderId, String productId, int qty, BigDecimal price) {

        Order order = repository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        OrderItem item = new OrderItem();
        // ❌ remove: item.setId(UUID.randomUUID());

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

    // ==========================
    // MARK PAID
    // ==========================
    public Order markPaid(UUID orderId) {

        Order order = repository.findById(orderId).orElseThrow(() -> new RuntimeException("Order not found"));

        order.setStatus(OrderStatus.PAID);
        order.setUpdatedAt(LocalDateTime.now());

        return repository.save(order);
    }
}
