CREATE TABLE `binding_type` (
  `id` bigint(11) PRIMARY KEY AUTO_INCREMENT,
  `type` varchar(30) NOT NULL,
  `localisationFK` bigint(11) NOT NULL,
  `isGlobal` tinyint(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
