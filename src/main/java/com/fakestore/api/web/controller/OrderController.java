package com.fakestore.api.web.controller;

import com.fakestore.api.dto.OrderDTO;
import com.fakestore.api.dto.OrderDetailDTO;
import com.fakestore.api.dto.OrderResponseDTO;
import com.fakestore.api.persistence.entity.*;
import com.fakestore.api.service.OrderService;
import com.fakestore.api.service.ProductService;
import com.fakestore.api.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/orders")
@AllArgsConstructor
public class OrderController {
    private final OrderService orderService;
    private final UserService userService;
    private final ProductService productService;

    @GetMapping("/all")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Page<OrderResponseDTO>> getAllOrders(
            @PageableDefault(page = 0, size =5)Pageable pageable
            ) {
        User user = userService.getAuthenticatedUser();
        Page<Order> orders = orderService.getAllOrdersByUser(user, pageable);

        Page<OrderResponseDTO> response = orders.map(order -> new OrderResponseDTO(
                order.getId(),
                order.getOrderDate().toString(),
                order.getOrderStatus().toString(),
                order.getTotal()
        ));

        return ResponseEntity.ok(response);
    }

    @PostMapping("/create")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<OrderResponseDTO> createOrder(@RequestBody OrderDTO orderDTO) {
        User user = userService.getAuthenticatedUser();

        Order order = new Order();
        order.setUser(user);
        order.setOrderDate(LocalDate.now());
        order.setOrderStatus(OrderStatus.PENDING);
        order.setOrderDetails(new ArrayList<>());

        for (OrderDetailDTO detailDTO : orderDTO.orderDetails()) {
            Product product = productService.getProductById(detailDTO.id());

            OrderDetail detail = new OrderDetail();
            detail.setOrder(order);
            detail.setProduct(product);
            detail.setQuantity(detailDTO.quantity());
            detail.setUnitPrice(product.getPrice());

            order.getOrderDetails().add(detail);
        }

        double total = order.getOrderDetails().stream()
                .mapToDouble(detail -> detail.getUnitPrice() * detail.getQuantity())
                .sum();
        order.setTotal(total);

        Order savedOrder = orderService.saveOrder(order);
        return ResponseEntity.status(HttpStatus.CREATED).body(new OrderResponseDTO(
                savedOrder.getId(),
                savedOrder.getOrderDate().toString(),
                savedOrder.getOrderStatus().toString(),
                savedOrder.getTotal()
        ));
    }

    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<OrderResponseDTO> getOrderById(@PathVariable Long id) {
        User user = userService.getAuthenticatedUser();
        Order order = orderService.getOrderById(id);

        System.out.println("order.getUser().getId(): " + order.getUser().getId());
        System.out.println("user.getId(): " + user.getId());

        if (!Objects.equals(order.getUser().getId(), user.getId())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        return ResponseEntity.ok(new OrderResponseDTO(
                order.getId(),
                order.getOrderDate().toString(),
                order.getOrderStatus().toString(),
                order.getTotal()
        ));
    }

    @PutMapping("/cancel/{id}")
    @PreAuthorize(("isAuthenticated()"))
    public ResponseEntity<OrderResponseDTO> cancelOrder(@PathVariable Long id) {
        User user = userService.getAuthenticatedUser();
        Order order = orderService.getOrderById(id);

        if (!Objects.equals(order.getUser().getId(), user.getId())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        order.setOrderStatus(OrderStatus.CANCELLED);
        Order savedOrder = orderService.saveOrder(order);
        return ResponseEntity.ok(new OrderResponseDTO(
                savedOrder.getId(),
                savedOrder.getOrderDate().toString(),
                savedOrder.getOrderStatus().toString(),
                savedOrder.getTotal()
        ));
    }

    @PutMapping("/retake/{id}")
    @PreAuthorize(("isAuthenticated()"))
    public ResponseEntity<OrderResponseDTO> retakeOrder(@PathVariable Long id) {
        User user = userService.getAuthenticatedUser();
        Order order = orderService.getOrderById(id);

        if (!Objects.equals(order.getUser().getId(), user.getId())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        order.setOrderStatus(OrderStatus.PENDING);
        Order savedOrder = orderService.saveOrder(order);
        return ResponseEntity.ok(new OrderResponseDTO(
                savedOrder.getId(),
                savedOrder.getOrderDate().toString(),
                savedOrder.getOrderStatus().toString(),
                savedOrder.getTotal()
        ));
    }

    @PutMapping("/complete/{id}")
    @PreAuthorize(("isAuthenticated()"))
    public ResponseEntity<OrderResponseDTO> completeOrder(@PathVariable Long id) {
        User user = userService.getAuthenticatedUser();
        Order order = orderService.getOrderById(id);

        if (!Objects.equals(order.getUser().getId(), user.getId())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        order.setOrderStatus(OrderStatus.COMPLETED);
        Order savedOrder = orderService.saveOrder(order);
        return ResponseEntity.ok(new OrderResponseDTO(
                savedOrder.getId(),
                savedOrder.getOrderDate().toString(),
                savedOrder.getOrderStatus().toString(),
                savedOrder.getTotal()
        ));
    }
}
