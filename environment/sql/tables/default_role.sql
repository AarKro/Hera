CREATE TABLE `default_role` (
  `id` int(11) PRIMARY KEY AUTO_INCREMENT,
  `guildFK` int(11) NOT NULL,
  `roleFK` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
