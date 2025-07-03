package com.trindadeisencoes.crm.controller;

import com.trindadeisencoes.crm.model.User;
import com.trindadeisencoes.crm.repository.UserRepository;
import com.trindadeisencoes.crm.security.JwtUtil;
import com.trindadeisencoes.crm.security.LoginRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@SecurityRequirement(name = "Bearer Authentication")
@RestController
@RequestMapping("/auth")
public class LoginController {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    public LoginController(UserRepository userRepository, JwtUtil jwtUtil, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        User user = userRepository.findByEmail(loginRequest.getEmail()).orElse(null);

        if (user != null && passwordEncoder.matches(loginRequest.getSenha(), user.getSenha())) {
            String token = jwtUtil.gerarToken(user);
            return ResponseEntity.ok(
                java.util.Map.of(
                    "token", token,
                    "role", user.getRole().name(),
                    "email", user.getEmail()
                )
            );
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciais inválidas");
    }
}