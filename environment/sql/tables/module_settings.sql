CREATE TABLE `module_settings` (
  `id` int(11) PRIMARY KEY AUTO_INCREMENT,
  `guildFK` int(11) NOT NULL,
  `commandFK` int(11) NOT NULL,
  `enabled` tinyint(1) NOT NULL,
  `roleFK` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
