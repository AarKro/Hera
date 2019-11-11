-- phpMyAdmin SQL Dump
-- version 4.9.0.1
-- https://www.phpmyadmin.net/
--
-- Host: localhost:3306
-- Generation Time: Nov 10, 2019 at 08:16 PM
-- Server version: 10.2.27-MariaDB-cll-lve
-- PHP Version: 7.3.6

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `aaronkromer_hera`
--

-- --------------------------------------------------------

--
-- Table structure for table `binding`
--

CREATE TABLE `binding` (
  `guildFK` bigint(11) NOT NULL,
  `bindingTypeFK` int(11) NOT NULL,
  `channelSnowflake` bigint(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `binding_type`
--

CREATE TABLE `binding_type` (
  `id` int(11) NOT NULL,
  `type` varchar(30) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `command`
--

CREATE TABLE `command` (
  `id` int(11) NOT NULL,
  `name` varchar(30) NOT NULL,
  `description` varchar(500) NOT NULL,
  `paramCount` int(11) NOT NULL,
  `infiniteParam` tinyint(1) NOT NULL,
  `admin` tinyint(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `command_metrics`
--

CREATE TABLE `command_metrics` (
  `commandFK` int(11) NOT NULL,
  `guildFK` bigint(11) NOT NULL,
  `userFK` bigint(11) NOT NULL,
  `callCount` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `default_role`
--

CREATE TABLE `default_role` (
  `guildFK` bigint(11) NOT NULL,
  `roleFK` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `global_settings`
--

CREATE TABLE `global_settings` (
  `id` int(11) NOT NULL,
  `name` varchar(50) NOT NULL,
  `value` varchar(500) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `guild`
--

CREATE TABLE `guild` (
  `snowflake` bigint(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `guild_settings`
--

CREATE TABLE `guild_settings` (
  `id` int(11) NOT NULL,
  `guildFK` bigint(11) NOT NULL,
  `name` varchar(50) NOT NULL,
  `value` varchar(200) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `localisation`
--

CREATE TABLE `localisation` (
  `language` varchar(10) NOT NULL,
  `key` varchar(50) NOT NULL,
  `value` varchar(1000) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `module_settings`
--

CREATE TABLE `module_settings` (
  `guildFK` bigint(11) NOT NULL,
  `commandFK` int(11) NOT NULL,
  `enabled` tinyint(1) NOT NULL,
  `roleFK` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `owner`
--

CREATE TABLE `owner` (
  `userFK` bigint(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `role`
--

CREATE TABLE `role` (
  `id` int(11) NOT NULL,
  `guildFK` bigint(11) NOT NULL,
  `parent` int(11) DEFAULT NULL,
  `name` varchar(30) NOT NULL,
  `description` varchar(500) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `role_member`
--

CREATE TABLE `role_member` (
  `snowflake` bigint(11) NOT NULL,
  `roleFK` int(11) NOT NULL,
  `snowflakeTypeFK` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `snowflake_type`
--

CREATE TABLE `snowflake_type` (
  `id` int(11) NOT NULL,
  `type` varchar(30) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `token`
--

CREATE TABLE `token` (
  `id` int(11) NOT NULL,
  `token` varchar(500) NOT NULL,
  `name` varchar(50) NOT NULL,
  `description` varchar(500) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

CREATE TABLE `user` (
  `snowflake` bigint(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `binding`
--
ALTER TABLE `binding`
  ADD PRIMARY KEY (`guildFK`,`bindingTypeFK`),
  ADD KEY `guildFK` (`guildFK`),
  ADD KEY `bindingTypeFK` (`bindingTypeFK`);

--
-- Indexes for table `binding_type`
--
ALTER TABLE `binding_type`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `command`
--
ALTER TABLE `command`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `command_metrics`
--
ALTER TABLE `command_metrics`
  ADD PRIMARY KEY (`commandFK`,`guildFK`,`userFK`),
  ADD KEY `commandFK` (`commandFK`),
  ADD KEY `guildFK` (`guildFK`),
  ADD KEY `userFK` (`userFK`);

--
-- Indexes for table `default_role`
--
ALTER TABLE `default_role`
  ADD PRIMARY KEY (`guildFK`,`roleFK`),
  ADD KEY `guildFK` (`guildFK`),
  ADD KEY `roleFK` (`roleFK`);

--
-- Indexes for table `global_settings`
--
ALTER TABLE `global_settings`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `guild`
--
ALTER TABLE `guild`
  ADD PRIMARY KEY (`snowflake`);

--
-- Indexes for table `guild_settings`
--
ALTER TABLE `guild_settings`
  ADD PRIMARY KEY (`id`),
  ADD KEY `guildFK` (`guildFK`);

--
-- Indexes for table `localisation`
--
ALTER TABLE `localisation`
  ADD PRIMARY KEY (`language`,`key`);

--
-- Indexes for table `module_settings`
--
ALTER TABLE `module_settings`
  ADD PRIMARY KEY (`guildFK`,`commandFK`),
  ADD KEY `guildFK` (`guildFK`),
  ADD KEY `commandFK` (`commandFK`),
  ADD KEY `roleFK` (`roleFK`);

--
-- Indexes for table `owner`
--
ALTER TABLE `owner`
  ADD PRIMARY KEY (`userFK`),
  ADD KEY `userFK` (`userFK`);

--
-- Indexes for table `role`
--
ALTER TABLE `role`
  ADD PRIMARY KEY (`id`),
  ADD KEY `guildFK` (`guildFK`),
  ADD KEY `parent` (`parent`);

--
-- Indexes for table `role_member`
--
ALTER TABLE `role_member`
  ADD PRIMARY KEY (`snowflake`,`roleFK`),
  ADD KEY `roleFK` (`roleFK`),
  ADD KEY `snowflakeTypeFK` (`snowflakeTypeFK`);

--
-- Indexes for table `snowflake_type`
--
ALTER TABLE `snowflake_type`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `token`
--
ALTER TABLE `token`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`snowflake`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `binding_type`
--
ALTER TABLE `binding_type`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `command`
--
ALTER TABLE `command`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `global_settings`
--
ALTER TABLE `global_settings`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `guild_settings`
--
ALTER TABLE `guild_settings`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `role`
--
ALTER TABLE `role`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `snowflake_type`
--
ALTER TABLE `snowflake_type`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `token`
--
ALTER TABLE `token`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `binding`
--
ALTER TABLE `binding`
  ADD CONSTRAINT `binding_ibfk_1` FOREIGN KEY (`guildFK`) REFERENCES `guild` (`snowflake`),
  ADD CONSTRAINT `binding_ibfk_2` FOREIGN KEY (`bindingTypeFK`) REFERENCES `binding_type` (`id`);

--
-- Constraints for table `command_metrics`
--
ALTER TABLE `command_metrics`
  ADD CONSTRAINT `command_metrics_ibfk_1` FOREIGN KEY (`commandFK`) REFERENCES `command` (`id`),
  ADD CONSTRAINT `command_metrics_ibfk_2` FOREIGN KEY (`guildFK`) REFERENCES `guild` (`snowflake`),
  ADD CONSTRAINT `command_metrics_ibfk_3` FOREIGN KEY (`userFK`) REFERENCES `user` (`snowflake`);

--
-- Constraints for table `default_role`
--
ALTER TABLE `default_role`
  ADD CONSTRAINT `default_role_ibfk_1` FOREIGN KEY (`roleFK`) REFERENCES `role` (`id`),
  ADD CONSTRAINT `default_role_ibfk_2` FOREIGN KEY (`guildFK`) REFERENCES `guild` (`snowflake`);

--
-- Constraints for table `guild_settings`
--
ALTER TABLE `guild_settings`
  ADD CONSTRAINT `guild_settings_ibfk_1` FOREIGN KEY (`guildFK`) REFERENCES `guild` (`snowflake`);

--
-- Constraints for table `module_settings`
--
ALTER TABLE `module_settings`
  ADD CONSTRAINT `module_settings_ibfk_1` FOREIGN KEY (`commandFK`) REFERENCES `command` (`id`),
  ADD CONSTRAINT `module_settings_ibfk_2` FOREIGN KEY (`guildFK`) REFERENCES `guild` (`snowflake`),
  ADD CONSTRAINT `module_settings_ibfk_3` FOREIGN KEY (`roleFK`) REFERENCES `role` (`id`);

--
-- Constraints for table `owner`
--
ALTER TABLE `owner`
  ADD CONSTRAINT `owner_ibfk_1` FOREIGN KEY (`userFK`) REFERENCES `user` (`snowflake`);

--
-- Constraints for table `role`
--
ALTER TABLE `role`
  ADD CONSTRAINT `role_ibfk_1` FOREIGN KEY (`guildFK`) REFERENCES `guild` (`snowflake`),
  ADD CONSTRAINT `role_ibfk_2` FOREIGN KEY (`parent`) REFERENCES `role` (`id`);

--
-- Constraints for table `role_member`
--
ALTER TABLE `role_member`
  ADD CONSTRAINT `role_member_ibfk_1` FOREIGN KEY (`roleFK`) REFERENCES `role` (`id`),
  ADD CONSTRAINT `role_member_ibfk_2` FOREIGN KEY (`snowflakeTypeFK`) REFERENCES `snowflake_type` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
