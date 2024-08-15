package com.unit.studentmgmt.mapper;

import com.unit.studentmgmt.dto.request.UserCreateRequest;
import com.unit.studentmgmt.dto.request.UserUpdateRequest;
import com.unit.studentmgmt.dto.response.UserResponse;
import com.unit.studentmgmt.entity.User;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {
	@Mapping(target = "photo", ignore = true)
	User toUser(UserCreateRequest request);

	@Mapping(target = "photo", ignore = true)
	UserResponse toUserResponse(User user);

	@Mapping(target = "photo", ignore = true)
	void updateUser(@MappingTarget User user, UserUpdateRequest request);

}
