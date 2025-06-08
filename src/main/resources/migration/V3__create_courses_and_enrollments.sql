CREATE TABLE IF NOT EXISTS `courses` (
                                         `id`           BIGINT NOT NULL AUTO_INCREMENT,
                                         `course_code`  VARCHAR(20) NOT NULL UNIQUE,
    `name`         VARCHAR(100) NOT NULL,
    `description`  TEXT,
    `credit_hours` INT,
    `teacher_id`   BIGINT NOT NULL,
    `created_at`   TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `updated_at`   TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`),
    CONSTRAINT `fk_courses_teacher`
    FOREIGN KEY (`teacher_id`) REFERENCES `teachers`(`id`)
                                                                ON DELETE CASCADE
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS `enrollments` (
                                             `id`          BIGINT NOT NULL AUTO_INCREMENT,
                                             `student_id`  BIGINT NOT NULL,
                                             `course_id`   BIGINT NOT NULL,
                                             `enrolled_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                             `status`      VARCHAR(20) NOT NULL DEFAULT 'ACTIVE',
    `created_at`  TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `updated_at`  TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_enrollments_student_course` (`student_id`,`course_id`),
    CONSTRAINT `fk_enrollments_student`
    FOREIGN KEY (`student_id`) REFERENCES `students`(`id`)
                                                               ON DELETE CASCADE,
    CONSTRAINT `fk_enrollments_course`
    FOREIGN KEY (`course_id`) REFERENCES `courses`(`id`)
                                                               ON DELETE CASCADE
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
