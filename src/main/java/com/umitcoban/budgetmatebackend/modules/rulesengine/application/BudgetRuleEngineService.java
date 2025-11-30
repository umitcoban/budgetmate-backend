package com.umitcoban.budgetmatebackend.modules.rulesengine.application;

import com.umitcoban.budgetmatebackend.modules.rulesengine.domain.model.BudgetRuleContext;
import org.jeasy.rules.api.Facts;
import org.jeasy.rules.api.Rules;
import org.jeasy.rules.api.RulesEngine;
import org.jeasy.rules.core.DefaultRulesEngine;
import org.springframework.stereotype.Service;

@Service
public class BudgetRuleEngineService {

    private final RulesEngine engine;
    private final Rules rules;

    public BudgetRuleEngineService() {
        this.engine = new DefaultRulesEngine();
        this.rules = new Rules();
        this.rules.register(new BudgetUsageRule());
    }

    public void evaluate(BudgetRuleContext context) {
        Facts facts = new Facts();
        facts.put("context", context);
        engine.fire(rules, facts);
    }
}
