CREATE TABLE courses (
                         id BIGINT AUTO_INCREMENT PRIMARY KEY,
                         title VARCHAR(255) NOT NULL,
                         is_advanced BOOLEAN NOT NULL,
                         topic VARCHAR(255) NOT NULL
);

CREATE TABLE students (
                          id BIGINT AUTO_INCREMENT PRIMARY KEY,
                          name VARCHAR(255) NOT NULL
);

CREATE TABLE enrollments (
                             id BIGINT AUTO_INCREMENT PRIMARY KEY,
                             student_id BIGINT,
                             course_id BIGINT,
                             FOREIGN KEY (student_id) REFERENCES students(id),
                             FOREIGN KEY (course_id) REFERENCES courses(id)
);
