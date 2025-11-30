package com.umitcoban.budgetmatebackend.modules.rulesengine.application;

import com.umitcoban.budgetmatebackend.modules.rulesengine.domain.model.BudgetRuleContext;
import lombok.extern.slf4j.Slf4j;
import org.jeasy.rules.annotation.Action;
import org.jeasy.rules.annotation.Condition;
import org.jeasy.rules.annotation.Fact;
import org.jeasy.rules.annotation.Rule;

import java.math.BigDecimal;

@Rule(name = "Budget usage >= 80%", description = "Warn when monthly expenses exceed 80% of income")
@Slf4j
public class BudgetUsageRule {

    private static final BigDecimal THRESHOLD = new BigDecimal("0.80");

    @Condition
    public boolean when(@Fact("context") BudgetRuleContext context) {
        if (context.getTotalIncome().compareTo(BigDecimal.ZERO) <= 0) {
            return false;
        }
        return context.getUsageRate().compareTo(THRESHOLD) >= 0;
    }

    @Action
    public void then(@Fact("context") BudgetRuleContext context) {
        log.info(
                "[RULE_ALERT] userId={} period={} totalIncome={} totalExpenses={} usageRate={}",
                context.getUserId(),
                context.getPeriod(),
                context.getTotalIncome(),
                context.getTotalExpenses(),
                context.getUsageRate()
                        .multiply(new BigDecimal("100"))
                        .doubleValue()
        );
    }
}
