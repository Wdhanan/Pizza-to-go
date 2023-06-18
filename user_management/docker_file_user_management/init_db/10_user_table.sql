CREATE TABLE user
(
    user_id       INT AUTO_INCREMENT PRIMARY KEY,
    username      VARCHAR(20) NOT NULL UNIQUE,
    email         VARCHAR(50) NOT NULL,
    firstname     VARCHAR(20) NOT NULL,
    lastname      VARCHAR(20) NOT NULL,
    telefon	  VARCHAR(20) NOT NULL, 
    street        VARCHAR(30) NOT NULL,
    street_number VARCHAR(20) NOT NULL,
    zip           VARCHAR(20) NOT NULL,
    city          VARCHAR(30) NOT NULL,
    password_hash BINARY(20) NOT NULL,
    password_salt BINARY(32) NOT NULL
) CHARACTER SET utf8mb4;