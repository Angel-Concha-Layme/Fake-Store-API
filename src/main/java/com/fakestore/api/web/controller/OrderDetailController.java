package com.fakestore.api.web.controller;

import com.fakestore.api.dto.OrderDetailResponseDTO;
import com.fakestore.api.dto.OrderDetailUpdateDTO;
import com.fakestore.api.persistence.entity.OrderDetail;
import com.fakestore.api.persistence.entity.User;
import com.fakestore.api.service.OrderDetailService;
import com.fakestore.api.service.ProductService;
import com.fakestore.api.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/order-details")
@AllArgsConstructor
public class OrderDetailController {
    private final OrderDetailService orderDetailService;
    private final UserService userService;
    private final ProductService productService;

    // GET /api/order-details
    // GET /api/order-details/all/{id} -> id es el is del Order al que pertenece el OrderDetail
    @GetMapping("/all/{id}") // id es el id del Order al que pertenece el OrderDetail
    public ResponseEntity<List<OrderDetailResponseDTO>> getAllOrderDetails(@PathVariable Long id) {
        List<OrderDetailResponseDTO> orderDetails = orderDetailService.getAllOrderDetailsByOrder(id);
        return new ResponseEntity<>(orderDetails, HttpStatus.OK);
    }

    // POST /api/order-details
    // PUT /api/order-details/{id} // solo el usuario que hizo la orden puede modificarla
    @PutMapping("/{idOrderDetails}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<OrderDetailResponseDTO> updateOrderDetail(@PathVariable Long idOrderDetails, @RequestBody OrderDetailUpdateDTO orderDetailUpdateDTO) {
        User user = userService.getAuthenticatedUser();

        OrderDetail orderDetail = orderDetailService.getOrderDetailById(idOrderDetails);
        User userOrder = orderDetail.getOrder().getUser();

        if (!Objects.equals(user.getId(), userOrder.getId())) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        // verificamos que el producto exista, y que la cantidad sea mayor a 0
        if (orderDetailUpdateDTO.productId() == null || orderDetailUpdateDTO.quantity() == null || orderDetailUpdateDTO.quantity() <= 0) {
            // TODO: mejorar el mensaje de error, para que sea mas especifico (se puede hacer con un DTO, o con un mensaje de error personalizado)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        // verificamos que el producto exista en la base de datos
        if (!productService.existsById(orderDetailUpdateDTO.productId())) {
            // TODO: mejorar el mensaje de error, para que sea mas especifico (se puede hacer con un DTO, o con un mensaje de error personalizado)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        OrderDetailResponseDTO orderDetailDTO = orderDetailService.updateOrderDetail(idOrderDetails, orderDetailUpdateDTO);
        return new ResponseEntity<>(orderDetailDTO, HttpStatus.OK);
    }
    // DELETE /api/order-details/{id} // solo administradores
}
