package com.umitcoban.budgetmatebackend.modules.auth.application.jpa;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Entity
@Table(name = "user_accounts")
@Getter
@Setter
@NoArgsConstructor
public class UserAccountEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false, unique = true, length = 255)
	private String email;
	
	@Column(nullable = false, length = 255)
	private String passwordHash;
	
	@Column(nullable = false, length = 255)
	private String roles; // "ROLE_USER,ROLE_ADMIN" gibi CSV
	
	@Column(nullable = false)
	private Instant createdAt;
}