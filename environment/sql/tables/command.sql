CREATE TABLE `command` (
  `id` int(11) PRIMARY KEY,
  `name` varchar(30) NOT NULL,
  `description` varchar(500) NOT NULL,
  `paramCount` int(11) NOT NULL,
  `infiniteParam` tinyint(1) NOT NULL,
  `level` tinyint(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
