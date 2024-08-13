package com.unit.student_mgmt.entity;

import com.unit.student_mgmt.constant.Gender;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.Set;

@Entity(name = "student")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    Long id;

    String studentId;

    String email;

    String password;

    String fullName;

    LocalDate dob;

    @Enumerated
    Gender gender;

    @Lob
    String photo;

    LocalDate admissionDate;

    @ManyToMany
    Set<Role> roles;

    String classBelongs;
}
