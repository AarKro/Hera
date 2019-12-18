INSERT INTO `binding_type` (`id`, `type`) VALUES
(0, 'Music'),
(1, 'Announcement'),
(2, 'Report');

INSERT INTO `snowflake_type` (`id`, `type`) VALUES
(0, 'Guild'),
(1, 'Channel'),
(2, 'User');

INSERT INTO `owner` (`userFK`) VALUES
(178581372284305409),
(245597003323670528),
(248116143020048384),
(442711068737929216);

INSERT INTO `user` (`snowflake`) VALUES
(178581372284305409),
(245597003323670528),
(248116143020048384),
(442711068737929216);

INSERT INTO `command` (`id`, `name`, `description`, `paramCount`, `infiniteParam`, `admin`) VALUES
(0, 'UPTIME', 'Checks how long Hera has been up and running', 0, 0, 1),
(1, 'VERSION', 'Displays current version number', 0, 0, 0),
(2, 'HELP', 'Shows this Help page.', 0, 0, 0);
(3, 'DELETEMESSAGES', 'Deletes the newest channel messages.', 1, 0, 1);
(4, 'ALIAS', 'Creates an alias for a command that works in only the current guild.', 2, 0, 1);
(5, 'PREFIX', 'Sets command prefix.', 1, 0, 1);

INSERT INTO `token` (`id`, `token`, `name`, `description`) VALUES
(0, '#DISCORD_LOGIN_TOKEN', 'DISCORD_LOGIN', 'Discord bot token for Hera login');

INSERT INTO `localisation` (`language`, `name`, `value`) VALUES
('en', 'COMMAND_UPTIME', 'I am up and running for the last %s'),
('en', 'COMMAND_VERSION', 'I am running on version %s'),
('en', 'COMMAND_HELP', 'Commands');
('en', 'COMMAND_PREFIX', 'Prefix set to: %s');

INSERT INTO `global_settings` (`id`, `name`, `value`) VALUES
(0, 'VERSION', '2.0.0-alpha.0');

INSERT INTO `alias` (`id`, `commandFK`, `alias`) VALUES
(0, 1, 'vers');