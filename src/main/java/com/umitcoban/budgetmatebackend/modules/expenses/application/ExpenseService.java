package com.umitcoban.budgetmatebackend.modules.expenses.application;

import com.umitcoban.budgetmatebackend.modules.expenses.domain.event.ExpenseCreatedEvent;
import com.umitcoban.budgetmatebackend.modules.expenses.domain.model.Expense;
import com.umitcoban.budgetmatebackend.modules.expenses.domain.port.ExpenseRepositoryPort;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
public class ExpenseService {

    private final ExpenseRepositoryPort repository;
    private final ApplicationEventPublisher eventPublisher;

    public ExpenseService(ExpenseRepositoryPort repository,
                          ApplicationEventPublisher eventPublisher) {
        this.repository = repository;
        this.eventPublisher = eventPublisher;
    }

    public Expense createExpense(Long userId,
                                 BigDecimal amount,
                                 String currency,
                                 String category,
                                 String description,
                                 LocalDate expenseDate) {

        Expense expense = Expense.createNew(userId, amount, currency, category, description, expenseDate);
        Expense saved = repository.save(expense);

        eventPublisher.publishEvent(new ExpenseCreatedEvent(
                saved.getId(),
                saved.getUserId(),
                saved.getAmount(),
                saved.getCurrency(),
                saved.getCategory(),
                saved.getExpenseDate()
        ));

        return saved;
    }

    @Transactional(readOnly = true)
    public List<Expense> getExpensesForUser(Long userId) {
        return repository.findByUserId(userId);
    }

    @Transactional(readOnly = true)
    public List<Expense> getExpensesForUserInRange(Long userId, LocalDate from, LocalDate to) {
        return repository.findByUserIdAndDateRange(userId, from, to);
    }

}
