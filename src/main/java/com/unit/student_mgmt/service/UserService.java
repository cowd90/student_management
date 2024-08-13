package com.unit.student_mgmt.service;

import com.unit.student_mgmt.dto.request.UserCreateRequest;
import com.unit.student_mgmt.dto.response.UserResponse;
import com.unit.student_mgmt.entity.User;
import com.unit.student_mgmt.mapper.UserMapper;
import com.unit.student_mgmt.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class UserService {
	
	UserRepository userRepository;
	UserMapper userMapper;
	
	public UserResponse createUser(UserCreateRequest request) {
		User user = userMapper.toUser(request);
		
		user = userRepository.save(user);
		
		// generate student id automatically
		String studentId = generateStudentId(user.getId(), user.getAdmissionDate().getYear());
		
		// generate education email for student
		String fullName = user.getFullName();
		int index = fullName.lastIndexOf(' ');
		String firstName = fullName.substring(0, index);
		String lastName = fullName.substring(index, 1);
		String studentEmail = String.format("%s%s.%s@yopmail.com", lastName, firstName, studentId);
		
		user.setStudentId(studentId);
		user.setEmail(studentEmail);
		
		return userMapper.toUserResponse(userRepository.save(user));
	}
	
	private String generateStudentId(Long id, int yearOfAdmission) {
		return yearOfAdmission + String.format("%06d", id);
	}
	
}
