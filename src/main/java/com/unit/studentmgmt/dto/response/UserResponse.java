package com.unit.studentmgmt.dto.response;

import com.unit.studentmgmt.entity.Role;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

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
    LocalDateTime createAt;
    LocalDateTime updateAt;
    Set<Role> roles;
}
