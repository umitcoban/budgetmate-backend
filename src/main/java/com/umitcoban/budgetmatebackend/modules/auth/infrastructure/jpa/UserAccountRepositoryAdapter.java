package com.umitcoban.budgetmatebackend.modules.auth.infrastructure.jpa;

import com.umitcoban.budgetmatebackend.modules.auth.domain.model.UserAccount;
import com.umitcoban.budgetmatebackend.modules.auth.domain.port.UserAccountRepositoryPort;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Repository
public class UserAccountRepositoryAdapter implements UserAccountRepositoryPort {

    private final UserAccountJpaRepository jpaRepository;

    public UserAccountRepositoryAdapter(UserAccountJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public UserAccount save(UserAccount account) {
        UserAccountEntity entity = new UserAccountEntity();
        entity.setId(account.getId());
        entity.setEmail(account.getEmail());
        entity.setPasswordHash(account.getPasswordHash());
        entity.setRoles(String.join(",", account.getRoles()));
        entity.setCreatedAt(account.getCreatedAt());

        UserAccountEntity saved = jpaRepository.save(entity);

        return new UserAccount(
                saved.getId(),
                saved.getEmail(),
                saved.getPasswordHash(),
                parseRoles(saved.getRoles()),
                saved.getCreatedAt()
        );
    }

    @Override
    public Optional<UserAccount> findByEmail(String email) {
        return jpaRepository.findByEmail(email)
                .map(e -> new UserAccount(
                        e.getId(),
                        e.getEmail(),
                        e.getPasswordHash(),
                        parseRoles(e.getRoles()),
                        e.getCreatedAt()
                ));
    }

    @Override
    public boolean existsByEmail(String email) {
        return jpaRepository.existsByEmail(email);
    }

    private Set<String> parseRoles(String csv) {
        if (csv == null || csv.isBlank()) return Set.of();
        return Arrays.stream(csv.split(","))
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .collect(Collectors.toSet());
    }
}
