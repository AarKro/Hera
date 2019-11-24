CREATE TABLE `command_metrics` (
  `commandFK` int(11) NOT NULL,
  `guildFK` bigint(11) NOT NULL,
  `userFK` bigint(11) NOT NULL,
  `callCount` int(11) NOT NULL,
  `date` DATE NOT NULL,
  PRIMARY KEY (`commandFK`, `guildFK`, `userFK`, `date`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
