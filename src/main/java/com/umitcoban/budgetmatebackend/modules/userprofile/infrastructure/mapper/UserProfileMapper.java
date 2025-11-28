package com.umitcoban.budgetmatebackend.modules.userprofile.infrastructure.mapper;

import com.umitcoban.budgetmatebackend.modules.userprofile.domain.model.UserProfile;
import com.umitcoban.budgetmatebackend.modules.userprofile.infrastructure.jpa.UserProfileEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserProfileMapper {
	UserProfileEntity toEntity(UserProfile domain);
	
	@Mapping(target = "id", source = "id")
	UserProfile toDomain(UserProfileEntity entity);
}
