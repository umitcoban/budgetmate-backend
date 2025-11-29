package com.umitcoban.budgetmatebackend.modules.auth.application;

import com.nimbusds.jose.JOSEException;
import com.umitcoban.budgetmatebackend.modules.auth.domain.event.UserRegisteredEvent;
import com.umitcoban.budgetmatebackend.modules.auth.domain.model.UserAccount;
import com.umitcoban.budgetmatebackend.modules.auth.domain.port.UserAccountRepositoryPort;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
public class AuthService {

    private final UserAccountRepositoryPort repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenService jwtTokenService;
    private final ApplicationEventPublisher eventPublisher;

    public AuthService(UserAccountRepositoryPort repository, PasswordEncoder passwordEncoder, JwtTokenService jwtTokenService, ApplicationEventPublisher eventPublisher) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenService = jwtTokenService;
        this.eventPublisher = eventPublisher;
    }


    @Transactional
    public void register(String email, String rawPassword) {
        if (repository.existsByEmail(email)) {
            throw new IllegalArgumentException("Email already registered");
        }
        String hash = passwordEncoder.encode(rawPassword);
        UserAccount account = UserAccount.createNew(email, hash, Set.of("ROLE_USER"));
        UserAccount saved = repository.save(account);
        eventPublisher.publishEvent(new UserRegisteredEvent(saved.getId(), saved.getEmail()));
    }

    public String login(String email, String rawPassword) throws JOSEException {
        UserAccount account = repository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Invalid credentials"));

        if (!passwordEncoder.matches(rawPassword, account.getPasswordHash())) {
            throw new IllegalArgumentException("Invalid credentials");
        }

        return jwtTokenService.generateToken(
                account.getId(),
                account.getEmail(),
                account.getRoles()
        );
    }
}
