CREATE TABLE roles (
                       role_id SERIAL PRIMARY KEY,
                       role_name VARCHAR(50) NOT NULL UNIQUE,
                       description TEXT
);

CREATE TABLE users (
                       user_id SERIAL PRIMARY KEY,
                       username VARCHAR(50) UNIQUE,
                       password VARCHAR(255) NOT NULL,
                       role_id INTEGER,
                       full_name VARCHAR(100),
                       email VARCHAR(100) UNIQUE,
                       created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                       updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                       FOREIGN KEY (role_id) REFERENCES roles(role_id) ON DELETE CASCADE
);

CREATE TABLE course_section (
                                section_id SERIAL PRIMARY KEY,
                                course_id VARCHAR(50) NOT NULL,
                                section_name VARCHAR(100) NOT NULL,
                                instructor_id INTEGER NOT NULL,
                                FOREIGN KEY (instructor_id) REFERENCES users(user_id) ON DELETE CASCADE
);

CREATE TABLE registration (
                              registration_id SERIAL PRIMARY KEY,
                              student_id INTEGER NOT NULL,
                              section_id INTEGER NOT NULL,
                              registration_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                              FOREIGN KEY (student_id) REFERENCES users(user_id) ON DELETE CASCADE,
                              FOREIGN KEY (section_id) REFERENCES course_section(section_id) ON DELETE CASCADE,
                              UNIQUE (student_id, section_id)  -- Đảm bảo mỗi sinh viên chỉ có thể đăng ký một lần vào mỗi lớp học phần
);

CREATE TABLE grade (
                       grade_id SERIAL PRIMARY KEY,
                       student_id INTEGER NOT NULL,
                       section_id INTEGER NOT NULL,
                       grade NUMERIC(2, 0) CHECK (grade >= 0 AND grade <= 10),  -- Điểm số từ 0 đến 10
                       comments TEXT,
                       FOREIGN KEY (student_id) REFERENCES users(user_id) ON DELETE CASCADE,
                       FOREIGN KEY (section_id) REFERENCES course_section(section_id) ON DELETE CASCADE,
                       UNIQUE (student_id, section_id)  -- Đảm bảo mỗi sinh viên chỉ có một điểm cho mỗi lớp học phần
);

