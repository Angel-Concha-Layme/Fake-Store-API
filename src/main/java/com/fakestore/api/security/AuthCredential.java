package com.fakestore.api.security;

import lombok.Data;

@Data
public class AuthCredential {
    private String email;
    private String password;
}
