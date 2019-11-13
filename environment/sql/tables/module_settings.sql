CREATE TABLE `module_settings` (
  `guildFK` bigint(11) NOT NULL,
  `commandFK` int(11) NOT NULL,
  `enabled` tinyint(1) NOT NULL,
  `roleFK` int(11) DEFAULT NULL,
  PRIMARY KEY (`guildFK`, `commandFK`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
