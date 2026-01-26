package com.kdu.smartHome.controller;

import com.kdu.smartHome.dto.request.LoginRequest;
import com.kdu.smartHome.dto.request.RegisterRequest;
import com.kdu.smartHome.dto.response.LoginResponse;
import com.kdu.smartHome.entity.User;
import com.kdu.smartHome.exception.ConflictException;
import com.kdu.smartHome.exception.UnauthorizedException;
import com.kdu.smartHome.repository.UserRepository;
import com.kdu.smartHome.service.impl.JwtServiceImpl;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * REST endpoints for authentication.
 */

@RestController
@RequestMapping("/api/v1/auth")
@AllArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtServiceImpl jwtService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(AuthController.class);

    /**
     * Authenticates a user and returns a JWT.
     */
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request) {
        log.info("Login attempt for {}", request.getEmail());
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

        String username = authentication.getName();
        User user = userRepository.findByEmailAndDeletedDateIsNull(username)
                .orElseThrow(() -> new UnauthorizedException("User not found"));
        Long userId = user.getUserId();
        String token = jwtService.generateToken(userId);

        return ResponseEntity.ok(LoginResponse.builder()
                .message("Login successful")
                .token(token)
                .userId(userId)
                .build());
    }

    /**
     * Registers a new user and returns a JWT.
     */
    @PostMapping("/register")
    public ResponseEntity<LoginResponse> register(@Valid @RequestBody RegisterRequest request) {
        if (userRepository.existsByEmailAndDeletedDateIsNull(request.getEmail())) {
            throw new ConflictException("Email already registered");
        }

        User user = User.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .name(request.getName())
                .build();

        User saved = userRepository.save(user);
        String token = jwtService.generateToken(saved.getUserId());

        return ResponseEntity.status(HttpStatus.CREATED).body(LoginResponse.builder()
                .message("Registration successful")
                .token(token)
                .userId(saved.getUserId())
                .build());
    }
}
