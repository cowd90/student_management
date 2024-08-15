package com.unit.studentmgmt.entity;

import com.unit.studentmgmt.constant.Gender;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.sql.Blob;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

@Entity(name = "users")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@EntityListeners(AuditingEntityListener.class)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String studentId;

    String email;

    String fullName;

    String password;

    LocalDate dob;

    @Enumerated(EnumType.STRING)
    Gender gender;

    @Lob
    Blob photo;

    @Column(updatable = false)
    LocalDate admissionDate;

    String classBelongs;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    LocalDateTime createAt;

    @LastModifiedDate
    @Column(insertable = false)
    LocalDateTime updateAt;

    @ManyToMany(fetch = FetchType.LAZY)
    Set<Role> roles;

}
