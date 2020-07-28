CREATE TABLE `config_flag_type` (
  `id` bigint(11) PRIMARY KEY AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  `isDefault` tinyint(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
