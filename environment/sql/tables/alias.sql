CREATE TABLE `alias` (
  `id` bigint(11) NOT NULL,
  `commandFK` bigint(11) NOT NULL,
  `alias` varchar(20) NOT NULL,
  `guildFK` bigint(11) NULL,
  PRIMARY KEY (`aliasId`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
