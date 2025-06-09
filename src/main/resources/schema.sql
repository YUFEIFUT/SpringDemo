CREATE TABLE users (
    id INT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(255)
);

CREATE TABLE points (
    id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT,
    amount INT
);