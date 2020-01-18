CREATE TABLE `role_member` (
  `id` int(11) PRIMARY KEY AUTO_INCREMENT,
  `snowflake` bigint(11) NOT NULL,
  `roleFK` int(11) NOT NULL,
  `snowflakeTypeFK` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
