package com.university.dataaccess.repository;

import com.university.dataaccess.entity.StudentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StudentRepository extends JpaRepository<StudentEntity, Long> {

    // Find a student by name
    Optional<StudentEntity> findByName(String name);

    // Check if a student exists by name
    boolean existsByName(String name);

    // Check if a student exists by ID
    boolean existsById(Long id);


    Optional<StudentEntity> findById(Long id);

    StudentEntity save(StudentEntity studentEntity);

    void deleteById(Long id);
}
