package com.unit.studentmgmt.configuration;

import com.unit.studentmgmt.constant.PredefinedRole;
import com.unit.studentmgmt.entity.Role;
import com.unit.studentmgmt.entity.User;
import com.unit.studentmgmt.repository.RoleRepository;
import com.unit.studentmgmt.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;

@Configuration
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class ApplicationInitConfig {

    PasswordEncoder passwordEncoder;

    static final String ADMIN_EMAIL = "admin@yopmail.com";
    static final String ADMIN_PASSWORD = "admin123";

    @Bean
    ApplicationRunner applicationRunner(UserRepository userRepository, RoleRepository roleRepository) {
        return args -> {
          if (!userRepository.existsByEmail(ADMIN_EMAIL)) {
              roleRepository.save(Role.builder()
                              .name(PredefinedRole.STUDENT_ROLE)
                              .description("Student Role")
                      .build());

              Role adminRole = roleRepository.save(Role.builder()
                              .name(PredefinedRole.ADMIN_ROLE)
                              .description("Admin Role")
                      .build());

              var roles = new HashSet<Role>();
              roles.add(adminRole);

              User admin = userRepository.save(User.builder()
                              .email(ADMIN_EMAIL)
                              .password(passwordEncoder.encode(ADMIN_PASSWORD))
                              .roles(roles)
                      .build());

              userRepository.save(admin);
              log.warn("Admin has been created with default " +
                      "email: admin@yopmail.com and password: admin. Please change it");
          }
        };
    }

}
