CREATE TABLE `metric` (
  `ìd` int(11) PRIMARY KEY AUTO_INCREMENT,
  `name` varchar(30) NOT NULL,
  `date` TIMESTAMP NOT NULL,
  `commandFK` int(11) NULL,
  `guildFK` bigint(11) NULL,
  `userFK` bigint(11) NULL,
  `value` bigint(11) NULL,
  `details` varchar(200) NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
