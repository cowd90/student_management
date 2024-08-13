package com.unit.student_mgmt.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.unit.student_mgmt.constant.Gender;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
    String fullName;

    @JsonProperty("dob")
    LocalDate dob;

    @JsonProperty("gender")
    @Enumerated(EnumType.STRING)
    Gender gender;

    @JsonProperty("photo")
    MultipartFile photo;

    @JsonProperty("admission_date")
    LocalDate admissionDate;

    @JsonProperty("class")
    String classBelongs;
}
