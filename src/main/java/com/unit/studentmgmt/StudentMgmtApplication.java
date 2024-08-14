package com.unit.studentmgmt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class StudentMgmtApplication {

    public static void main(String[] args) {
        SpringApplication.run(StudentMgmtApplication.class, args);
    }

}
