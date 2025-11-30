package com.umitcoban.budgetmatebackend.modules.expenses.domain.model;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;

public class Expense {

    private final Long id;
    private final Long userId;
    private BigDecimal amount;
    private String currency;     // "TRY", "USD" vs.
    private String category;     // "FOOD", "RENT", "TRANSPORT" vs.
    private String description;
    private LocalDate expenseDate;
    private final Instant createdAt;
    private Instant updatedAt;

    public Expense(Long id,
                   Long userId,
                   BigDecimal amount,
                   String currency,
                   String category,
                   String description,
                   LocalDate expenseDate,
                   Instant createdAt,
                   Instant updatedAt) {
        this.id = id;
        this.userId = userId;
        this.amount = amount;
        this.currency = currency;
        this.category = category;
        this.description = description;
        this.expenseDate = expenseDate;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static Expense createNew(Long userId,
                                    BigDecimal amount,
                                    String currency,
                                    String category,
                                    String description,
                                    LocalDate expenseDate) {
        Instant now = Instant.now();
        return new Expense(
                null,
                userId,
                amount,
                currency,
                category,
                description,
                expenseDate,
                now,
                now
        );
    }

    // Getters
    public Long getId() {
        return id;
    }

    public Long getUserId() {
        return userId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public String getCurrency() {
        return currency;
    }

    public String getCategory() {
        return category;
    }

    public String getDescription() {
        return description;
    }

    public LocalDate getExpenseDate() {
        return expenseDate;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public void update(String category,
                       String description,
                       BigDecimal amount,
                       String currency,
                       LocalDate expenseDate) {
        this.category = category;
        this.description = description;
        this.amount = amount;
        this.currency = currency;
        this.expenseDate = expenseDate;
        this.updatedAt = Instant.now();
    }
}
