package com.umitcoban.budgetmatebackend.modules.income.infrastructure.jpa;

import com.umitcoban.budgetmatebackend.modules.income.domain.model.Income;
import com.umitcoban.budgetmatebackend.modules.income.domain.port.IncomeRepositoryPort;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Repository
public class IncomeRepositoryAdapter implements IncomeRepositoryPort {

    private final IncomeJpaRepository jpaRepository;

    public IncomeRepositoryAdapter(IncomeJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public Income save(Income income) {
        IncomeEntity e = toEntity(income);
        IncomeEntity saved = jpaRepository.save(e);
        return toDomain(saved);
    }

    @Override
    public List<Income> findByUserId(Long userId) {
        return jpaRepository.findByUserIdOrderByIncomeDateDesc(userId)
                .stream().map(this::toDomain).toList();
    }

    @Override
    public List<Income> findByUserIdAndDateRange(Long userId, LocalDate from, LocalDate to) {
        return jpaRepository
                .findByUserIdAndIncomeDateBetweenOrderByIncomeDateDesc(userId, from, to)
                .stream().map(this::toDomain).toList();
    }

    @Override
    public BigDecimal sumByUserIdAndDateRange(Long userId, LocalDate from, LocalDate to) {
        return jpaRepository.sumByUserIdAndIncomeDateBetween(userId, from, to);
    }

    private IncomeEntity toEntity(Income income) {
        IncomeEntity e = new IncomeEntity();
        e.setId(income.getId());
        e.setUserId(income.getUserId());
        e.setAmount(income.getAmount());
        e.setCurrency(income.getCurrency());
        e.setSource(income.getSource());
        e.setDescription(income.getDescription());
        e.setIncomeDate(income.getIncomeDate());
        e.setCreatedAt(income.getCreatedAt());
        e.setUpdatedAt(income.getUpdatedAt());
        return e;
    }

    private Income toDomain(IncomeEntity e) {
        return new Income(
                e.getId(),
                e.getUserId(),
                e.getAmount(),
                e.getCurrency(),
                e.getSource(),
                e.getDescription(),
                e.getIncomeDate(),
                e.getCreatedAt(),
                e.getUpdatedAt()
        );
    }
}
