package com.fakestore.api.dto;

public record OrderDetailUpdateDTO(
        Long orderId,
        Long productId,
        Integer quantity
) {
}
