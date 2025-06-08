-- V4__create_grades_admissions_and_indexes.sql

CREATE TABLE IF NOT EXISTS `grades` (
                                        `id`            BIGINT NOT NULL AUTO_INCREMENT,
                                        `enrollment_id` BIGINT NOT NULL UNIQUE,
                                        `grade_value`   FLOAT NOT NULL,
    `letter_grade`  VARCHAR(2),
    `graded_at`     TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `comments`      TEXT,
    `created_at`    TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `updated_at`    TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`),
    CONSTRAINT `fk_grades_enrollment`
    FOREIGN KEY (`enrollment_id`) REFERENCES `enrollments`(`id`)
                                                                 ON DELETE CASCADE
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS `admissions` (
                                            `id`               BIGINT NOT NULL AUTO_INCREMENT,
                                            `student_id`       BIGINT NOT NULL UNIQUE,
                                            `application_number` VARCHAR(20) NOT NULL UNIQUE,
    `status`           VARCHAR(20) NOT NULL DEFAULT 'PENDING',
    `applied_at`       TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `decision_at`      TIMESTAMP NULL,
    `notes`            TEXT,
    `created_at`       TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `updated_at`       TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`),
    CONSTRAINT `fk_admissions_student`
    FOREIGN KEY (`student_id`) REFERENCES `students`(`id`)
                                                                    ON DELETE CASCADE
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Indexes for performance
CREATE INDEX `idx_users_email`       ON `users`(`email`);
CREATE INDEX `idx_users_username`    ON `users`(`username`);
CREATE INDEX `idx_students_student_id`  ON `students`(`student_id`);
CREATE INDEX `idx_teachers_employee_id` ON `teachers`(`employee_id`);
CREATE INDEX `idx_courses_code`      ON `courses`(`course_code`);
CREATE INDEX `idx_enrollments_student` ON `enrollments`(`student_id`);
CREATE INDEX `idx_enrollments_course`  ON `enrollments`(`course_id`);
CREATE INDEX `idx_grades_enrollment`   ON `grades`(`enrollment_id`);
CREATE INDEX `idx_admissions_application` ON `admissions`(`application_number`);
