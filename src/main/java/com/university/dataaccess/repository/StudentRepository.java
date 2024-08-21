package com.university.dataaccess.repository;

import com.university.dataaccess.entity.StudentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StudentRepository extends JpaRepository<StudentEntity, Long> {

    boolean existsById(Long id);

    Optional<StudentEntity> findById(Long id);
}
