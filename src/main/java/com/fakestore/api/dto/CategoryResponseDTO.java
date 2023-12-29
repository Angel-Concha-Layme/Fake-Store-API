package com.fakestore.api.dto;

public record CategoryResponseDTO(
        Long id,
        String name,
        String image,
        String description
) {
}
