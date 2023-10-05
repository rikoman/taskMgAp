package com.example.backend.security.pojo;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;
@Getter
@Setter
public class SignupRequest {
    private String username;
    private Set<String> roles;
    private String password;
    private String email;
}