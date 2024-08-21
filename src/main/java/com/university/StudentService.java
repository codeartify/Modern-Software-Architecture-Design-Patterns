package com.university;

import com.university.domain.Course;
import com.university.domain.Enrollment;
import com.university.domain.Student;
import com.university.db.entity.CourseEntity;
import com.university.db.entity.EnrollmentEntity;
import com.university.db.entity.StudentEntity;
import com.university.db.CourseRepository;
import com.university.db.EnrollmentRepository;
import com.university.db.StudentRepository;
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

    @Transactional
    public void enrollInCourse(Long studentId, Long courseId) {
        // TODO Implement this method
        // A student cannot enroll in the same course more than twice.
        //A student cannot enroll in an advanced course on a specific topic unless they have already completed the corresponding basic courses.
        //Prevent double enrollment in the same course in the same semester.
        //Only existing student can sign up to the existing course
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
