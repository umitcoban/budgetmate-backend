package com.umitcoban.budgetmatebackend.modules.userprofile.domain.model;

import lombok.Getter;

import java.time.Instant;

@Getter
public class UserProfile {
	
	private final Long id;
	private final String email;
	private String fullName;
	private final Instant createdAt;
	private Instant updatedAt;
	
	public UserProfile(Long id, String email, String fullName, Instant createdAt, Instant updatedAt) {
		this.id = id;
		this.email = email;
		this.fullName = fullName;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}
	
	public static UserProfile of(String email, String fullName) {
		return new UserProfile(null, email, fullName, Instant.now(), Instant.now());
	}
	
	public void rename(String newFullName) {
		this.fullName = newFullName;
		this.updatedAt = Instant.now();
	}
}
