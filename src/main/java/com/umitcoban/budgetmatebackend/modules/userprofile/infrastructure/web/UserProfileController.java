package com.umitcoban.budgetmatebackend.modules.userprofile.infrastructure.web;

import com.umitcoban.budgetmatebackend.modules.userprofile.application.UserProfileService;
import com.umitcoban.budgetmatebackend.modules.userprofile.domain.model.UserProfile;
import com.umitcoban.budgetmatebackend.modules.userprofile.infrastructure.web.dto.UserProfileRequest;
import com.umitcoban.budgetmatebackend.modules.userprofile.infrastructure.web.dto.UserProfileResponse;
import jakarta.validation.Valid;
import lombok.NonNull;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/user-profiles")
public class UserProfileController {
	private final UserProfileService service;
	
	public UserProfileController(UserProfileService service) {
		this.service = service;
	}
	
	
	@PostMapping
	public ResponseEntity<@NonNull UserProfileResponse> create(@Valid @RequestBody UserProfileRequest request) {
		UserProfile created = service.createProfile(request.email(), request.fullName());
		UserProfileResponse response = toResponse(created);
		return ResponseEntity
				.created(URI.create("/api/user-profiles/" + response.id()))
				.body(response);
	}

    @GetMapping("/me")
    public UserProfileResponse me(Authentication authentication) {
        String email = authentication.getName();
        UserProfile profile = service.getByEmail(email);
        return toResponse(profile);
    }
	
	@GetMapping("/{id}")
	public UserProfileResponse getById(@PathVariable Long id) {
		return toResponse(service.getById(id));
	}
	
	@GetMapping
	public List<UserProfileResponse> list() {
		return service.getAll().stream().map(this::toResponse).toList();
	}
	
	private UserProfileResponse toResponse(UserProfile profile) {
		return new UserProfileResponse(
				profile.getId(),
				profile.getEmail(),
				profile.getFullName(),
				profile.getCreatedAt(),
				profile.getUpdatedAt()
		);
	}
}
