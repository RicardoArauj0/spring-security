package com.example.security.jwt;

import lombok.Data;

@Data
public class UsernamePasswordAuthenticationRequest {
    private String username;
    private String password;
}
