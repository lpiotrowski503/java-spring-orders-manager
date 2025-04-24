package com.example.auth.service;

import com.example.auth.dto.RegisterRequest;
import com.example.auth.dto.UserResponse;
import com.example.auth.entity.User;
import com.example.auth.dto.LoginRequest;
import com.example.auth.dto.LoginResponse;
import com.example.auth.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.UserDetails;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authManager;
    private final JwtService jwtService;

    public void register(RegisterRequest request) {
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("Email already registered");
        }

        User user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .build();

        userRepository.save(user);
    }

    public LoginResponse login(LoginRequest request) {
        authManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.email(), request.password())
        );

        UserDetails user = userRepository.findByEmail(request.email()).orElseThrow();
        String token = jwtService.generateToken(user.getUsername());

        System.out.println("User: " + user.getUsername());
        System.out.println("Token: " + token);

        return new LoginResponse(token);
    }

    public UserResponse me(Authentication authentication) {
        var user = (UserDetails) authentication.getPrincipal();

        User userRepo = userRepository.findByEmail(user.getUsername()).orElseThrow();

        return UserResponse.builder()
                .id(userRepo.getId())
                .username(userRepo.getName())
                .email(userRepo.getEmail())
                .build();
    }
}
