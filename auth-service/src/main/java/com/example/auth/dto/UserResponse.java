package com.example.auth.dto;

import lombok.Builder;

@Builder
public record UserResponse(
        Long id,
        String username,
        String email
) {}
