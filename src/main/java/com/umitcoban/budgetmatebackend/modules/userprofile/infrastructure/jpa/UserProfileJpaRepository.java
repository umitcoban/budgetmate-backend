package com.umitcoban.budgetmatebackend.modules.userprofile.infrastructure.jpa;

import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserProfileJpaRepository extends JpaRepository<@NonNull UserProfileEntity, @NonNull Long> {
	boolean existsByEmail(String email);
    Optional<UserProfileEntity> findByEmail(String email);
}
