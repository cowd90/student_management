package com.unit.student_mgmt.configuration;

import com.unit.student_mgmt.constant.PredefinedRole;
import com.unit.student_mgmt.entity.Role;
import com.unit.student_mgmt.entity.User;
import com.unit.student_mgmt.repository.RoleRepository;
import com.unit.student_mgmt.repository.UserRepository;
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
    static final String ADMIN_PASSWORD = "admin";

    @Bean
    ApplicationRunner applicationRunner(UserRepository userRepository, RoleRepository roleRepository) {
        return args -> {
          if (!userRepository.existsByEmail(ADMIN_EMAIL)) {
              roleRepository.save(Role.builder()
                              .name(PredefinedRole.USER_ROLE)
                              .description("User Role")
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
