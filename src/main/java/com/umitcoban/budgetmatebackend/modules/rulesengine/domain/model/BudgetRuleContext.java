package com.umitcoban.budgetmatebackend.modules.rulesengine.domain.model;

import lombok.Getter;

import java.math.BigDecimal;
import java.time.YearMonth;

@Getter
public class BudgetRuleContext {

    private final Long userId;
    private final YearMonth period;
    private final BigDecimal totalIncome;
    private final BigDecimal totalExpenses;

    public BudgetRuleContext(Long userId,
                             YearMonth period,
                             BigDecimal totalIncome,
                             BigDecimal totalExpenses) {
        this.userId = userId;
        this.period = period;
        this.totalIncome = totalIncome;
        this.totalExpenses = totalExpenses;
    }

    public BigDecimal getUsageRate() {
        if (totalIncome.compareTo(BigDecimal.ZERO) == 0) {
            return BigDecimal.ZERO;
        }
        return totalExpenses
                .divide(totalIncome, 4, java.math.RoundingMode.HALF_UP);
    }
}
