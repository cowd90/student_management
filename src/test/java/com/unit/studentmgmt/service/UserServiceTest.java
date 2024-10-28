package com.unit.studentmgmt.service;

import com.unit.studentmgmt.constant.Gender;
import com.unit.studentmgmt.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;

import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@SpringBootTest
@TestPropertySource("/test.properties")
class UserServiceTest {
    @Autowired
    private UserService userService;

    @MockBean
    private UserRepository userRepository;

    private UserCreateRequest request;
    private UserResponse response;
    private User user;

    @BeforeEach
    void initData() {
        request = UserCreateRequest.builder()
                .fullName("Nguyen Van A")
                .gender(Gender.FEMALE)
                .dob(LocalDate.of(2003, 1, 9))
                .admissionDate(LocalDate.of(2021, 9, 21))
                .classBelongs("SE001")
                .build();

        response = UserResponse.builder()
                .id("3")
                .fullName("Nguyen Van A")
                .dob(LocalDate.of(2003, 1, 9))
                .admissionDate(LocalDate.of(2021, 9, 21))
                .studentId("2021000003")
                .email("avan.2021000003@yopmail.com")
                .gender("MALE")
                .classBelongs("SE001")
                .build();

        user = User.builder()
                .id(3L)
                .fullName("Nguyen Van A")
                .dob(LocalDate.of(2003, 1, 9))
                .admissionDate(LocalDate.of(2021, 9, 21))
                .studentId("2021000003")
                .email("avan.2021000003@yopmail.com")
                .gender(Gender.MALE)
                .classBelongs("SE001")
                .build();
    }

    @Test
    void createUser_validRequest_success() {
        when(userRepository.existsByEmail(anyString())).thenReturn(false);
        when(userRepository.save(any())).thenReturn(user);

        response = userService.createUser(request);

        Assertions.assertThat(response.getEmail()).isEqualTo("avan.2021000003@yopmail.com");

    }

}
