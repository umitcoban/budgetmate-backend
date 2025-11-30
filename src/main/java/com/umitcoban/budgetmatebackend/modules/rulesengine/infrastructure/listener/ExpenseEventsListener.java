package com.umitcoban.budgetmatebackend.modules.rulesengine.infrastructure.listener;

import com.umitcoban.budgetmatebackend.modules.expenses.domain.event.ExpenseCreatedEvent;
import com.umitcoban.budgetmatebackend.modules.expenses.domain.port.ExpenseRepositoryPort;
import com.umitcoban.budgetmatebackend.modules.income.domain.port.IncomeRepositoryPort;
import com.umitcoban.budgetmatebackend.modules.rulesengine.application.BudgetRuleEngineService;
import com.umitcoban.budgetmatebackend.modules.rulesengine.domain.model.BudgetRuleContext;
import org.springframework.modulith.events.ApplicationModuleListener;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.YearMonth;

@Component
public class ExpenseEventsListener {

    private final IncomeRepositoryPort incomeRepository;
    private final ExpenseRepositoryPort expenseRepository;
    private final BudgetRuleEngineService ruleEngine;

    public ExpenseEventsListener(IncomeRepositoryPort incomeRepository,
                                 ExpenseRepositoryPort expenseRepository,
                                 BudgetRuleEngineService ruleEngine) {
        this.incomeRepository = incomeRepository;
        this.expenseRepository = expenseRepository;
        this.ruleEngine = ruleEngine;
    }

    @ApplicationModuleListener
    public void on(ExpenseCreatedEvent event) {
        Long userId = event.userId();
        LocalDate date = event.expenseDate();
        YearMonth ym = YearMonth.from(date);

        LocalDate from = ym.atDay(1);
        LocalDate to = ym.atEndOfMonth();

        BigDecimal totalIncome = incomeRepository.sumByUserIdAndDateRange(userId, from, to);
        BigDecimal totalExpenses = expenseRepository.sumByUserIdAndDateRange(userId, from, to);

        BudgetRuleContext context = new BudgetRuleContext(
                userId,
                ym,
                totalIncome,
                totalExpenses
        );

        ruleEngine.evaluate(context);
    }
}
