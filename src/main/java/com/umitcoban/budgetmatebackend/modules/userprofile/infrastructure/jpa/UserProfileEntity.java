package com.umitcoban.budgetmatebackend.modules.userprofile.infrastructure.jpa;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;

@Entity
@Table(name = "user_profiles")
@Getter
@Setter
@NoArgsConstructor
public class UserProfileEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false, unique = true)
	private String email;
	
	@Column(nullable = false)
	private String fullName;
	
	@Column(nullable = false)
	@CreationTimestamp
	private Instant createdAt;
	
	@Column(nullable = false)
	@UpdateTimestamp
	private Instant updatedAt;
}
