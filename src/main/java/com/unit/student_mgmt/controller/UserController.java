package com.unit.student_mgmt.controller;

import com.unit.student_mgmt.dto.request.UserCreateRequest;
import com.unit.student_mgmt.dto.request.UserUpdateRequest;
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

    @PutMapping("/{id}")
    ApiResponse<UserResponse> updateUser(
            @PathVariable("id") Long id,
            @ModelAttribute UserUpdateRequest request
    ) {
        return ApiResponse.<UserResponse>builder()
                .data(userService.updateUser(id, request))
                .build();
    }

    @DeleteMapping("/{id}")
    ApiResponse<String> deleteUser(@PathVariable("id") Long id) {
        userService.deleteUser(id);
        return ApiResponse.<String>builder()
                .message("User has been deleted!")
                .build();
    }

}
