package com.fakestore.api.dto;

import com.fakestore.api.persistence.entity.Role;

import java.time.LocalDate;

public record UserResponseDTO(
        Long id,
        String username,
        String email,
        String avatar,
        Role role,
        LocalDate createdAt
) {
}
