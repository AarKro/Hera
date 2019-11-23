INSERT INTO `binding_type` (`id`, `type`) VALUES
(1, 'Music'),
(2, 'Announcement'),
(3, 'Report');

INSERT INTO `snowflake_type` (`id`, `type`) VALUES
(1, 'Guild'),
(2, 'Channel'),
(3, 'User');

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
(1, 'uptime', 'Checks how long Hera has been up and running', 0, 0, 1);

INSERT INTO `token` (`id`, `token`, `name`, `description`) VALUES
(1, '#DISCORD_LOGIN_TOKEN', 'discord_login', 'Discord bot token for Hera login');

INSERT INTO `localisation` (`language`, `key`, `value`) VALUES
('en', 'command_uptime', 'I am up and running since the last %s');