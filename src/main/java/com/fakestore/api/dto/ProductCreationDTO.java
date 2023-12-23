package com.fakestore.api.dto;

public record ProductCreationDTO(
        String name,
        String description,
        Double price,
        Integer stockQuantity,
        Long categoryId,
        String imageUrl) {
}