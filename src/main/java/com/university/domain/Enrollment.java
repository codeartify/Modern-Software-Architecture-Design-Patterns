package com.university.domain;

public class Enrollment {
    private final Long id;
    private final Student student;
    private final Course course;

    public Enrollment(Long id, Student student, Course course) {
        this.id = id;
        this.student = student;
        this.course = course;
    }

    public Long getId() {
        return id;
    }

    public Student getStudent() {
        return student;
    }

    public Course getCourse() {
        return course;
    }
}