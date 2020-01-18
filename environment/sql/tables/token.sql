CREATE TABLE `token` (
  `id` int(11) PRIMARY KEY AUTO_INCREMENT,
  `token` varchar(500) NOT NULL,
  `name` varchar(50) NOT NULL,
  `description` varchar(500) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
