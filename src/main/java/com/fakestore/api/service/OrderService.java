package com.fakestore.api.service;

import com.fakestore.api.persistence.entity.Order;
import com.fakestore.api.persistence.entity.User;
import com.fakestore.api.persistence.repository.OrderRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;

    public Order saveOrder(Order newOrder) {
        return orderRepository.save(newOrder);
    }

    public List<Order> getAllOrdersByUser(User user) {
        return orderRepository.findAllByUser(user);
    }
}