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
(0, 'UPTIME', 'Checks how long Hera has been up and running', 0, 0, 0),
(1, 'VERSION', 'Displays current version number', 0, 0, 0),
(2, 'HELP', 'Shows this Help page.', 0, 0, 0),
(3, 'DELETEMESSAGES', 'Deletes the newest channel messages.', 1, 0, 1),
(4, 'ALIAS', 'Creates an alias for a command that works in only the current guild.', 2, 0, 1),
(5, 'PREFIX', 'Sets command prefix.', 1, 0, 1),
(6, 'JOIN', 'Makes Hera join the voice channel you are currently connected to', 0, 0, 0),
(7, 'LEAVE', 'Makes Hera leave the voice channel she is currently connected to', 0, 0, 0),
(8, 'PLAY', 'Plays/Queues a song from a link / a few keywords to search on YouTube', 1, 0, 0),
(9, 'QUEUE', 'Display the songs currently in the queue', 0, 0, 0),
(10, 'SKIP', 'Skip the current song', 0, 0, 0),
(11, 'LOOPQUEUE', 'Toggle loop queue mode', 0, 0, 0),
(12, 'NOWPLAYING', 'Display the currently playing song', 0, 0, 0),
(13, 'CLEAR', 'Clear the music queue', 0, 0, 0),
(14, 'RESUME', 'Resume the music player', 0, 0, 0),
(15, 'PAUSE', 'Pause the music player', 0, 0, 0),
(16, 'VOLUME', 'Sets volume for music player', 1, 0, 1),
(17, 'TOGGLECOMMAND', 'Toggles if a command is enabled or not', 1, 0, 1);
-- TODO: Change paramCount of PLAY command so it can accept multiple keywords when YouTube integration is implemented

INSERT INTO `token` (`id`, `token`, `name`, `description`) VALUES
(0, '#DISCORD_LOGIN_TOKEN', 'DISCORD_LOGIN', 'Discord bot token for Hera login');

INSERT INTO `localisation` (`language`, `name`, `value`) VALUES
('en', 'COMMAND_UPTIME', 'I am up and running for the last %s'),
('en', 'COMMAND_VERSION', 'I am running on version %s'),
('en', 'COMMAND_HELP', 'Commands'),
('en', 'COMMAND_PREFIX', 'Prefix set to: %s'),
('en', 'COMMAND_PLAY_TITLE', 'Add to queue'),
('en', 'COMMAND_RESUMED', 'Player resumed'),
('en', 'COMMAND_RESUMED_ERROR', 'Player is already resumed'),
('en', 'COMMAND_PAUSED', 'Player paused'),
('en', 'COMMAND_PAUSED_ERROR', 'Player is already paused'),
('en', 'COMMAND_QUEUE_TITLE', 'Current queue'),
('en', 'COMMAND_QUEUE_EMPTY', 'looks like it\'s empty'),
('en', 'COMMAND_QUEUE_FOOTER', 'Page: %s of %s | Total songs: %s | Total duration: %s | Loop queue: %s'),
('en', 'COMMON_ENABLED', 'enabled'),
('en', 'COMMON_DISABLED', 'disabled'),
('en', 'COMMAND_NOWPLAYING', 'Author: %s'),
('en', 'COMMAND_NOWPLAYING_NO_SONG', 'No song is playing right now...'),
('en', 'COMMAND_NOWPLAYING_TITLE', 'Now playing'),
('en', 'COMMAND_JOIN', 'You need to be in a voice channel to use this command'),
('en', 'COMMAND_LOOPQUEUE', 'Loop queue %s'),
('en', 'COMMAND_VOLUME', 'Volume set to %d'),
('en', 'PLAYLIST_LOADED', 'Total songs: %s | Total duration: %s'),
('en', 'COMMAND_CLEAR', 'Queue cleared'),
('en', 'COMMAND_TOGGLE_ON', 'Command %s is now on.'),
('en', 'COMMAND_TOGGLE_OFF', 'Command %s is now off.'),
('en', 'ERROR_NOT_REAL_COMMAND', 'Command %s doesn\'t exist.');

INSERT INTO `global_setting` (`id`, `name`, `value`) VALUES
(0, 'VERSION', '2.0.0-alpha.0');

INSERT INTO `alias` (`id`, `commandFK`, `alias`) VALUES
(1, 8, 'p'),
(2, 9, 'q'),
(3, 12, 'np'),
(4, 11, 'lq'),
(5, 13, 'clr'),
(6, 3, 'delmsg');