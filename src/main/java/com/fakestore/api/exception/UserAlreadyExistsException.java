package com.fakestore.api.exception;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class UserAlreadyExistsException extends RuntimeException{
    public UserAlreadyExistsException(String message) {
        super(message);
    }
}
