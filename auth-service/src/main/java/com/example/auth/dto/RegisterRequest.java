package com.example.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Schema(description = "Request for user registration")
@Getter
@Setter
public class RegisterRequest {

    @Schema(example = "testuser", description = "Username of the new user")
    @NotBlank
    private String name;

    @Schema(example = "test@example.com", description = "Email of the new user")
    @Email
    @NotBlank
    private String email;

    @Schema(example = "Password123!", description = "Password of the new user")
    @NotBlank
    private String password;
}
