package com.example.auth.controller;

import com.example.auth.dto.ForgotPasswordRequest;
import com.example.auth.dto.ResetPasswordRequest;
import com.example.auth.service.PasswordResetService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SuppressWarnings("removal")
@SpringBootTest
@AutoConfigureMockMvc
class PasswordResetControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PasswordResetService passwordResetService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void forgotPassword_shouldReturn200() throws Exception {
        ForgotPasswordRequest request = new ForgotPasswordRequest();
        request.setEmail("test@example.com");

        doNothing().when(passwordResetService).sendResetToken(request);

        mockMvc.perform(post("/api/auth/forgot-password")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().string("Email z linkiem do resetu został wysłany (lub symulowany)"));
    }

    @Test
    void resetPassword_shouldReturn200() throws Exception {
        ResetPasswordRequest request = new ResetPasswordRequest();
        request.setToken("abc123");
        request.setNewPassword("newSecret123");

        doNothing().when(passwordResetService).resetPassword(request);

        mockMvc.perform(post("/api/auth/reset-password")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().string("Hasło zostało zmienione"));
    }
}
