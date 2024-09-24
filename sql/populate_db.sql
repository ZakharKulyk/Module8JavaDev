INSERT INTO WORKER (name, birthday, level, salary)
VALUES ('Roman', '1980-08-10', 'Senior', 5000),
       ('Zakhar', '2005-11-04', 'Middle', 2500),
       ('Artem', '2006-08-31', 'Junior', 1200),
       ('Julia', '1980-06-02', 'Trainee', 500),
       ('John', '2000-01-20', 'Senior', 7000),
       ('Anton', '2003-04-04', 'Trainee', 600),
       ('Bogdan', '2002-07-08', 'Middle', 3000),
       ('Donald', '1946-05-14', 'Senior', 9500),
       ('Kate', '2003-02-10', 'Senior', 8000),
       ('Pavel', '2004-05-24', 'Middle', 2700);


INSERT INTO CLIENT(name)
VALUES ('John'),
       ('Richard'),
       ('Roman'),
       ('Julia'),
       ('Tom');


INSERT INTO PROJECT (client_id, start_date, finish_date)
VALUES (1, '2024-01-01', '2024-03-01'),
       (2, '2023-06-15', '2024-09-15'),
       (3, '2022-05-01', '2023-11-01'),
       (4, '2024-03-01', '2024-07-01'),
       (5, '2024-04-15', '2024-10-15'),
       (2, '2023-11-01', '2024-11-01'),
       (1, '2024-06-01', '2025-02-01'),
       (5, '2024-07-01', '2024-12-01'),
       (4, '2023-08-15', '2024-12-15'),
       (3, '2024-05-01', '2024-12-01');



INSERT INTO PROJECT_WORKER(project_id, worker_id)
VALUES (1, 2),
       (1, 3),
       (2, 1),
       (3, 9),
       (3, 5),
       (4, 4),
       (5, 1),
       (8, 7),
       (8, 10),
       (6, 6),
       (7, 4),
       (9, 9),
       (10, 5),
       (10, 9);