package com.umitcoban.budgetmatebackend.modules.auth.domain.event;

public record UserRegisteredEvent(
        Long userId,
        String email
) {}
