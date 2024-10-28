package com.unit.studentmgmt.repository;

import com.unit.studentmgmt.entity.CourseSection;
import com.unit.studentmgmt.entity.Registration;
import com.unit.studentmgmt.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RegistrationRepository extends JpaRepository<Registration, Long> {
    Registration findByStudentAndCourseSection(User student, CourseSection courseSection);

    List<Registration> findByCourseSection(CourseSection courseSection);

    boolean existsByStudentAndCourseSection(User student, CourseSection courseSection);
}
