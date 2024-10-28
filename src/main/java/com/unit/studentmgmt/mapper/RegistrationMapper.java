package com.unit.studentmgmt.mapper;

import com.unit.studentmgmt.dto.request.RegistrationRequest;
import com.unit.studentmgmt.dto.response.RegistrationResponse;
import com.unit.studentmgmt.entity.Registration;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RegistrationMapper {
	Registration toRegistration(RegistrationRequest request);

	RegistrationResponse toRegistrationResponse(Registration registration);

}
