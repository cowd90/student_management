package com.unit.student_mgmt.dto.request;

import com.unit.student_mgmt.constant.Gender;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserUpdateRequest {
    LocalDate dob;
    Gender gender;
    MultipartFile photo;
    String classBelongs;
    String password;
}
