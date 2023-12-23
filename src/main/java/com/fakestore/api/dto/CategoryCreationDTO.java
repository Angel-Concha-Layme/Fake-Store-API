package com.fakestore.api.dto;

import jakarta.validation.constraints.NotNull;

public record CategoryCreationDTO(
        @NotNull
        String name,
        @NotNull
        String description
) {
}