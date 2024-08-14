package com.unit.studentmgmt.controller;

import com.unit.studentmgmt.dto.request.UserCreateRequest;
import com.unit.studentmgmt.dto.request.UserUpdateRequest;
import com.unit.studentmgmt.dto.response.ApiResponse;
import com.unit.studentmgmt.dto.response.UserResponse;
import com.unit.studentmgmt.service.UserService;
import jakarta.validation.Valid;
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

    @GetMapping("/{id}")
    ApiResponse<UserResponse> getUserById(@PathVariable("id") String studentId) {
        return ApiResponse.<UserResponse>builder()
                .data(userService.findByStudentId(studentId))
                .build();
    }

    @GetMapping("/my")
    ApiResponse<UserResponse> getMyInfo() {
        return ApiResponse.<UserResponse>builder()
                .data(userService.getMyInfo())
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
