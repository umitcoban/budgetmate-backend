package com.umitcoban.budgetmatebackend.modules.income.infrastructure.web.dto;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;

public record IncomeResponse(
        Long id,
        BigDecimal amount,
        String currency,
        String source,
        String description,
        LocalDate incomeDate,
        Instant createdAt,
        Instant updatedAt
) {}
