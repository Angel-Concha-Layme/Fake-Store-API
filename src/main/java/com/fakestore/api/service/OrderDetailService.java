package com.fakestore.api.service;

import com.fakestore.api.dto.OrderDetailResponseDTO;
import com.fakestore.api.dto.OrderDetailUpdateDTO;
import com.fakestore.api.persistence.entity.OrderDetail;
import com.fakestore.api.persistence.entity.Product;
import com.fakestore.api.persistence.repository.OrderDetailRepository;
import com.fakestore.api.persistence.repository.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class OrderDetailService {
    private final OrderDetailRepository orderDetailRepository;
    private final ProductRepository productRepository;
    private final OrderService orderService;

    public List<OrderDetailResponseDTO> getAllOrderDetailsByOrder(Long id) {
        List<OrderDetail> orderDetails = orderDetailRepository.findAllByOrder_Id(id);
        return orderDetails.stream().map(orderDetail -> new OrderDetailResponseDTO(
                orderDetail.getId(),
                orderDetail.getOrder().getId(),
                orderDetail.getProduct().getId(),
                orderDetail.getQuantity(),
                orderDetail.getUnitPrice()
        )).toList();
    }

    public OrderDetailResponseDTO updateOrderDetail(Long id, OrderDetailUpdateDTO orderDetailUpdateDTO) {
        OrderDetail orderDetail = orderDetailRepository.findById(id).orElseThrow();
        Product product = productRepository.findById(orderDetailUpdateDTO.productId()).orElseThrow();
        orderDetail.setProduct(product);
        orderDetail.setQuantity(orderDetailUpdateDTO.quantity());

        orderDetail.setUnitPrice(product.getPrice());

        orderDetail = orderDetailRepository.save(orderDetail);

        orderService.updateOrderTotalPrice(orderDetail.getOrder().getId(), orderDetail.getQuantity());

        return new OrderDetailResponseDTO(
                orderDetail.getId(),
                orderDetail.getOrder().getId(),
                orderDetail.getProduct().getId(),
                orderDetail.getQuantity(),
                orderDetail.getUnitPrice()
        );
    }

    public OrderDetail getOrderDetailById(Long idOrderDetails) {
        return orderDetailRepository.findById(idOrderDetails).orElseThrow();
    }
}
