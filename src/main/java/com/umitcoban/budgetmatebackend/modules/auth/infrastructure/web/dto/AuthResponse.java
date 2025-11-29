package com.umitcoban.budgetmatebackend.modules.auth.infrastructure.web.dto;

public record AuthResponse(
        String accessToken,
        String tokenType
) {}