package com.university.presentation;

import com.university.domain.Student;
import com.university.application.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/students")
public class StudentController {

    private final StudentService studentService;

    @Autowired
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping
    public ResponseEntity<List<Student>> getAllStudents() {
        List<Student> students = studentService.findAll();
        return ResponseEntity.ok(students);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Student> getStudentById(@PathVariable("id") Long id) {
        return studentService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/{studentId}/enroll/{courseId}")
    public ResponseEntity<Void> enrollInCourse(@PathVariable("studentId") Long studentId, @PathVariable("courseId") Long courseId) {
        try {
            studentService.enrollInCourse(studentId, courseId);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
