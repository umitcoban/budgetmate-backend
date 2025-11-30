package com.umitcoban.budgetmatebackend.modules.income.domain.port;

import com.umitcoban.budgetmatebackend.modules.income.domain.model.Income;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface IncomeRepositoryPort {

    Income save(Income income);

    List<Income> findByUserId(Long userId);

    List<Income> findByUserIdAndDateRange(Long userId, LocalDate from, LocalDate to);

    BigDecimal sumByUserIdAndDateRange(Long userId, LocalDate from, LocalDate to);
}
