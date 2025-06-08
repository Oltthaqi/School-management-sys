-- V5__seed_default_data.sql

-- Insert default admin user
INSERT INTO `users`
(`username`, `email`, `password`, `first_name`, `last_name`, `is_active`)
VALUES
    ('admin', 'admin@school.com',
     '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2uheWG/igi.',
     'Admin', 'User', 1);

-- Assign ROLE_ADMIN
INSERT INTO `user_roles` (`user_id`, `role`)
VALUES
    (
        (SELECT `id` FROM `users` WHERE `username` = 'admin'),
        'ROLE_ADMIN'
    );

-- Insert sample teacher account
INSERT INTO `users`
(`username`, `email`, `password`, `first_name`, `last_name`, `is_active`)
VALUES
    ('teacher1', 'teacher1@school.com',
     '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2uheWG/igi.',
     'John', 'Smith', 1);

INSERT INTO `user_roles` (`user_id`, `role`)
VALUES
    (
        (SELECT `id` FROM `users` WHERE `username` = 'teacher1'),
        'ROLE_TEACHER'
    );

INSERT INTO `teachers`
(`user_id`, `employee_id`, `department`, `phone_number`, `specialization`)
VALUES
    (
        (SELECT `id` FROM `users` WHERE `username` = 'teacher1'),
        'EMP001', 'Computer Science', '+1234567890', 'Web Development'
    );

-- Insert sample student account
INSERT INTO `users`
(`username`, `email`, `password`, `first_name`, `last_name`, `is_active`)
VALUES
    ('student1', 'student1@school.com',
     '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2uheWG/igi.',
     'Jane', 'Doe', 1);

INSERT INTO `user_roles` (`user_id`, `role`)
VALUES
    (
        (SELECT `id` FROM `users` WHERE `username` = 'student1'),
        'ROLE_STUDENT'
    );

INSERT INTO `students`
(`user_id`, `student_id`, `date_of_birth`, `admission_date`, `phone_number`, `address`)
VALUES
    (
        (SELECT `id` FROM `users` WHERE `username` = 'student1'),
        'STU001', '2000-05-15', '2023-09-01', '+0987654321', '123 Main St, City, State'
    );

-- Insert a sample course
INSERT INTO `courses`
(`course_code`, `name`, `description`, `credit_hours`, `teacher_id`)
VALUES
    (
        'CS101', 'Introduction to Programming',
        'Basic programming concepts and problem-solving skills',
        3,
        (SELECT `id` FROM `teachers` WHERE `employee_id` = 'EMP001')
    );

-- Enroll that student in the course
INSERT INTO `enrollments`
(`student_id`, `course_id`, `enrolled_at`, `status`)
VALUES
    (
        (SELECT `id` FROM `students` WHERE `student_id` = 'STU001'),
        (SELECT `id` FROM `courses`  WHERE `course_code`  = 'CS101'),
        CURRENT_TIMESTAMP,
        'ACTIVE'
    );

-- Record an admission decision
INSERT INTO `admissions`
(`student_id`, `application_number`, `status`, `applied_at`, `decision_at`, `notes`)
VALUES
    (
        (SELECT `id` FROM `students` WHERE `student_id` = 'STU001'),
        'APP001', 'APPROVED',
        '2023-08-01', '2023-08-15',
        'Excellent academic record'
    );
