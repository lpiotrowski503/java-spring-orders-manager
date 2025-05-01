package com.example.auth.controller;

import com.example.auth.dto.RegisterRequest;
import com.example.auth.dto.LoginRequest;
import com.example.auth.dto.LoginResponse;
import com.example.auth.dto.UserResponse;
import com.example.auth.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication", description = "Endpoints for register, login, current user")
public class AuthController {

    private final AuthService authService;

    @Operation(
            summary = "Get current user",
            security = @SecurityRequirement(name = "BearerAuth")
    )
    @GetMapping("/me")
    public ResponseEntity<UserResponse> getMe(Authentication authentication) {
        return ResponseEntity.ok(authService.me(authentication));
    }

    @Operation(summary = "Register new user")
    @PostMapping("/register")
    public ResponseEntity<String> register(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                description = "User registration data",
                required = true
            )
            @RequestBody @Valid RegisterRequest request
    ) {

        authService.register(request);
        return ResponseEntity.ok("User registered successfully");
    }

    @Operation(summary = "Login and get JWT token")
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                description = "Login credentials",
                required = true
            )
            @RequestBody LoginRequest request)
    {
        return ResponseEntity.ok(authService.login(request));
    }
}
