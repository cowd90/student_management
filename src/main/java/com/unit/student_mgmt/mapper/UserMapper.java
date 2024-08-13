package com.unit.student_mgmt.mapper;

import com.unit.student_mgmt.dto.request.UserCreateRequest;
import com.unit.student_mgmt.dto.response.UserResponse;
import com.unit.student_mgmt.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {
	@Mapping(target = "photo", ignore = true)
	User toUser(UserCreateRequest request);

	UserResponse toUserResponse(User user);
}
