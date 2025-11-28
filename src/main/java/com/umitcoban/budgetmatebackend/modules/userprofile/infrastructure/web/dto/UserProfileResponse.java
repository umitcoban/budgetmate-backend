package com.umitcoban.budgetmatebackend.modules.userprofile.infrastructure.web.dto;

import java.time.Instant;

public record UserProfileResponse(
		Long id,
		String email,
		String fullName,
		Instant createdAt,
		Instant updatedAt
) {}
