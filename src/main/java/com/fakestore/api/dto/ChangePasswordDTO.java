package com.fakestore.api.dto;

public record ChangePasswordDTO(
        String currentPassword,
        String newPassword,
        String confirmNewPassword
) {
}
