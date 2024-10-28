package com.unit.studentmgmt.mapper;

import com.unit.studentmgmt.dto.request.UserRequest;
import com.unit.studentmgmt.dto.response.UserResponse;
import com.unit.studentmgmt.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {
	User toUser(UserRequest request);

	UserResponse toUserResponse(User user);

	void updateUser(@MappingTarget User user, UserRequest request);

}
