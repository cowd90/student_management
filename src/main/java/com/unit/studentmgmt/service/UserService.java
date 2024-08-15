package com.unit.studentmgmt.service;

import com.unit.studentmgmt.constant.PredefinedRole;
import com.unit.studentmgmt.dto.request.UserCreateRequest;
import com.unit.studentmgmt.dto.request.UserUpdateRequest;
import com.unit.studentmgmt.dto.response.UserResponse;
import com.unit.studentmgmt.entity.Role;
import com.unit.studentmgmt.entity.User;
import com.unit.studentmgmt.exception.AppException;
import com.unit.studentmgmt.exception.ErrorCode;
import com.unit.studentmgmt.mapper.UserMapper;
import com.unit.studentmgmt.repository.RoleRepository;
import com.unit.studentmgmt.repository.UserRepository;
import com.unit.studentmgmt.util.ImageUtils;
import jakarta.persistence.EntityListeners;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.rowset.serial.SerialBlob;
import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
@EntityListeners(AuditingEntityListener.class)
public class UserService {
	UserRepository userRepository;
	RoleRepository roleRepository;
	PasswordEncoder passwordEncoder;
	UserMapper userMapper;

	@Transactional
	@PostAuthorize("hasRole('ADMIN')")
	public UserResponse createUser(UserCreateRequest request) {
		User user = userMapper.toUser(request);
		Blob photoBlob = uploadPhoto(request.getPhoto());
		user.setPhoto(photoBlob);

		user = userRepository.save(user);
		
		// generate student id automatically
		String studentId = generateStudentId(user.getId(), user.getAdmissionDate().getYear());
		
		// generate education email for student
		String fullName = user.getFullName().trim().toLowerCase();
		int index = fullName.lastIndexOf(' ');
		String lastname = fullName.substring(index +1);
		String firstname = fullName.substring(0, fullName.indexOf(' '));
		String studentEmail = String.format("%s%s.%s@yopmail.com", lastname, firstname, studentId);

		var roles = new HashSet<Role>();
		roleRepository.findById(PredefinedRole.STUDENT_ROLE).ifPresent(roles::add);

		String password = request.getDob().format(DateTimeFormatter.ofPattern("ddMMyyyy"));

		user.setPassword(passwordEncoder.encode(password));
		user.setRoles(roles);
		user.setStudentId(studentId);
		user.setEmail(studentEmail);

		user = userRepository.save(user);

		// convert image blob to base64
		String photoBase64 = ImageUtils.convertToBase64(photoBlob);
		UserResponse userResponse = userMapper.toUserResponse(user);
		userResponse.setPhoto(photoBase64);

		return userResponse;
	}

	@PreAuthorize("hasRole('STUDENT') or hasRole('ADMIN')")
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

	public UserResponse getMyInfo() {
		var context = SecurityContextHolder.getContext();
		String name = context.getAuthentication().getName();

		User user = userRepository.findByStudentId(name)
				.orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTS));

		return userMapper.toUserResponse(user);
	}

	public UserResponse findByStudentId(String studentId) {
		User user = userRepository.findByStudentId(studentId)
				.orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTS));

		// convert image blob to base64
		String photoBase64 = ImageUtils.convertToBase64(user.getPhoto());
		UserResponse userResponse = userMapper.toUserResponse(user);
		userResponse.setPhoto(photoBase64);

		return userResponse;
	}

	public List<UserResponse> searchByName(String conditional, int page, int size) {
		Pageable pageable = PageRequest.of(
				page,
				size,
				Sort.by("studentId").ascending()
		);
		var students = userRepository.findAllByFullNameOrStudentIdContainingIgnoreCase(conditional, pageable);

		return students
				.map(userMapper::toUserResponse)
				.stream().toList();
	}

	public List<UserResponse> searchStudentsByAdmissionDateRange(LocalDate start, LocalDate end, int page, int size) {
		Pageable pageable = PageRequest.of(
				page,
				size,
				Sort.by("studentId").ascending()
		);
		var students = userRepository
				.findByAdmissionDateBetween(start, end, pageable);

		return students
				.map(userMapper::toUserResponse)
				.stream().toList();
	}

	@PreAuthorize("hasRole('ADMIN')")
	public List<UserResponse> getAllUsers() {
		return userRepository
				.findAll().stream()
				.map(userMapper::toUserResponse)
				.toList();
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
