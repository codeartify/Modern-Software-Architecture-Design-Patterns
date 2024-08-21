package com.university.service;

import com.university.domain.Course;
import com.university.domain.Enrollment;
import com.university.domain.Student;
import com.university.dataaccess.entity.CourseEntity;
import com.university.dataaccess.entity.EnrollmentEntity;
import com.university.dataaccess.entity.StudentEntity;
import com.university.dataaccess.repository.CourseRepository;
import com.university.dataaccess.repository.EnrollmentRepository;
import com.university.dataaccess.repository.StudentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class StudentService {
    private final StudentRepository studentRepository;
    private final EnrollmentRepository enrollmentRepository;
    private final CourseRepository courseRepository;

    public StudentService(StudentRepository studentRepository, EnrollmentRepository enrollmentRepository, CourseRepository courseRepository) {
        this.studentRepository = studentRepository;
        this.enrollmentRepository = enrollmentRepository;
        this.courseRepository = courseRepository;
    }

    public List<Student> findAll() {
        return studentRepository.findAll()
                .stream()
                .map(this::mapToDomain)
                .collect(Collectors.toList());
    }

    public Optional<Student> findById(Long id) {
        return studentRepository.findById(id).map(this::mapToDomain);
    }


    @Transactional
    public void deleteById(Long id) {
        if (enrollmentRepository.existsByStudentId(id)) {
            throw new IllegalStateException("Cannot delete student who is enrolled in courses");
        }
        studentRepository.deleteById(id);
    }

    @Transactional
    public void enrollInCourse(Long studentId, Long courseId) {
        StudentEntity studentEntity = studentRepository.findById(studentId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid student ID"));
        Student student = mapToDomain(studentEntity);

        CourseEntity courseEntity = courseRepository.findById(courseId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid course ID"));
        Course course = mapToDomain(courseEntity);

        if (student.getEnrollmentCountForCourse(courseId) >= 2) {
            throw new IllegalStateException("Cannot enroll in the same course more than twice");
        }

        if (course.isAdvanced() && !student.hasCompletedBasicCourseInTopic(course.getTopic())) {
            throw new IllegalStateException("Must complete basic courses in " + course.getTopic() + " before enrolling in an advanced course");
        }

        EnrollmentEntity enrollmentEntity = new EnrollmentEntity(studentEntity, courseEntity);
        enrollmentRepository.save(enrollmentEntity);
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
        return new Enrollment(enrollmentEntity.getId(), enrollmentEntity.getStudent().getId(), mapToDomain(enrollmentEntity.getCourse()));
    }

    private StudentEntity mapToEntity(Student student) {
        return new StudentEntity(student.getName());
    }

    private CourseEntity mapToEntity(Course course) {
        return new CourseEntity(course.getTitle(), course.isAdvanced(), course.getTopic());
    }
}
