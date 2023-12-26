package com.fakestore.api.web.controller;

import com.fakestore.api.dto.OrderDTO;
import com.fakestore.api.dto.OrderDetailDTO;
import com.fakestore.api.dto.OrderResponseDTO;
import com.fakestore.api.persistence.entity.Order;
import com.fakestore.api.persistence.entity.OrderDetail;
import com.fakestore.api.persistence.entity.Product;
import com.fakestore.api.persistence.entity.User;
import com.fakestore.api.service.OrderService;
import com.fakestore.api.service.ProductService;
import com.fakestore.api.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/orders")
@AllArgsConstructor
public class OrderController {
    private final OrderService orderService;
    private final UserService userService;
    private final ProductService productService;

    // ALL CRUD OPERATIONS:
    // only users with JWT can access these endpoint methods

    // GET /api/orders
    @GetMapping("/all")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<OrderResponseDTO>> getAllOrders() {
        User user = userService.getAuthenticatedUser();
        List<Order> orders = orderService.getAllOrdersByUser(user);

        List<OrderResponseDTO> response = new ArrayList<>();
        for (Order order : orders) {
            response.add(new OrderResponseDTO(
                    order.getId(),
                    order.getOrderDate().toString(),
                    order.getOrderStatus().toString(),
                    order.getTotal()
            ));
        }
        return ResponseEntity.ok(response);
    }


    // GET /api/orders/{id}
    // POST /api/orders
    @PostMapping("/create")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<OrderResponseDTO> createOrder(@RequestBody OrderDTO orderDTO) {
        User user = userService.getAuthenticatedUser();

        Order order = new Order();
        order.setUser(user);
        order.setOrderDate(orderDTO.orderDate());
        order.setOrderStatus(orderDTO.orderStatus());
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

    // PUT /api/orders/{id} // solo administradores
    // DELETE /api/orders/{id} // solo administradores
}
