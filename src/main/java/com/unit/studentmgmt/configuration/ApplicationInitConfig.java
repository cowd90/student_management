package com.unit.studentmgmt.configuration;

import com.unit.studentmgmt.constant.PredefinedRole;
import com.unit.studentmgmt.entity.Role;
import com.unit.studentmgmt.entity.User;
import com.unit.studentmgmt.repository.RoleRepository;
import com.unit.studentmgmt.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class ApplicationInitConfig {

    private final PasswordEncoder passwordEncoder;

    @Value("${account.username}")
    private String ADMIN_EMAIL;

    @Value("${account.password}")
    private String ADMIN_PASSWORD;

    @Bean
    ApplicationRunner applicationRunner(UserRepository userRepository, RoleRepository roleRepository) {
        return args -> {
          if (!userRepository.existsByEmail(ADMIN_EMAIL)) {
              roleRepository.save(Role.builder()
                              .roleName(PredefinedRole.STUDENT_ROLE)
                              .description("Student Role")
                      .build());

              roleRepository.save(Role.builder()
                      .roleName(PredefinedRole.LECTURER_ROLE)
                      .description("Lecturer Role")
                      .build());

              Role adminRole = roleRepository.save(Role.builder()
                              .roleName(PredefinedRole.ADMIN_ROLE)
                              .description("Admin Role")
                      .build());

              User admin = userRepository.save(User.builder()
                              .username(ADMIN_EMAIL)
                              .email(ADMIN_EMAIL)
                              .password(passwordEncoder.encode(ADMIN_PASSWORD))
                              .role(adminRole)
                      .build());

              userRepository.save(admin);
              log.info("Admin has been created with default " +
                      "email: {} and password: {}. Please change it", ADMIN_EMAIL, ADMIN_PASSWORD);
          }
        };
    }

}
