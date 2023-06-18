INSERT INTO `orders` (`order_id`, `username`, `price`, `date_order`) VALUES
	(1, 'test1', 13.20, '2022-01-13 10:51:57'),
	(2, 'test3', 4.40, '2022-01-13 10:51:57');

INSERT INTO `pizza` (`pizza_id`, `order_id`, `size`, `price`, `topping1`) VALUES
	(1, 1, 'big', 6.6, 'salami'),
	(2, 1, 'big', 6.6, 'paprica'),
	(3, 2, 'small', 4.4, 'ham');
