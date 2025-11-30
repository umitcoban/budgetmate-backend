package com.umitcoban.budgetmatebackend.modules.income.infrastructure.web;

import com.umitcoban.budgetmatebackend.modules.income.application.IncomeService;
import com.umitcoban.budgetmatebackend.modules.income.domain.model.Income;
import com.umitcoban.budgetmatebackend.modules.income.infrastructure.web.dto.CreateIncomeRequest;
import com.umitcoban.budgetmatebackend.modules.income.infrastructure.web.dto.IncomeResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Tag(name = "Income", description = "User incomes API")
@RestController
@RequestMapping("/api/incomes")
public class IncomeController {

    private final IncomeService service;

    public IncomeController(IncomeService service) {
        this.service = service;
    }

    private Long currentUserId(Authentication authentication) {
        String userIdStr = (String) authentication.getDetails();
        return Long.parseLong(userIdStr);
    }

    @Operation(summary = "Create new income for current user")
    @PostMapping
    public IncomeResponse create(@Valid @RequestBody CreateIncomeRequest request,
                                 Authentication authentication) {
        Long userId = currentUserId(authentication);

        Income created = service.createIncome(
                userId,
                request.amount(),
                request.currency(),
                request.source(),
                request.description(),
                request.incomeDate()
        );

        return toResponse(created);
    }

    @Operation(summary = "List all incomes for current user")
    @GetMapping
    public List<IncomeResponse> list(Authentication authentication) {
        Long userId = currentUserId(authentication);
        return service.getIncomesForUser(userId).stream()
                .map(this::toResponse)
                .toList();
    }

    @Operation(summary = "List incomes for current user in date range")
    @GetMapping("/range")
    public List<IncomeResponse> listInRange(
            Authentication authentication,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to
    ) {
        Long userId = currentUserId(authentication);
        return service.getIncomesForUserInRange(userId, from, to).stream()
                .map(this::toResponse)
                .toList();
    }

    private IncomeResponse toResponse(Income i) {
        return new IncomeResponse(
                i.getId(),
                i.getAmount(),
                i.getCurrency(),
                i.getSource(),
                i.getDescription(),
                i.getIncomeDate(),
                i.getCreatedAt(),
                i.getUpdatedAt()
        );
    }
}
