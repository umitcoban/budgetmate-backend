package com.umitcoban.budgetmatebackend.modules.auth.domain.port;

import com.umitcoban.budgetmatebackend.modules.auth.domain.model.UserAccount;

import java.util.Optional;

public interface UserAccountRepositoryPort {
	UserAccount save(UserAccount account);
	
	Optional<UserAccount> findByEmail(String email);
	
	boolean existsByEmail(String email);
}
