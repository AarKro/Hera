CREATE TABLE `alias` (
  `id` bigint(11) PRIMARY KEY AUTO_INCREMENT,
  `commandFK` bigint(11) NOT NULL,
  `alias` varchar(20) NOT NULL,
  `guildFK` bigint(11)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
