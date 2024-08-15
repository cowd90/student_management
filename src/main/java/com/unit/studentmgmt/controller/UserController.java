package com.unit.studentmgmt.controller;

import com.unit.studentmgmt.dto.request.UserCreateRequest;
import com.unit.studentmgmt.dto.request.UserUpdateRequest;
import com.unit.studentmgmt.dto.response.ApiResponse;
import com.unit.studentmgmt.dto.response.UserResponse;
import com.unit.studentmgmt.service.UserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
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

    @GetMapping("/search")
    ApiResponse<List<UserResponse>> searchStudentByName(
            @RequestParam String term,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return ApiResponse.<List<UserResponse>>builder()
                .data(userService.searchByName(term, page, size))
                .build();
    }

    @GetMapping("/search-by-date")
    ApiResponse<List<UserResponse>> searchStudentByAdmissionDate(
            @RequestParam LocalDate startDate,
            @RequestParam LocalDate endDate,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return ApiResponse.<List<UserResponse>>builder()
                .data(userService.searchStudentsByAdmissionDateRange(startDate, endDate, page, size))
                .build();
    }

    @GetMapping("/my")
    ApiResponse<UserResponse> getMyInfo() {
        return ApiResponse.<UserResponse>builder()
                .data(userService.getMyInfo())
                .build();
    }

    @GetMapping("/all")
    ApiResponse<List<UserResponse>> getAll() {
        return ApiResponse.<List<UserResponse>>builder()
                .data(userService.getAllUsers())
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
