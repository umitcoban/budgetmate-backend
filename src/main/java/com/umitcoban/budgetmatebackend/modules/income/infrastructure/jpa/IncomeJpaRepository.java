package com.umitcoban.budgetmatebackend.modules.income.infrastructure.jpa;

import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface IncomeJpaRepository extends JpaRepository<@NonNull IncomeEntity, @NonNull Long> {

    List<IncomeEntity> findByUserIdOrderByIncomeDateDesc(Long userId);

    List<IncomeEntity> findByUserIdAndIncomeDateBetweenOrderByIncomeDateDesc(
            Long userId, LocalDate from, LocalDate to
    );

    @Query("""
        SELECT COALESCE(SUM(i.amount), 0)
        FROM IncomeEntity i
        WHERE i.userId = :userId
          AND i.incomeDate BETWEEN :from AND :to
        """)
    BigDecimal sumByUserIdAndIncomeDateBetween(Long userId, LocalDate from, LocalDate to);
}
