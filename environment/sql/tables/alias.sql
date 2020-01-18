CREATE TABLE `alias` (
  `id` int(11) PRIMARY KEY AUTO_INCREMENT,
  `commandFK` int(11) NOT NULL,
  `alias` varchar(20) NOT NULL,
  `guildFK` int(11)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
