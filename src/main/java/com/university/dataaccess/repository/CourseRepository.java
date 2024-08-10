package com.university.dataaccess.repository;

import com.university.dataaccess.entity.CourseEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CourseRepository extends JpaRepository<CourseEntity, Long> {

    // Find all courses by topic
    List<CourseEntity> findByTopic(String topic);

    // Find all basic (non-advanced) courses by topic
    List<CourseEntity> findByTopicAndIsAdvancedFalse(String topic);

    // Find all advanced courses
    List<CourseEntity> findByIsAdvancedTrue();

    // Check if a course exists by ID
    boolean existsById(Long id);

    Optional<CourseEntity> findById(Long courseId);
}
