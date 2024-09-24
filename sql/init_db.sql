CREATE TABLE client
(
    client_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name      VARCHAR(1000) NOT NULL CHECK (LENGTH(name) BETWEEN 2 AND 1000)
);


CREATE TABLE worker
(
    id       BIGINT AUTO_INCREMENT PRIMARY KEY,
    name     VARCHAR(1000) NOT NULL CHECK (LENGTH(name) BETWEEN 2 AND 1000),
    birthday DATE,
    level    VARCHAR(50)   NOT NULL,
    salary   INTEGER,

    CONSTRAINT check_birthday CHECK (birthday > '1900-01-01'),
    CONSTRAINT check_level CHECK (level IN ('Trainee', 'Junior', 'Middle', 'Senior')),
    CONSTRAINT check_salary CHECK (salary > 100 AND salary < 100000)
);


CREATE TABLE project
(
    project_id  BIGINT AUTO_INCREMENT PRIMARY KEY,
    client_id   BIGINT,
    start_date  DATE,
    finish_date DATE,

    FOREIGN KEY (client_id) REFERENCES client (client_id)
);


CREATE TABLE project_worker
(
    project_id BIGINT,
    worker_id  BIGINT,
    PRIMARY KEY (project_id, worker_id),
    FOREIGN KEY (worker_id) REFERENCES worker (id),
    FOREIGN KEY (project_id) REFERENCES project (project_id)
);