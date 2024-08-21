package com.university.domain;

import java.util.List;

public class Student {
    private final Long id;
    private final String name;
    private final List<Enrollment> enrollments;

    public Student(Long id, String name, List<Enrollment> enrollments) {
        this.id = id;
        this.name = name;
        this.enrollments = enrollments;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<Enrollment> getEnrollments() {
        return enrollments;
    }

    public boolean hasCompletedBasicCourseInTopic(String topic) {
        return enrollments.stream()
                .anyMatch(e -> e.getCourse().getTopic().equals(topic) && !e.getCourse().isAdvanced());
    }

    public long getEnrollmentCountForCourse(Long courseId) {
        return enrollments.stream()
                .filter(e -> e.getCourse().getId().equals(courseId))
                .count();
    }
}
