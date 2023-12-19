package com.example.backend.api.component;

import com.example.backend.security.service.UserDetailsImpl;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
public class CustomUserPrincipal {
    public UserDetailsImpl getUserDetails(Authentication authentication){
        return (UserDetailsImpl) authentication.getPrincipal();
    }
}