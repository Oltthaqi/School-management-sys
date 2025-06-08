INSERT INTO `users`
( `email`, `password`, `first_name`, `last_name`, `is_active`)
VALUES
    ('oltthaqi@school.admin.com',
     '$2a$10$aAp/R9HiePr9vj9Z1egYxOQ6kjz2WC2mcEDdEAcyy.H7/gNWjPtve',
     'Olt', 'Thaqi', 1);

-- Assign ROLE_ADMIN
INSERT INTO `user_roles` (`user_id`, `role`)
VALUES
    (
        (SELECT `id` FROM `users` WHERE `email` = 'oltthaqi@school.admin.com'),
        'ROLE_ADMIN'
    );