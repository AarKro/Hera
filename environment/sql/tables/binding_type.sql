CREATE TABLE `binding_type` (
  `id` bigint(11) PRIMARY KEY AUtO_INCREMENT,
  `type` varchar(30) NOT NULL,
  `localisationFK` bigint(11) NOT NULL,
  `snowflakeTypeFK` bigint(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
