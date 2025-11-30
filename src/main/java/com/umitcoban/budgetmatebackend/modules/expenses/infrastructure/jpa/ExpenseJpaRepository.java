package com.umitcoban.budgetmatebackend.modules.expenses.infrastructure.jpa;

import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ExpenseJpaRepository extends JpaRepository<@NonNull ExpenseEntity, @NonNull Long> {

    Optional<ExpenseEntity> findByIdAndUserId(Long id, Long userId);

    List<ExpenseEntity> findByUserIdOrderByExpenseDateDesc(Long userId);

    List<ExpenseEntity> findByUserIdAndExpenseDateBetweenOrderByExpenseDateDesc(
            Long userId,
            LocalDate from,
            LocalDate to
    );

    @Query("""
    SELECT COALESCE(SUM(e.amount), 0)
    FROM ExpenseEntity e
    WHERE e.userId = :userId
      AND e.expenseDate BETWEEN :from AND :to
    """)
    BigDecimal sumByUserIdAndExpenseDateBetween(Long userId, LocalDate from, LocalDate to);
}
