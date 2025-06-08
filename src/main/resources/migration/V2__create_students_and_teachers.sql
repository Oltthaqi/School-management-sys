CREATE TABLE IF NOT EXISTS `students` (
                                          `id`            BIGINT NOT NULL AUTO_INCREMENT,
                                          `user_id`       BIGINT NOT NULL,
                                          `student_id`    VARCHAR(20) NOT NULL UNIQUE,
    `date_of_birth` DATE,
    `admission_date` DATE,
    `phone_number`  VARCHAR(20),
    `address`       TEXT,
    `created_at`    TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `updated_at`    TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_students_user_id` (`user_id`),
    CONSTRAINT `fk_students_user`
    FOREIGN KEY (`user_id`) REFERENCES `users`(`id`)
                                                                 ON DELETE CASCADE
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS `teachers` (
                                          `id`            BIGINT NOT NULL AUTO_INCREMENT,
                                          `user_id`       BIGINT NOT NULL,
                                          `employee_id`   VARCHAR(20) NOT NULL UNIQUE,
    `department`    VARCHAR(100) NOT NULL,
    `phone_number`  VARCHAR(20),
    `specialization` VARCHAR(100),
    `created_at`    TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `updated_at`    TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_teachers_user_id` (`user_id`),
    CONSTRAINT `fk_teachers_user`
    FOREIGN KEY (`user_id`) REFERENCES `users`(`id`)
                                                                 ON DELETE CASCADE
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
