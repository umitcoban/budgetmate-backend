package com.umitcoban.budgetmatebackend.modules.auth.infrastructure.jpa;

import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserAccountJpaRepository extends JpaRepository<@NonNull UserAccountEntity, @NonNull Long> {

    Optional<UserAccountEntity> findByEmail(String email);

    boolean existsByEmail(String email);
}