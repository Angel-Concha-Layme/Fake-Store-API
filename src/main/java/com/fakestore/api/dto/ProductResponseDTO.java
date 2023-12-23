package com.fakestore.api.dto;

import java.time.LocalDate;

public record ProductResponseDTO(
        Long id,
        String name,
        String description,
        Double price,
        Integer stockQuantity,
        String categoryName,
        String imageUrl,
        LocalDate createdAt,
        LocalDate updatedAt
) {
}