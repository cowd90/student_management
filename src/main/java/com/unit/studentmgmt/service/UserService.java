package com.unit.studentmgmt.service;

import com.unit.studentmgmt.dto.request.UserRequest;
import com.unit.studentmgmt.dto.response.UserResponse;
import com.unit.studentmgmt.entity.Role;
import com.unit.studentmgmt.entity.User;
import com.unit.studentmgmt.exception.AppException;
import com.unit.studentmgmt.exception.ErrorCode;
import com.unit.studentmgmt.mapper.UserMapper;
import com.unit.studentmgmt.repository.RoleRepository;
import com.unit.studentmgmt.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserMapper userMapper;

    @PreAuthorize("hasRole('ADMIN')")
    public UserResponse createUser(UserRequest userRequest) {
        User user = new User();
        user.setUsername(userRequest.getUsername());
        user.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        user.setFullName(userRequest.getFullName());
        user.setEmail(userRequest.getEmail());

        Role role = roleRepository.findById(userRequest.getRoleId())
            .orElseThrow(() -> new AppException(ErrorCode.NOT_EXISTS));
        user.setRole(role);

        user = userRepository.save(user);

        UserResponse response = new UserResponse();
        response.setUserId(user.getUserId());
        response.setUsername(user.getUsername());
        response.setFullName(user.getFullName());
        response.setEmail(user.getEmail());
        response.setRoleName(role.getRoleName());
        
        return response;
    }

    public UserResponse getMyInfo() {
        var context = SecurityContextHolder.getContext();
        String name = context.getAuthentication().getName();

        User user = userRepository.findById(Long.valueOf(name))
                .orElseThrow(() -> new AppException(ErrorCode.NOT_EXISTS));

        return userMapper.toUserResponse(user);
    }
}
