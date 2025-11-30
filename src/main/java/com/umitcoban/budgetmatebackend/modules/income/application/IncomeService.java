package com.umitcoban.budgetmatebackend.modules.income.application;

import com.umitcoban.budgetmatebackend.modules.income.domain.event.IncomeCreatedEvent;
import com.umitcoban.budgetmatebackend.modules.income.domain.model.Income;
import com.umitcoban.budgetmatebackend.modules.income.domain.port.IncomeRepositoryPort;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
public class IncomeService {

    private final IncomeRepositoryPort repository;
    private final ApplicationEventPublisher eventPublisher;

    public IncomeService(IncomeRepositoryPort repository,
                         ApplicationEventPublisher eventPublisher) {
        this.repository = repository;
        this.eventPublisher = eventPublisher;
    }

    public Income createIncome(Long userId,
                               BigDecimal amount,
                               String currency,
                               String source,
                               String description,
                               LocalDate incomeDate) {

        Income income = Income.createNew(userId, amount, currency, source, description, incomeDate);
        Income saved = repository.save(income);

        eventPublisher.publishEvent(new IncomeCreatedEvent(
                saved.getId(),
                saved.getUserId(),
                saved.getAmount(),
                saved.getCurrency(),
                saved.getSource(),
                saved.getIncomeDate()
        ));

        return saved;
    }

    @Transactional(readOnly = true)
    public List<Income> getIncomesForUser(Long userId) {
        return repository.findByUserId(userId);
    }

    @Transactional(readOnly = true)
    public List<Income> getIncomesForUserInRange(Long userId, LocalDate from, LocalDate to) {
        return repository.findByUserIdAndDateRange(userId, from, to);
    }

    @Transactional(readOnly = true)
    public java.math.BigDecimal sumIncomesForUserInRange(Long userId, LocalDate from, LocalDate to) {
        return repository.sumByUserIdAndDateRange(userId, from, to);
    }
}
