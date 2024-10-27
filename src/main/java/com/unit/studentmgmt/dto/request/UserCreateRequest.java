package com.unit.studentmgmt.dto.request;

import com.unit.studentmgmt.constant.Gender;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserCreateRequest {
    @NotBlank(message = "User must have a name")
    @Max(value = 200, message = "The length of full name is less than 200 characters")
    String fullName;

    @NotBlank(message = "The date of birth is required")
    LocalDate dob;

    @Enumerated(EnumType.STRING)
    @NotBlank(message = "Gender is required")
    Gender gender;

    @NotBlank(message = "Photo is required")
    MultipartFile photo;

    @NotBlank(message = "Admission date is required")
    LocalDate admissionDate;

    @NotBlank(message = "Student must have a class")
    String classBelongs;
}
