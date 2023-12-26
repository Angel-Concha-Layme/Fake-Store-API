package com.fakestore.api.dto;

import com.fakestore.api.persistence.entity.OrderStatus;
import com.fakestore.api.persistence.entity.User;

import java.time.LocalDate;
import java.util.List;

public record OrderDTO(
        LocalDate orderDate,
        OrderStatus orderStatus,
        List<OrderDetailDTO> orderDetails
) {
}