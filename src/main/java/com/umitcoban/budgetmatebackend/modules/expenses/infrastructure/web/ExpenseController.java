package com.umitcoban.budgetmatebackend.modules.expenses.infrastructure.web;

import com.umitcoban.budgetmatebackend.modules.expenses.application.ExpenseService;
import com.umitcoban.budgetmatebackend.modules.expenses.domain.model.Expense;
import com.umitcoban.budgetmatebackend.modules.expenses.infrastructure.web.dto.CreateExpenseRequest;
import com.umitcoban.budgetmatebackend.modules.expenses.infrastructure.web.dto.ExpenseResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Tag(name = "Expenses", description = "User expenses API")
@RestController
@RequestMapping("/api/expenses")
public class ExpenseController {

    private final ExpenseService service;

    public ExpenseController(ExpenseService service) {
        this.service = service;
    }

    private Long currentUserId(Authentication authentication) {
        String userIdStr = (String) authentication.getDetails();
        return Long.parseLong(userIdStr);
    }

    @Operation(summary = "Create new expense for current user")
    @PostMapping
    public ExpenseResponse create(@Valid @RequestBody CreateExpenseRequest request,
                                  Authentication authentication) {

        Long userId = currentUserId(authentication);

        Expense created = service.createExpense(
                userId,
                request.amount(),
                request.currency(),
                request.category(),
                request.description(),
                request.expenseDate()
        );

        return toResponse(created);
    }

    @Operation(summary = "List all expenses for current user")
    @GetMapping
    public List<ExpenseResponse> list(Authentication authentication) {
        Long userId = currentUserId(authentication);
        return service.getExpensesForUser(userId).stream()
                .map(this::toResponse)
                .toList();
    }

    @Operation(summary = "List expenses for current user in a date range")
    @GetMapping("/range")
    public List<ExpenseResponse> listInRange(
            Authentication authentication,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to
    ) {
        Long userId = currentUserId(authentication);
        return service.getExpensesForUserInRange(userId, from, to).stream()
                .map(this::toResponse)
                .toList();
    }

    private ExpenseResponse toResponse(Expense e) {
        return new ExpenseResponse(
                e.getId(),
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
