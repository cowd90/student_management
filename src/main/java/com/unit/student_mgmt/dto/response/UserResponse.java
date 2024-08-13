package com.unit.student_mgmt.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserResponse {
    String id;
    String studentId;
    String email;
    String password;
    String fullName;
    LocalDate dob;
    String gender;
    String photo;
    LocalDate admissionDate;
    String classBelongs;
}
