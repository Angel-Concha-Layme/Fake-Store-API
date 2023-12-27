package com.fakestore.api.service;

import com.fakestore.api.persistence.entity.Order;
import com.fakestore.api.persistence.entity.User;
import com.fakestore.api.persistence.repository.OrderRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;

    public Order saveOrder(Order newOrder) {
        return orderRepository.save(newOrder);
    }

    public Page<Order> getAllOrdersByUser(User user, Pageable pageable) {
        return orderRepository.findAllByUser(user, pageable);
    }

    public Order getOrderById(Long id) {
        return orderRepository.findById(id).orElseThrow();
    }

    public void updateOrderTotalPrice(Long id, Integer quantity) {
        Order order = orderRepository.findById(id).orElseThrow();

        double total = order.getOrderDetails().stream()
                .mapToDouble(detail -> detail.getUnitPrice() * detail.getQuantity())
                .sum();

        order.setTotal(total);

        orderRepository.save(order);
    }
}