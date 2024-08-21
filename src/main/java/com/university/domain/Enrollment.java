package com.university.domain;

public class Enrollment {
    private final Long id;
    private final Long studentId; // Use ID instead of Student object
    private final Course course;

    public Enrollment(Long id, Long studentId, Course course) {
        this.id = id;
        this.studentId = studentId;
        this.course = course;
    }

    public Long getId() {
        return id;
    }

    public Long getStudentId() {
        return studentId;
    }

    public Course getCourse() {
        return course;
    }
}
