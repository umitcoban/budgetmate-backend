package com.umitcoban.budgetmatebackend.modules.auth.domain.model;

import lombok.Getter;

import java.time.Instant;
import java.util.Set;

@Getter
public class UserAccount {
	Long id;
	String email;
	String passwordHash;
	private final Set<String> roles;
	private final Instant createdAt;
	
	public UserAccount(Long id, String email, String passwordHash, Set<String> roles, Instant createdAt) {
		this.id = id;
		this.email = email;
		this.passwordHash = passwordHash;
		this.roles = roles;
		this.createdAt = createdAt;
	}
	
	public static UserAccount createNew(String email, String passwordHash, Set<String> roles) {
		return new UserAccount(null, email, passwordHash, roles, Instant.now());
	}
}
