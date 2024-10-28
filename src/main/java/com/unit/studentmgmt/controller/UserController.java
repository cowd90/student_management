package com.unit.studentmgmt.controller;

import com.unit.studentmgmt.dto.request.UserRequest;
import com.unit.studentmgmt.dto.response.BaseRes;
import com.unit.studentmgmt.dto.response.UserResponse;
import com.unit.studentmgmt.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public BaseRes<UserResponse> registerUser(@RequestBody UserRequest request) {
        return BaseRes.<UserResponse>builder()
                .data(userService.createUser(request))
                .build();
    }

    @GetMapping("/my-info")
    public BaseRes<UserResponse> getMyInfo() {
        return BaseRes.<UserResponse>builder()
                .data(userService.getMyInfo())
                .build();
    }
}
