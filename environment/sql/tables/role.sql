CREATE TABLE `role` (
  `id` int(11) PRIMARY KEY AUTO_INCREMENT,
  `guildFK` int(11) NOT NULL,
  `parent` int(11) DEFAULT NULL,
  `name` varchar(30) NOT NULL,
  `description` varchar(500) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
