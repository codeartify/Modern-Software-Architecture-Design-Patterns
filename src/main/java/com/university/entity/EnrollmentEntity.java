package com.university.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "enrollments")
public class EnrollmentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id", nullable = false)
    private StudentEntity student;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id", nullable = false)
    private CourseEntity course;

    // Constructors
    public EnrollmentEntity() {
    }

    public EnrollmentEntity(StudentEntity student, CourseEntity course) {
        this.student = student;
        this.course = course;
    }

    // Getters
    public Long getId() {
        return id;
    }

    public StudentEntity getStudent() {
        return student;
    }

    public CourseEntity getCourse() {
        return course;
    }

    // Setters
    public void setStudent(StudentEntity student) {
        this.student = student;
    }

    public void setCourse(CourseEntity course) {
        this.course = course;
    }
}
