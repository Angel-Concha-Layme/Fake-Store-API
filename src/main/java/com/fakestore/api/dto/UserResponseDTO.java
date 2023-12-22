package com.fakestore.api.dto;

import java.time.LocalDate;

public record UserResponseDTO(
        Long id,
        String username,
        String email,
        LocalDate createdAt
) {
}
