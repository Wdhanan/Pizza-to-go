CREATE TABLE orders
(
    order_id       INT AUTO_INCREMENT PRIMARY KEY,
    username       VARCHAR(20) NOT NULL,
    price 		 FLOAT NOT NULL,
    date_order     DATETIME
) CHARACTER SET utf8mb4;