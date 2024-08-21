package com.university.db;

import com.university.db.entity.EnrollmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EnrollmentRepository extends JpaRepository<EnrollmentEntity, Long> {

    // Check if there are any enrollments for a particular student
    boolean existsByStudentId(Long studentId);
}
