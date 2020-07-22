CREATE TABLE `command` (
  `id` bigint(11) PRIMARY KEY AUTO_INCREMENT,
  `name` varchar(30) NOT NULL,
  `description` bigint(11) NOT NULL,
  `paramCount` int(11) NOT NULL,
  `optionalParams` int(11) NOT NULL,
  `level` tinyint(1) NOT NULL,
  `minPermission` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
