package com.umitcoban.budgetmatebackend.modules.expenses.domain.event;

import java.math.BigDecimal;
import java.time.LocalDate;

public record ExpenseCreatedEvent(
        Long expenseId,
        Long userId,
        BigDecimal amount,
        String currency,
        String category,
        LocalDate expenseDate
) {}
