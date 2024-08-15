package com.unit.studentmgmt.repository;

import com.unit.studentmgmt.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	boolean existsByEmail(String email);
	Optional<User> findByEmail(String email);
	Optional<User> findByStudentId(String studentId);

	@Query("SELECT s FROM users s WHERE LOWER(s.fullName) LIKE LOWER(CONCAT('%', :searchTerm, '%')) " +
			"OR LOWER(s.studentId) LIKE LOWER(CONCAT('%', :searchTerm, '%'))")
	Page<User> findAllByFullNameOrStudentIdContainingIgnoreCase(
			@Param("searchTerm") String searchTerm, Pageable pageable);

	@Query("SELECT s FROM users s WHERE s.admissionDate BETWEEN :startDate AND :endDate")
	Page<User> findByAdmissionDateBetween(@Param("startDate") LocalDate startDate,
											 @Param("endDate") LocalDate endDate,
											 Pageable pageable);

}
