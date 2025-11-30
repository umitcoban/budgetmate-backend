package com.umitcoban.budgetmatebackend.modules.income.domain.event;

import java.math.BigDecimal;
import java.time.LocalDate;

public record IncomeCreatedEvent(
        Long incomeId,
        Long userId,
        BigDecimal amount,
        String currency,
        String source,
        LocalDate incomeDate
) {}
