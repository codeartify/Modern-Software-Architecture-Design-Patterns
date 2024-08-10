package com.university;

import com.university.domain.Course;
import com.university.domain.Enrollment;
import com.university.domain.Student;
import com.university.entity.CourseEntity;
import com.university.entity.EnrollmentEntity;
import com.university.entity.StudentEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudentService {

    @Transactional
    public void enrollInCourse(Long studentId, Long courseId) {
       // TODO implement your logic here
    }

    private Student mapToDomain(StudentEntity studentEntity) {
        List<Enrollment> enrollments = studentEntity.getEnrollments().stream()
                .map(this::mapToDomain)
                .collect(Collectors.toList());
        return new Student(studentEntity.getId(), studentEntity.getName(), enrollments);
    }

    private Course mapToDomain(CourseEntity courseEntity) {
        return new Course(courseEntity.getId(), courseEntity.getTitle(), courseEntity.isAdvanced(), courseEntity.getTopic());
    }

    private Enrollment mapToDomain(EnrollmentEntity enrollmentEntity) {
        return new Enrollment(enrollmentEntity.getId(),
                mapToDomain(enrollmentEntity.getStudent()),
                mapToDomain(enrollmentEntity.getCourse()));
    }

}
