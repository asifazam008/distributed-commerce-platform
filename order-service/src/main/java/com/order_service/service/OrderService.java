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

//        New Validation: Prevent adding items to a PAID order

        if (order.getStatus() == OrderStatus.PAID) {
            throw new RuntimeException("Cannot add items to a paid order");
        }

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

    // ========================== FIND ONE ORDER ==========================
    public Order findOrder(UUID orderId) {
        return repository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));
    }

    // ========================== FIND ALL ORDERS ==========================
    public List<Order> findAllOrders() {
        return repository.findAll();
    }

    // ========================== DELETE ITEM FROM ORDER ==========================
    public Order deleteItem(UUID orderId, UUID itemId) {

        Order order = repository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        if (order.getStatus() == OrderStatus.PAID) {
            throw new RuntimeException("Cannot delete item from a paid order");
        }

        OrderItem item = order.getItems().stream()
                .filter(i -> i.getId().equals(itemId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Item not found in this order"));

        order.getItems().remove(item);

        BigDecimal newTotal = order.getItems().stream()
                .map(i -> i.getPrice().multiply(BigDecimal.valueOf(i.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        order.setTotalAmount(newTotal);
        order.setUpdatedAt(LocalDateTime.now());

        return repository.save(order);
    }

    public Order updateItem(UUID orderId, UUID itemId, int qty, BigDecimal price) {

        Order order = repository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        if (order.getStatus() == OrderStatus.PAID) {
            throw new RuntimeException("Cannot update item in a paid order");
        }

        OrderItem item = order.getItems().stream()
                .filter(i -> i.getId().equals(itemId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException());

        item.setQuantity(qty);
        item.setPrice(price);

        BigDecimal total = order.getItems().stream()
                .map(i -> i.getPrice().multiply(BigDecimal.valueOf(i.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        order.setTotalAmount(total);
        order.setUpdatedAt(LocalDateTime.now());

        return repository.save(order);
    }

    public Order cancelOrder(UUID orderId) {
        Order order = repository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        if (order.getStatus() == OrderStatus.CANCELLED) ;
        order.setUpdatedAt(LocalDateTime.now());

        return order;
    }

    public Order clearAllItems(UUID orderId) {
        Order order = repository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        order.getItems().clear();
        order.setTotalAmount(BigDecimal.ZERO);
        order.setUpdatedAt(LocalDateTime.now());

        return repository.save(order);
    }


    public List<Order> findByCustomer(String customerId) {
        return repository.findByCustomerId(customerId);
    }

    public List<Order> findByStatus(String status) {

        OrderStatus orderStatus = OrderStatus.valueOf(status.toUpperCase());
        return repository.findByStatus(orderStatus);
    }

    public BigDecimal getTotalAmount(UUID orderId) {

        Order order = repository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        if (order.getStatus() == OrderStatus.CANCELLED) {
            throw new RuntimeException("Cannot get total amount of a cancelled order");
        }
        return order.getTotalAmount();
    }
}
