CREATE TABLE `role_member` (
  `id` bigint(11) PRIMARY KEY AUTO_INCREMENT,
  `snowflake` bigint(11) NOT NULL,
  `roleFK` bigint(11) NOT NULL,
  `snowflakeTypeFK` bigint(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
