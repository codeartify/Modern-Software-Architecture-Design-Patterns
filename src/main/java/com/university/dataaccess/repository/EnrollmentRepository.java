package com.university.dataaccess.repository;

import com.university.dataaccess.entity.EnrollmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EnrollmentRepository extends JpaRepository<EnrollmentEntity, Long> {

    // Check if there are any enrollments for a particular student
    boolean existsByStudentId(Long studentId);

    // Find all enrollments for a particular student
    List<EnrollmentEntity> findByStudentId(Long studentId);

    // Find all enrollments for a particular course
    List<EnrollmentEntity> findByCourseId(Long courseId);

    // Count the number of times a student has enrolled in a specific course
    long countByStudentIdAndCourseId(Long studentId, Long courseId);

}
