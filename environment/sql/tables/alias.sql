CREATE TABLE `alias` (
  `id` bigint(11) PRIMARY KEY AUTO_INCREMENT,
  `commandFK` int(11) NOT NULL,
  `alias` varchar(20) NOT NULL,
  `guildFK` bigint(11) NULL,
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
