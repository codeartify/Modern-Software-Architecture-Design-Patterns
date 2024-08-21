-- Correct SQL inserts based on the CourseEntity structure
INSERT INTO courses (title, is_advanced, topic) VALUES ('Math 101', false, 'Mathematics');
INSERT INTO courses (title, is_advanced, topic) VALUES ('History 101', false, 'History');

INSERT INTO students (id, name) VALUES (1,'John Doe');
INSERT INTO students (id, name) VALUES (2,'Jane Smith');

INSERT INTO enrollments (student_id, course_id) VALUES (1, 1);
INSERT INTO enrollments (student_id, course_id) VALUES (2, 2);
