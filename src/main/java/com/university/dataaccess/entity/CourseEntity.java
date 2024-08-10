package com.university.dataaccess.entity;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "courses")
public class CourseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private boolean isAdvanced;

    @Column(nullable = false)
    private String topic;

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<EnrollmentEntity> enrollments = new ArrayList<>();

    // Constructors
    public CourseEntity() {
    }

    public CourseEntity(String title, boolean isAdvanced, String topic) {
        this.title = title;
        this.isAdvanced = isAdvanced;
        this.topic = topic;
    }

    // Getters
    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public boolean isAdvanced() {
        return isAdvanced;
    }

    public String getTopic() {
        return topic;
    }

    public List<EnrollmentEntity> getEnrollments() {
        return enrollments;
    }

    // Add and remove methods for enrollments
    public void addEnrollment(EnrollmentEntity enrollment) {
        enrollments.add(enrollment);
        enrollment.setCourse(this);
    }

    public void removeEnrollment(EnrollmentEntity enrollment) {
        enrollments.remove(enrollment);
        enrollment.setCourse(null);
    }
}
