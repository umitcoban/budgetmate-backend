package com.umitcoban.budgetmatebackend.modules.income.domain.model;

import lombok.Getter;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;

@Getter
public class Income {

    private final Long id;
    private final Long userId;
    private BigDecimal amount;
    private String currency;
    private String source;
    private String description;
    private LocalDate incomeDate;
    private final Instant createdAt;
    private Instant updatedAt;

    public Income(Long id,
                  Long userId,
                  BigDecimal amount,
                  String currency,
                  String source,
                  String description,
                  LocalDate incomeDate,
                  Instant createdAt,
                  Instant updatedAt) {
        this.id = id;
        this.userId = userId;
        this.amount = amount;
        this.currency = currency;
        this.source = source;
        this.description = description;
        this.incomeDate = incomeDate;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static Income createNew(Long userId,
                                   BigDecimal amount,
                                   String currency,
                                   String source,
                                   String description,
                                   LocalDate incomeDate) {
        Instant now = Instant.now();
        return new Income(null, userId, amount, currency, source, description, incomeDate, now, now);
    }

    public void update(BigDecimal amount,
                       String currency,
                       String source,
                       String description,
                       LocalDate incomeDate) {
        this.amount = amount;
        this.currency = currency;
        this.source = source;
        this.description = description;
        this.incomeDate = incomeDate;
        this.updatedAt = Instant.now();
    }
}
