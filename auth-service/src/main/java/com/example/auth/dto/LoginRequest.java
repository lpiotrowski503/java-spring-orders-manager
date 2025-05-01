package com.example.auth.dto;

//public record LoginRequest(String email, String password) {}

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Schema(description = "Request for user login")
@Getter
@Setter
public class LoginRequest {

    @Schema(example = "test@example.com", description = "Email used to login")
    @Email
    @NotBlank
    private String email;

    @Schema(example = "Password123!", description = "Password used to login")
    @NotBlank
    private String password;
}