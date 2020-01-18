CREATE TABLE `binding` (
  `id` int(11) PRIMARY KEY AUTO_INCREMENT,
  `guildFK` int(11) NOT NULL,
  `bindingTypeFK` int(11) NOT NULL,
  `channelSnowflake` bigint(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
