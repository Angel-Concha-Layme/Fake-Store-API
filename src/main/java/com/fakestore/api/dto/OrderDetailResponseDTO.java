package com.fakestore.api.dto;

public record OrderDetailResponseDTO(
        Long id,
        Long orderId,
        Long productId,
        Integer quantity,
        Double unitPrice
) {
}
