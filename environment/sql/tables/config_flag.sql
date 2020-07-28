CREATE TABLE `config_flag` (
  `id` bigint(11) PRIMARY KEY AUTO_INCREMENT,
  `guildFK` bigint(11) NOT NULL,
  `configFlagTypeFK` bigint(11) NOT NULL,
  `value` tinyint(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
