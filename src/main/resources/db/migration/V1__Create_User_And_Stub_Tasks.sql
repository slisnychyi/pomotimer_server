-- Drop tables if they exist
DROP TABLE IF EXISTS tasks;
DROP TABLE IF EXISTS users;

-- Create users table
CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    email VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL
);

-- Insert user data
INSERT INTO users (email, password) VALUES ('test@email.com', '123');

-- Create tasks table
CREATE TABLE tasks (
    id SERIAL PRIMARY KEY,
    task_id VARCHAR(255) NOT NULL,
    name VARCHAR(255) NOT NULL,
    date DATE NOT NULL,
    completed BOOLEAN NOT NULL,
    pomodoros INT NOT NULL,
    completed_pomodoros INT NOT NULL,
    task_notes VARCHAR(255),
    user_id BIGINT,
    FOREIGN KEY (user_id) REFERENCES users (id)
);

-- Insert stub data into tasks table
INSERT INTO tasks (task_id, name, date, completed, pomodoros, completed_pomodoros, task_notes, user_id)
VALUES
    -- Scheduled tasks
    ('1', 'Підготувати робоче завдання', CURRENT_DATE, false, 10, 5, 'Some task note', 1),
    ('2', 'Приготувати сніданок', CURRENT_DATE + INTERVAL '1' DAY, false, 3, 2, 'Some task note2', 1),
    ('3', 'Сходити в спортзал', CURRENT_DATE + INTERVAL '2' DAY, false, 5, 3, 'Some task note3', 1),
    ('4', 'Виконати лабораторну по моделюванню систем', CURRENT_DATE, false, 6, 4, 'Task note for lab', 1),
    ('5', 'Сконфігурувати Docker', CURRENT_DATE + INTERVAL '1' DAY, false, 8, 6, 'Task note for Docker', 1),
    -- Completed tasks
    ('6', 'Намалювати UML діаграму', CURRENT_DATE, true, 4, 3, 'Completed task note 1', 1),
    ('7', 'Описати use-case сценарії', CURRENT_DATE - INTERVAL '1' DAY, true, 7, 6, 'Completed task note 2', 1),
    ('9', 'Підключити postgres до проекту', CURRENT_DATE - INTERVAL '1' DAY, true, 5, 6, 'Completed task note 4', 1);
