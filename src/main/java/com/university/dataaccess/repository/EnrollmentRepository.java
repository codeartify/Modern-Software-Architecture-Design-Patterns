package com.university.dataaccess.repository;

import com.university.dataaccess.entity.EnrollmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EnrollmentRepository extends JpaRepository<EnrollmentEntity, Long> {

    // Check if there are any enrollments for a particular student
    boolean existsByStudentId(Long studentId);
}
