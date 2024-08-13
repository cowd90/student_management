package com.unit.student_mgmt.controller;

import com.unit.student_mgmt.dto.request.UserCreateRequest;
import com.unit.student_mgmt.dto.response.ApiResponse;
import com.unit.student_mgmt.dto.response.UserResponse;
import com.unit.student_mgmt.service.UserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserController {
    UserService userService;

    @PostMapping
    ApiResponse<UserResponse> createUser(@ModelAttribute UserCreateRequest request) {
        return ApiResponse.<UserResponse>builder()
                .data(userService.createUser(request))
                .build();
    }



}
