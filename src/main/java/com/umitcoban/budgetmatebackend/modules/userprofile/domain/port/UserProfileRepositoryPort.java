package com.umitcoban.budgetmatebackend.modules.userprofile.domain.port;

import com.umitcoban.budgetmatebackend.modules.userprofile.domain.model.UserProfile;

import java.util.List;
import java.util.Optional;

public interface UserProfileRepositoryPort {
	UserProfile save(UserProfile profile);
	
	Optional<UserProfile> findById(Long id);
	
	List<UserProfile> findAll();
	
	boolean existsByEmail(String email);
}
