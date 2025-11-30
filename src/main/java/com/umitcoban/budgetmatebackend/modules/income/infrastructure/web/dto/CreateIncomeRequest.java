package com.umitcoban.budgetmatebackend.modules.income.infrastructure.web.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDate;

public record CreateIncomeRequest(
        @NotNull @Min(0) BigDecimal amount,
        @NotBlank String currency,
        @NotBlank String source,
        String description,
        @NotNull LocalDate incomeDate
) {}
