package com.fakestore.api.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UserUpdateDTO(
        @NotNull
        @Size(min = 3, max = 50)
        String username,
        @Email
        @NotNull
        String email
) {
}
