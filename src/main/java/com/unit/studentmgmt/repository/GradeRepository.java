package com.unit.studentmgmt.repository;

import com.unit.studentmgmt.entity.CourseSection;
import com.unit.studentmgmt.entity.Grade;
import com.unit.studentmgmt.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GradeRepository extends JpaRepository<Grade, Long> {
    Grade findByStudentAndCourseSection(User student, CourseSection courseSection);
    List<Grade> findByCourseSection(CourseSection courseSection);

    @Query("SELECT g FROM Grade g WHERE g.student.userId = :studentId")
    List<Grade> findByStudentId(@Param("studentId") Long studentId);
}
