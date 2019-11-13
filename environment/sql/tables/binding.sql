CREATE TABLE `binding` (
  `guildFK` bigint(11) NOT NULL,
  `bindingTypeFK` int(11) NOT NULL,
  `channelSnowflake` bigint(11) NOT NULL,
  PRIMARY KEY (`guildFK`, `bindingTypeFK`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
