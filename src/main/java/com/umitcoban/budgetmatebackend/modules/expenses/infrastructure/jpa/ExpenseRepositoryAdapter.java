package com.umitcoban.budgetmatebackend.modules.expenses.infrastructure.jpa;

import com.umitcoban.budgetmatebackend.modules.expenses.domain.model.Expense;
import com.umitcoban.budgetmatebackend.modules.expenses.domain.port.ExpenseRepositoryPort;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public class ExpenseRepositoryAdapter implements ExpenseRepositoryPort {

    private final ExpenseJpaRepository jpaRepository;

    public ExpenseRepositoryAdapter(ExpenseJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public Expense save(Expense expense) {
        ExpenseEntity entity = toEntity(expense);
        ExpenseEntity saved = jpaRepository.save(entity);
        return toDomain(saved);
    }

    @Override
    public Optional<Expense> findByIdAndUserId(Long id, Long userId) {
        return jpaRepository.findByIdAndUserId(id, userId).map(this::toDomain);
    }

    @Override
    public List<Expense> findByUserId(Long userId) {
        return jpaRepository.findByUserIdOrderByExpenseDateDesc(userId)
                .stream()
                .map(this::toDomain)
                .toList();
    }

    @Override
    public List<Expense> findByUserIdAndDateRange(Long userId, LocalDate from, LocalDate to) {
        return jpaRepository
                .findByUserIdAndExpenseDateBetweenOrderByExpenseDateDesc(userId, from, to)
                .stream()
                .map(this::toDomain)
                .toList();
    }

    @Override
    public BigDecimal sumByUserIdAndDateRange(Long userId, LocalDate from, LocalDate to) {
        return jpaRepository.sumByUserIdAndExpenseDateBetween(userId, from, to);
    }

    private ExpenseEntity toEntity(Expense expense) {
        ExpenseEntity e = new ExpenseEntity();
        e.setId(expense.getId());
        e.setUserId(expense.getUserId());
        e.setAmount(expense.getAmount());
        e.setCurrency(expense.getCurrency());
        e.setCategory(expense.getCategory());
        e.setDescription(expense.getDescription());
        e.setExpenseDate(expense.getExpenseDate());
        e.setCreatedAt(expense.getCreatedAt());
        e.setUpdatedAt(expense.getUpdatedAt());
        return e;
    }

    private Expense toDomain(ExpenseEntity e) {
        return new Expense(
                e.getId(),
                e.getUserId(),
                e.getAmount(),
                e.getCurrency(),
                e.getCategory(),
                e.getDescription(),
                e.getExpenseDate(),
                e.getCreatedAt(),
                e.getUpdatedAt()
        );
    }
}
