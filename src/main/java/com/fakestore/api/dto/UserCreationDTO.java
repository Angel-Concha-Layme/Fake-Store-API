package com.fakestore.api.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record UserCreationDTO(
        @NotNull
        @Size(min = 3, max = 50)
        String username,
        @NotNull
        @Size(min = 8)
        String password,
        @Email
        @NotNull
        String email
) {
}
