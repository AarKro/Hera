CREATE TABLE `binding` (
  `id` bigint(11) PRIMARY KEY AUTO_INCREMENT,
  `guildFK` bigint(11) NOT NULL,
  `bindingTypeFK` bigint(11) NOT NULL,
  `channelSnowflake` bigint(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
