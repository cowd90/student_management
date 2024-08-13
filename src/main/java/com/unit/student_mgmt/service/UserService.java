package com.unit.student_mgmt.service;

import com.unit.student_mgmt.constant.PredefinedRole;
import com.unit.student_mgmt.dto.request.UserCreateRequest;
import com.unit.student_mgmt.dto.request.UserUpdateRequest;
import com.unit.student_mgmt.dto.response.UserResponse;
import com.unit.student_mgmt.entity.Role;
import com.unit.student_mgmt.entity.User;
import com.unit.student_mgmt.exception.AppException;
import com.unit.student_mgmt.exception.ErrorCode;
import com.unit.student_mgmt.mapper.UserMapper;
import com.unit.student_mgmt.repository.RoleRepository;
import com.unit.student_mgmt.repository.UserRepository;
import com.unit.student_mgmt.util.ImageUtils;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.rowset.serial.SerialBlob;
import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class UserService {
	UserRepository userRepository;
	RoleRepository roleRepository;
	PasswordEncoder passwordEncoder;
	UserMapper userMapper;

	@Transactional
	@PreAuthorize("hasRole('ADMIN')")
	public UserResponse createUser(UserCreateRequest request) {
		User user = userMapper.toUser(request);
		Blob photoBlob = uploadPhoto(request.getPhoto());
		user.setPhoto(photoBlob);

		var roles = new HashSet<Role>();
		roleRepository.findById(PredefinedRole.USER_ROLE).ifPresent(roles::add);
		user.setRoles(roles);

		String password = request.getDob().format(DateTimeFormatter.ofPattern("ddMMyyyy"));
		user.setPassword(passwordEncoder.encode(password));

		user = userRepository.save(user);
		
		// generate student id automatically
		String studentId = generateStudentId(user.getId(), user.getAdmissionDate().getYear());
		
		// generate education email for student
		String fullName = user.getFullName().trim().toLowerCase();
		int index = fullName.lastIndexOf(' ');
		String lastname = fullName.substring(index +1);
		String firstname = fullName.substring(0, fullName.indexOf(' '));
		String studentEmail = String.format("%s%s.%s@yopmail.com", lastname, firstname, studentId);
		
		user.setStudentId(studentId);
		user.setEmail(studentEmail);

		user = userRepository.save(user);

		// convert image blob to base64
		String photoBase64 = ImageUtils.convertToBase64(photoBlob);
		UserResponse userResponse = userMapper.toUserResponse(user);
		userResponse.setPhoto(photoBase64);

		return userResponse;
	}

	@PreAuthorize("hasRole('USER')")
	public UserResponse updateUser(Long id, UserUpdateRequest request) {
		User user = userRepository.findById(id)
				.orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTS));

		userMapper.updateUser(user, request);
		Blob photoBlob = uploadPhoto(request.getPhoto());
		user.setPhoto(photoBlob);
		user.setPassword(passwordEncoder.encode(request.getPassword()));

		user = userRepository.save(user);

		// convert image blob to base64
		String photoBase64 = ImageUtils.convertToBase64(photoBlob);
		UserResponse userResponse = userMapper.toUserResponse(user);
		userResponse.setPhoto(photoBase64);

		return userResponse;
	}

	@PreAuthorize("hasRole('ADMIN')")
	public void deleteUser(Long id) {
		userRepository.deleteById(id);
	}

	@Transactional
	private Blob uploadPhoto(MultipartFile photo) {
		try {
			ImageUtils.validateFile(photo);
			byte[] photoBytes;
			photoBytes = photo.getBytes();
			return new SerialBlob(photoBytes);
		} catch (IOException | SQLException e) {
			throw new AppException(ErrorCode.UNCATEGORIZED_EXCEPTION);
		}
	}
	
	private String generateStudentId(Long id, int yearOfAdmission) {
		return yearOfAdmission + String.format("%06d", id);
	}
	
}
