package com.example.auth.controller;

import com.example.auth.dto.LoginRequest;
import com.example.auth.dto.LoginResponse;
import com.example.auth.dto.RegisterRequest;
import com.example.auth.dto.UserResponse;
import com.example.auth.service.AuthService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SuppressWarnings("removal")
@SpringBootTest
@AutoConfigureMockMvc
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthService authService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldRegisterSuccessfully() throws Exception {
        RegisterRequest request = new RegisterRequest();
        request.setEmail("user@example.com");
        request.setPassword("password");
        request.setName("user");

        UserResponse response = new UserResponse(1L, "user", "user@example.com");

//        Mockito.when(authService.register(Mockito.any())).thenReturn(response);

        doNothing().when(authService).register(request);

        mockMvc.perform(post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().string("User registered successfully"));
//                .andExpect(jsonPath("$.id", is(1)))
//                .andExpect(jsonPath("$.username", is("user")))
//                .andExpect(jsonPath("$.email", is("user@example.com")));
    }

    @Test
    void shouldLoginSuccessfully() throws Exception {
        LoginRequest request = new LoginRequest();
        request.setEmail("user@example.com");
        request.setPassword("password");

        String jwt = "mocked.jwt.token";

        LoginResponse response = new LoginResponse(jwt);

        Mockito.when(authService.login(Mockito.any())).thenReturn(response);

        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token", is(jwt)));
//                .andExpect(content().string(jwt));
    }

    @Test
    @WithMockUser(username = "user@example.com", roles = "USER")
    void shouldReturnCurrentUser() throws Exception {
        UserResponse response = new UserResponse(1L, "user", "user@example.com");

        Mockito.when(authService.me(Mockito.any())).thenReturn(response);

        mockMvc.perform(get("/api/auth/me"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("user")))
                .andExpect(jsonPath("$.email", is("user@example.com")));
    }
}

