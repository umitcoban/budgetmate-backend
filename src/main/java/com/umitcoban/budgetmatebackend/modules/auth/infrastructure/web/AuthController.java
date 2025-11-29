// modules/auth/infrastructure/web/AuthController.java
package com.umitcoban.budgetmatebackend.modules.auth.infrastructure.web;

import com.nimbusds.jose.JOSEException;
import com.umitcoban.budgetmatebackend.modules.auth.application.AuthService;
import com.umitcoban.budgetmatebackend.modules.auth.infrastructure.web.dto.AuthRequest;
import com.umitcoban.budgetmatebackend.modules.auth.infrastructure.web.dto.AuthResponse;
import com.umitcoban.budgetmatebackend.modules.auth.infrastructure.web.dto.RegisterRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.NonNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Auth", description = "Authentication API")
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService service;

    public AuthController(AuthService service) {
        this.service = service;
    }

    @Operation(summary = "Register new user")
    @PostMapping("/register")
    public ResponseEntity<@NonNull Void> register(@Valid @RequestBody RegisterRequest request) {
        service.register(request.email(), request.password());
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Login and get JWT")
    @PostMapping("/login")
    public ResponseEntity<@NonNull AuthResponse> login(@Valid @RequestBody AuthRequest request) throws JOSEException {
        String token = service.login(request.email(), request.password());
        return ResponseEntity.ok(new AuthResponse(token, "Bearer"));
    }
}
