package com.fakestore.api.dto;

public record OrderResponseDTO(
        Long id,
        String orderDate,
        String orderStatus,
        Double total
) {
}
