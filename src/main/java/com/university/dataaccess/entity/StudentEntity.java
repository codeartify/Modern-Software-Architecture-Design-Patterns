package com.university.dataaccess.entity;

import com.university.dataaccess.entity.EnrollmentEntity;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "students")
public class StudentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<EnrollmentEntity> enrollments = new ArrayList<>();

    // Constructors
    public StudentEntity() {
    }

    public StudentEntity(String name) {
        this.name = name;
    }

    // Getters
    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<EnrollmentEntity> getEnrollments() {
        return enrollments;
    }

    // Add and remove methods for enrollments
    public void addEnrollment(EnrollmentEntity enrollment) {
        enrollments.add(enrollment);
        enrollment.setStudent(this);
    }

    public void removeEnrollment(EnrollmentEntity enrollment) {
        enrollments.remove(enrollment);
        enrollment.setStudent(null);
    }
}

