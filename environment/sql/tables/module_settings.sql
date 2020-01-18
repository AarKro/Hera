CREATE TABLE `module_settings` (
  `id` bigint(11) PRIMARY KEY AUTO_INCREMENT,
  `guildFK` bigint(11) NOT NULL,
  `commandFK` bigint(11) NOT NULL,
  `enabled` tinyint(1) NOT NULL,
  `roleFK` bigint(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
