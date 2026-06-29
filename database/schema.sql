CREATE DATABASE IF NOT EXISTS lesson_reservation
  CHARACTER SET utf8mb4
  COLLATE utf8mb4_unicode_ci;

USE lesson_reservation;

DROP TABLE IF EXISTS reservations;
DROP TABLE IF EXISTS lessons;
DROP TABLE IF EXISTS users;

CREATE TABLE users (
  id INT PRIMARY KEY AUTO_INCREMENT,
  name VARCHAR(80) NOT NULL,
  email VARCHAR(255) NOT NULL UNIQUE,
  password VARCHAR(255) NOT NULL,
  created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE lessons (
  id INT PRIMARY KEY AUTO_INCREMENT,
  category VARCHAR(50) NOT NULL,
  lesson_date DATE NOT NULL,
  start_time TIME NOT NULL,
  end_time TIME NOT NULL,
  title VARCHAR(120) NOT NULL,
  instructor VARCHAR(80) NOT NULL,
  level_name VARCHAR(30) NOT NULL,
  capacity INT NOT NULL,
  description TEXT NOT NULL,
  streaming_id VARCHAR(50) NOT NULL,
  streaming_password VARCHAR(50) NOT NULL,
  created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  INDEX idx_lessons_schedule (lesson_date, start_time),
  INDEX idx_lessons_category (category),
  INDEX idx_lessons_instructor (instructor)
);

CREATE TABLE reservations (
  id INT PRIMARY KEY AUTO_INCREMENT,
  user_id INT NOT NULL,
  lesson_id INT NOT NULL,
  reserved_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  CONSTRAINT fk_reservations_user
    FOREIGN KEY (user_id) REFERENCES users (id),
  CONSTRAINT fk_reservations_lesson
    FOREIGN KEY (lesson_id) REFERENCES lessons (id),
  CONSTRAINT uq_user_lesson UNIQUE (user_id, lesson_id)
);
