package com.unit.studentmgmt.dto.request;

import com.unit.studentmgmt.constant.Gender;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
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

    @NotBlank(message = "The date of birth is required")
    LocalDate dob;

    @NotBlank
    @NotBlank(message = "Gender is required")
    Gender gender;

    @NotBlank(message = "Photo is required")
    MultipartFile photo;

    @NotBlank
    @NotBlank(message = "Student must have a class")
    String classBelongs;

    @Size(min = 8, message = "INVALID_PASSWORD")
    String password;
}
