package com.umitcoban.budgetmatebackend.modules.expenses.infrastructure.web.dto;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;

public record ExpenseResponse(
        Long id,
        BigDecimal amount,
        String currency,
        String category,
        String description,
        LocalDate expenseDate,
        Instant createdAt,
        Instant updatedAt
) {}
