CREATE TABLE `localisation` (
  `id` bigint(11) PRIMARY KEY AUTO_INCREMENT,
  `language` varchar(10) NOT NULL,
  `name` varchar(50) NOT NULL,
  `value` varchar(1000) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
