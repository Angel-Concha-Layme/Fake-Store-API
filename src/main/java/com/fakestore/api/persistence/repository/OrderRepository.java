package com.fakestore.api.persistence.repository;

import com.fakestore.api.persistence.entity.Order;
import com.fakestore.api.persistence.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findAllByUser(User user);
}
