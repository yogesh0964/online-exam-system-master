package com.example.exam.controller;

import com.example.exam.config.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class JwtAuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserDetailsService userDetailsService;

    /**
     * POST /api/auth/login
     * Body: { "username": "test@gmail.com", "password": "pass123" }
     * Returns: { "token": "eyJ...", "role": "ROLE_STUDENT" }
     */
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        try {
            // 1. Username + password verify karo
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.username(), request.password()
                    )
            );
        } catch (BadCredentialsException e) {
            // Wrong password ya username
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "Invalid username or password"));
        }

        // 2. User load karo
        UserDetails userDetails = userDetailsService.loadUserByUsername(request.username());
        String role = userDetails.getAuthorities().iterator().next().getAuthority();

        // 3. JWT token banao
        String token = jwtUtil.generateToken(request.username(), role);

        // 4. Token return karo
        return ResponseEntity.ok(Map.of(
                "token", token,
                "role", role,
                "username", request.username()
        ));
    }

    // Simple DTO — Java 16+ record use kiya
    record LoginRequest(String username, String password) {}
}