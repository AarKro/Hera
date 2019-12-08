CREATE TABLE `default_role` (
  `guildFK` bigint(11) NOT NULL,
  `roleFK` int(11) NOT NULL,
   PRIMARY KEY (`guildFK`, `roleFK`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
