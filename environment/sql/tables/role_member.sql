CREATE TABLE `role_member` (
  `snowflake` bigint(11) NOT NULL,
  `roleFK` int(11) NOT NULL,
  `snowflakeTypeFK` int(11) NOT NULL,
   PRIMARY KEY (`snowflake`, `roleFK`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
