package com.fakestore.api.dto;

public record OrderDetailUpdateDTO(
        Long productId,
        Integer quantity
) {
}
