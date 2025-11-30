// modules/expenses/domain/port/ExpenseRepositoryPort.java
package com.umitcoban.budgetmatebackend.modules.expenses.domain.port;

import com.umitcoban.budgetmatebackend.modules.expenses.domain.model.Expense;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ExpenseRepositoryPort {

    Expense save(Expense expense);

    Optional<Expense> findByIdAndUserId(Long id, Long userId);

    List<Expense> findByUserId(Long userId);

    List<Expense> findByUserIdAndDateRange(Long userId, LocalDate from, LocalDate to);

    BigDecimal sumByUserIdAndDateRange(Long userId, LocalDate from, LocalDate to);
}
