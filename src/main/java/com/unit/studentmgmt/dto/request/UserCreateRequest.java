package com.unit.studentmgmt.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.unit.studentmgmt.constant.Gender;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
    @JsonProperty("full_name")
    @NotBlank(message = "User must have a name")
    String fullName;

    @JsonProperty("dob")
    @NotBlank(message = "The date of birth is required")
    LocalDate dob;

    @JsonProperty("gender")
    @Enumerated(EnumType.STRING)
    @NotBlank(message = "Gender is required")
    Gender gender;

    @JsonProperty("photo")
    @NotBlank(message = "Photo is required")
    MultipartFile photo;

    @JsonProperty("admission_date")
    @NotBlank(message = "Admission date is required")
    LocalDate admissionDate;

    @JsonProperty("class_belongs")
    @NotBlank(message = "Student must have a class")
    String classBelongs;
}
