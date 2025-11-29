package com.umitcoban.budgetmatebackend.modules.userprofile.infrastructure.jpa;

import com.umitcoban.budgetmatebackend.modules.userprofile.domain.model.UserProfile;
import com.umitcoban.budgetmatebackend.modules.userprofile.domain.port.UserProfileRepositoryPort;
import com.umitcoban.budgetmatebackend.modules.userprofile.infrastructure.mapper.UserProfileMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class UserProfileRepositoryAdapter implements UserProfileRepositoryPort {
	
	private final UserProfileJpaRepository jpaRepository;
	private final UserProfileMapper mapper;
	
	public UserProfileRepositoryAdapter(UserProfileJpaRepository jpaRepository, UserProfileMapper mapper) {
		this.jpaRepository = jpaRepository;
		this.mapper = mapper;
	}
	
	@Override
	public UserProfile save(UserProfile profile) {
		UserProfileEntity entity = mapper.toEntity(profile);
		UserProfileEntity saved = jpaRepository.save(entity);
		return mapper.toDomain(saved);
	}
	
	@Override
	public Optional<UserProfile> findById(Long id) {
		return jpaRepository.findById(id).map(mapper::toDomain);
	}
	
	@Override
	public List<UserProfile> findAll() {
		return jpaRepository.findAll().stream().map(mapper::toDomain).toList();
	}
	
	@Override
	public boolean existsByEmail(String email) {
		return jpaRepository.existsByEmail(email);
	}

    @Override
    public Optional<UserProfile> findByEmail(String email) {
        return jpaRepository.findByEmail(email).map(mapper::toDomain);
    }
}
