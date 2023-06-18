CREATE TABLE cartpizza
(
    pizza_id   INT AUTO_INCREMENT PRIMARY KEY,
    username   VARCHAR(20) NOT NULL,
    size       VARCHAR(20) NOT NULL,
    topping1   VARCHAR(50),
    topping2   VARCHAR(50),
    topping3   VARCHAR(50),
    topping4   VARCHAR(50),
    topping5   VARCHAR(50)
) CHARACTER SET utf8mb4;
