package com.unit.studentmgmt.repository;

import com.unit.studentmgmt.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	boolean existsByEmail(String email);
	Optional<User> findByEmail(String email);
	Optional<User> findByStudentId(String studentId);
	Page<User> findByFullNameContainingIgnoreCase(String name, Pageable pageable);
}
