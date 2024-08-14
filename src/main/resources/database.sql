CREATE TABLE users (
    id BIGSERIAL PRIMARY KEY,
    student_id VARCHAR(20) UNIQUE,
    email VARCHAR(100) UNIQUE NOT NULL,
    full_name VARCHAR(100),
    password VARCHAR(255) NOT NULL,
    dob DATE,
    gender VARCHAR(10)
       CONSTRAINT student_gender_check
           CHECK ((gender)::text = ANY
                  ((ARRAY ['MALE'::CHARACTER VARYING, 'FEMALE'::CHARACTER VARYING, 'OTHER'::CHARACTER VARYING])::text[])),
    photo oid,
    admission_date DATE,
    class_belongs VARCHAR(10),
    created_at TIMESTAMP(0),
    update_at TIMESTAMP(0)
);

CREATE TABLE roles (
    id SERIAL NOT NULL PRIMARY KEY,
    name VARCHAR(10),
    description VARCHAR(50)
);

CREATE TABLE users_roles (
    user_id BIGINT REFERENCES users(id) ON DELETE CASCADE,
    role_id INT REFERENCES roles(id) ON DELETE CASCADE
);

CREATE TABLE invalidated_token (
    id VARCHAR(255) NOT NULL PRIMARY KEY,
    expired_time TIMESTAMP(6)
);