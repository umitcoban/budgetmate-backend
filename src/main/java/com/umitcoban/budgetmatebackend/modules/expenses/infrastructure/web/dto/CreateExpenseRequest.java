package com.umitcoban.budgetmatebackend.modules.expenses.infrastructure.web.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDate;

public record CreateExpenseRequest(
        @NotNull @Min(0) BigDecimal amount,
        @NotBlank String currency,
        @NotBlank String category,
        String description,
        @NotNull LocalDate expenseDate
) {}
