CREATE TABLE `default_role` (
  `id` bigint(11) PRIMARY KEY AUTO_INCREMENT,
  `guildFK` bigint(11) NOT NULL,
  `roleFK` bigint(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
