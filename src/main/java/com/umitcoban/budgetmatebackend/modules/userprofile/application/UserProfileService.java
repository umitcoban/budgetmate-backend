package com.umitcoban.budgetmatebackend.modules.userprofile.application;

import com.umitcoban.budgetmatebackend.modules.userprofile.domain.model.UserProfile;
import com.umitcoban.budgetmatebackend.modules.userprofile.domain.port.UserProfileRepositoryPort;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class UserProfileService {
	
	private final UserProfileRepositoryPort repository;
	
	public UserProfileService(UserProfileRepositoryPort repository) {
		this.repository = repository;
	}
	
	public UserProfile createProfile(String email, String fullName) {
		if (repository.existsByEmail(email)) {
			throw new IllegalArgumentException("Email already in use");
		}
		UserProfile profile = UserProfile.of(email, fullName);
		return repository.save(profile);
	}

    @Transactional()
    public UserProfile getByEmail(String email) {
        return repository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("UserProfile not found for email " + email));
    }
	
	@Transactional()
	public UserProfile getById(Long id) {
		return repository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("UserProfile not found"));
	}
	
	@Transactional()
	public List<UserProfile> getAll() {
		return repository.findAll();
	}
}
