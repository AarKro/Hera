INSERT INTO `snowflake_type` (`type`) VALUES ('GUILD');
INSERT INTO `snowflake_type` (`type`) VALUES ('CHANNEL');
INSERT INTO `snowflake_type` (`type`) VALUES ('USER');
INSERT INTO `snowflake_type` (`type`) VALUES ('ROLE');

INSERT INTO `user` (`id`) VALUES (178581372284305409);
INSERT INTO `user` (`id`) VALUES (245597003323670528);
INSERT INTO `user` (`id`) VALUES (248116143020048384);
INSERT INTO `user` (`id`) VALUES (340585395899203585);
INSERT INTO `user` (`id`) VALUES (442711068737929216);

INSERT INTO `owner` (`id`) VALUES (178581372284305409);
INSERT INTO `owner` (`id`) VALUES (245597003323670528);
INSERT INTO `owner` (`id`) VALUES (248116143020048384);
INSERT INTO `owner` (`id`) VALUES (340585395899203585);
INSERT INTO `owner` (`id`) VALUES (442711068737929216);

INSERT INTO `token` (`token`, `name`, `description`) VALUES ('#DISCORD_LOGIN_TOKEN', 'DISCORD_LOGIN', 'Discord bot token for Hera login');
INSERT INTO `token` (`token`, `name`, `description`) VALUES ('#YOUTUBE_API_TOKEN', 'YOUTUBE_API_TOKEN', 'YouTube API token for the YouTube Data v3 API');
INSERT INTO `token` (`token`, `name`, `description`) VALUES ('#YOUTUBE_API_APP_NAME', 'YOUTUBE_API_APP_NAME', 'YouTube API application name for the YouTube Data v3 API');

INSERT INTO `localisation` (`language`, `name`, `value`) VALUES ('en', 'COMMAND_UPTIME', 'I am up since %s');
INSERT INTO `localisation` (`language`, `name`, `value`) VALUES ('en', 'COMMAND_VERSION', 'I am running on version %s');
INSERT INTO `localisation` (`language`, `name`, `value`) VALUES ('en', 'COMMAND_HELP', 'Commands');
INSERT INTO `localisation` (`language`, `name`, `value`) VALUES ('en', 'COMMAND_HELP_MISSING_PERMISSION', 'Disabled because Hera is missing permissions');
INSERT INTO `localisation` (`language`, `name`, `value`) VALUES ('en', 'COMMAND_PREFIX', 'Prefix set to: %s');
INSERT INTO `localisation` (`language`, `name`, `value`) VALUES ('en', 'COMMAND_PLAY_TITLE', 'Added to queue');
INSERT INTO `localisation` (`language`, `name`, `value`) VALUES ('en', 'COMMAND_RESUMED', 'Player resumed');
INSERT INTO `localisation` (`language`, `name`, `value`) VALUES ('en', 'COMMAND_RESUMED_ERROR', 'Player is already resumed');
INSERT INTO `localisation` (`language`, `name`, `value`) VALUES ('en', 'COMMAND_PAUSED', 'Player paused');
INSERT INTO `localisation` (`language`, `name`, `value`) VALUES ('en', 'COMMAND_PAUSED_ERROR', 'Player is already paused');
INSERT INTO `localisation` (`language`, `name`, `value`) VALUES ('en', 'COMMAND_QUEUE_TITLE', 'Current queue');
INSERT INTO `localisation` (`language`, `name`, `value`) VALUES ('en', 'COMMAND_QUEUE_EMPTY', 'looks like it\'s empty');
INSERT INTO `localisation` (`language`, `name`, `value`) VALUES ('en', 'COMMAND_QUEUE_FOOTER', 'Page: %s of %s | Total songs: %s | Total duration: %s | Loop queue: %s');
INSERT INTO `localisation` (`language`, `name`, `value`) VALUES ('en', 'COMMON_ENABLED', 'enabled');
INSERT INTO `localisation` (`language`, `name`, `value`) VALUES ('en', 'COMMON_DISABLED', 'disabled');
INSERT INTO `localisation` (`language`, `name`, `value`) VALUES ('en', 'COMMAND_NOWPLAYING', 'Author: %s');
INSERT INTO `localisation` (`language`, `name`, `value`) VALUES ('en', 'COMMAND_NOWPLAYING_NO_SONG', 'No song is playing right now...');
INSERT INTO `localisation` (`language`, `name`, `value`) VALUES ('en', 'COMMAND_NOWPLAYING_TITLE', 'Now playing');
INSERT INTO `localisation` (`language`, `name`, `value`) VALUES ('en', 'COMMAND_JOIN', 'You need to be in a voice channel to use this command');
INSERT INTO `localisation` (`language`, `name`, `value`) VALUES ('en', 'COMMAND_LOOPQUEUE', 'Loop queue %s');
INSERT INTO `localisation` (`language`, `name`, `value`) VALUES ('en', 'COMMAND_VOLUME', 'Volume set to %d');
INSERT INTO `localisation` (`language`, `name`, `value`) VALUES ('en', 'COMMAND_VOLUME_ERROR', 'Volume can only be set to a number between 0 and 100');
INSERT INTO `localisation` (`language`, `name`, `value`) VALUES ('en', 'COMMAND_VOLUME_MUTE', 'I am now muted');
INSERT INTO `localisation` (`language`, `name`, `value`) VALUES ('en', 'PLAYLIST_LOADED', 'Total songs: %s | Total duration: %s');
INSERT INTO `localisation` (`language`, `name`, `value`) VALUES ('en', 'COMMAND_CLEAR', 'Queue cleared');
INSERT INTO `localisation` (`language`, `name`, `value`) VALUES ('en', 'COMMAND_TOGGLE_ON', 'Command %s is now on');
INSERT INTO `localisation` (`language`, `name`, `value`) VALUES ('en', 'COMMAND_TOGGLE_OFF', 'Command %s is now off');
INSERT INTO `localisation` (`language`, `name`, `value`) VALUES ('en', 'COMMAND_FLIP_HEADS', 'Heads');
INSERT INTO `localisation` (`language`, `name`, `value`) VALUES ('en', 'COMMAND_FLIP_TAILS', 'Tails');
INSERT INTO `localisation` (`language`, `name`, `value`) VALUES ('en', 'ERROR_NOT_REAL_COMMAND', 'Command %s doesn\'t exist');
INSERT INTO `localisation` (`language`, `name`, `value`) VALUES ('en', 'COMMAND_SHUFFLE', 'Queue shuffled');
INSERT INTO `localisation` (`language`, `name`, `value`) VALUES ('en', 'COMMAND_TEAM_TEAM', 'Team');
INSERT INTO `localisation` (`language`, `name`, `value`) VALUES ('en', 'COMMAND_REMOVE', 'Removed from queue');
INSERT INTO `localisation` (`language`, `name`, `value`) VALUES ('en', 'COMMAND_REMOVE_ERROR', 'There is nothing at queue index %s');
INSERT INTO `localisation` (`language`, `name`, `value`) VALUES ('en', 'COMMAND_MOVE', 'Moved %s to %s');
INSERT INTO `localisation` (`language`, `name`, `value`) VALUES ('en', 'COMMAND_MOVE_ERROR', 'Could not move a song with indexes %s and %s');
INSERT INTO `localisation` (`language`, `name`, `value`) VALUES ('en', 'COMMAND_JUMPTO', 'Jumped to %s');
INSERT INTO `localisation` (`language`, `name`, `value`) VALUES ('en', 'COMMAND_JUMPTO_ERROR', 'Can not jump to queue index %s');
INSERT INTO `localisation` (`language`, `name`, `value`) VALUES ('en', 'COMMAND_ON_JOIN_ROLE', 'On join role set to %s');
INSERT INTO `localisation` (`language`, `name`, `value`) VALUES ('en', 'COMMAND_ON_JOIN_ROLE_ERROR', 'Can\'t set on join role to %s');
INSERT INTO `localisation` (`language`, `name`, `value`) VALUES ('en', 'COMMAND_VOTE_START_TITLE', '%s started a vote');
INSERT INTO `localisation` (`language`, `name`, `value`) VALUES ('en', 'COMMAND_VOTE_START_FOOTER', '%s, react with %s to end the vote');
INSERT INTO `localisation` (`language`, `name`, `value`) VALUES ('en', 'COMMAND_VOTE_END_TITLE', 'Vote results');
INSERT INTO `localisation` (`language`, `name`, `value`) VALUES ('en', 'COMMAND_VOTE_END_DESC', '> %s\n\nYes: %s | %s%%\nNo: %s | %s%%\n\nTotal votes: %s');
INSERT INTO `localisation` (`language`, `name`, `value`) VALUES ('en', 'BINDING_MUSIC', 'Binded channel for music');
INSERT INTO `localisation` (`language`, `name`, `value`) VALUES ('en', 'BINDING_ANNOUNCEMENT', 'Binded channel for announcements');
INSERT INTO `localisation` (`language`, `name`, `value`) VALUES ('en', 'BINDING_REPORT', 'Binded channel for reports');
INSERT INTO `localisation` (`language`, `name`, `value`) VALUES ('en', 'BINDING_ERROR_CHANNEL', '%s is not a valid channel mention');
INSERT INTO `localisation` (`language`, `name`, `value`) VALUES ('en', 'COMMAND_DESC_UPTIME', 'Checks how long Hera has been up and running');
INSERT INTO `localisation` (`language`, `name`, `value`) VALUES ('en', 'COMMAND_DESC_VERSION', 'Displays current version number');
INSERT INTO `localisation` (`language`, `name`, `value`) VALUES ('en', 'COMMAND_DESC_HELP', 'Shows this Help page');
INSERT INTO `localisation` (`language`, `name`, `value`) VALUES ('en', 'COMMAND_DESC_DELETEMESSAGES', 'Deletes the newest channel messages');
INSERT INTO `localisation` (`language`, `name`, `value`) VALUES ('en', 'COMMAND_DESC_ALIAS', 'Creates an alias for a command that works in only the current guild');
INSERT INTO `localisation` (`language`, `name`, `value`) VALUES ('en', 'COMMAND_DESC_PREFIX', 'Sets command prefix');
INSERT INTO `localisation` (`language`, `name`, `value`) VALUES ('en', 'COMMAND_DESC_JOIN', 'Makes Hera join the voice channel you are currently connected to');
INSERT INTO `localisation` (`language`, `name`, `value`) VALUES ('en', 'COMMAND_DESC_LEAVE', 'Makes Hera leave the voice channel she is currently connected to');
INSERT INTO `localisation` (`language`, `name`, `value`) VALUES ('en', 'COMMAND_DESC_PLAY', 'Plays/Queues a song from a link / a few keywords to search on YouTube');
INSERT INTO `localisation` (`language`, `name`, `value`) VALUES ('en', 'COMMAND_DESC_QUEUE', 'Display the songs currently in the queue');
INSERT INTO `localisation` (`language`, `name`, `value`) VALUES ('en', 'COMMAND_DESC_SKIP', 'Skip the current song');
INSERT INTO `localisation` (`language`, `name`, `value`) VALUES ('en', 'COMMAND_DESC_LOOPQUEUE', 'Toggle loop queue mode');
INSERT INTO `localisation` (`language`, `name`, `value`) VALUES ('en', 'COMMAND_DESC_NOWPLAYING', 'Display the currently playing song');
INSERT INTO `localisation` (`language`, `name`, `value`) VALUES ('en', 'COMMAND_DESC_CLEAR', 'Clear the music queue');
INSERT INTO `localisation` (`language`, `name`, `value`) VALUES ('en', 'COMMAND_DESC_RESUME', 'Resume the music player');
INSERT INTO `localisation` (`language`, `name`, `value`) VALUES ('en', 'COMMAND_DESC_PAUSE', 'Pause the music player');
INSERT INTO `localisation` (`language`, `name`, `value`) VALUES ('en', 'COMMAND_DESC_VOLUME', 'Sets volume for music player');
INSERT INTO `localisation` (`language`, `name`, `value`) VALUES ('en', 'COMMAND_DESC_TOGGLECOMMAND', 'Toggles if a command is enabled or not');
INSERT INTO `localisation` (`language`, `name`, `value`) VALUES ('en', 'COMMAND_DESC_SHUFFLE', 'Shuffles the queue');
INSERT INTO `localisation` (`language`, `name`, `value`) VALUES ('en', 'COMMAND_DESC_REMOVE', 'Removes a song from the queue');
INSERT INTO `localisation` (`language`, `name`, `value`) VALUES ('en', 'COMMAND_DESC_MOVE', 'Move a song to a new queue index');
INSERT INTO `localisation` (`language`, `name`, `value`) VALUES ('en', 'COMMAND_DESC_JUMPTO', 'Jump to an index of the queue');
INSERT INTO `localisation` (`language`, `name`, `value`) VALUES ('en', 'COMMAND_DESC_ONJOINROLE', 'Sets a role to use on join');
INSERT INTO `localisation` (`language`, `name`, `value`) VALUES ('en', 'COMMAND_DESC_FLIP', 'Flips a coin');
INSERT INTO `localisation` (`language`, `name`, `value`) VALUES ('en', 'COMMAND_DESC_TEAMS', 'Makes teams from the specified parameters');
INSERT INTO `localisation` (`language`, `name`, `value`) VALUES ('en', 'COMMAND_DESC_UPDATEYTTOKEN', 'Sets the youtube tokens to a new value');
INSERT INTO `localisation` (`language`, `name`, `value`) VALUES ('en', 'COMMAND_DESC_VOTE', 'Start a guild wide vote');
INSERT INTO `localisation` (`language`, `name`, `value`) VALUES ('en', 'COMMAND_DESC_BIND', 'Bind a message output group to a channel');

INSERT INTO `command` (`name`, `description`, `paramCount`, `optionalParams`, `level`, `minPermission`) VALUES ('UPTIME', (SELECT `id` FROM `localisation` WHERE `name` = 'COMMAND_DESC_UPTIME'), 0, 0, 0, 3072);
INSERT INTO `command` (`name`, `description`, `paramCount`, `optionalParams`, `level`, `minPermission`) VALUES ('VERSION', (SELECT `id` FROM `localisation` WHERE `name` = 'COMMAND_DESC_VERSION'), 0, 0, 0, 3072);
INSERT INTO `command` (`name`, `description`, `paramCount`, `optionalParams`, `level`, `minPermission`) VALUES ('HELP', (SELECT `id` FROM `localisation` WHERE `name` = 'COMMAND_DESC_HELP'), 0, 1, 0, 3072);
INSERT INTO `command` (`name`, `description`, `paramCount`, `optionalParams`, `level`, `minPermission`) VALUES ('DELETEMESSAGES', (SELECT `id` FROM `localisation` WHERE `name` = 'COMMAND_DESC_DELETEMESSAGES'), 1, 0, 1, 11264);
INSERT INTO `command` (`name`, `description`, `paramCount`, `optionalParams`, `level`, `minPermission`) VALUES ('ALIAS', (SELECT `id` FROM `localisation` WHERE `name` = 'COMMAND_DESC_ALIAS'), 2, 0, 1, 3072);
INSERT INTO `command` (`name`, `description`, `paramCount`, `optionalParams`, `level`, `minPermission`) VALUES ('PREFIX', (SELECT `id` FROM `localisation` WHERE `name` = 'COMMAND_DESC_PREFIX'), 1, 0, 1, 3072);
INSERT INTO `command` (`name`, `description`, `paramCount`, `optionalParams`, `level`, `minPermission`) VALUES ('JOIN', (SELECT `id` FROM `localisation` WHERE `name` = 'COMMAND_DESC_JOIN'), 0, 0, 0, 1051648);
INSERT INTO `command` (`name`, `description`, `paramCount`, `optionalParams`, `level`, `minPermission`) VALUES ('LEAVE', (SELECT `id` FROM `localisation` WHERE `name` = 'COMMAND_DESC_LEAVE'), 0, 0, 0, 1051648);
INSERT INTO `command` (`name`, `description`, `paramCount`, `optionalParams`, `level`, `minPermission`) VALUES ('PLAY', (SELECT `id` FROM `localisation` WHERE `name` = 'COMMAND_DESC_PLAY'), 1, -1, 0, 3148800);
INSERT INTO `command` (`name`, `description`, `paramCount`, `optionalParams`, `level`, `minPermission`) VALUES ('QUEUE', (SELECT `id` FROM `localisation` WHERE `name` = 'COMMAND_DESC_QUEUE'), 0, 0, 0, 3136);
INSERT INTO `command` (`name`, `description`, `paramCount`, `optionalParams`, `level`, `minPermission`) VALUES ('SKIP', (SELECT `id` FROM `localisation` WHERE `name` = 'COMMAND_DESC_SKIP'), 0, 0, 0, 3072);
INSERT INTO `command` (`name`, `description`, `paramCount`, `optionalParams`, `level`, `minPermission`) VALUES ('LOOPQUEUE', (SELECT `id` FROM `localisation` WHERE `name` = 'COMMAND_DESC_LOOPQUEUE'), 0, 0, 0, 3072);
INSERT INTO `command` (`name`, `description`, `paramCount`, `optionalParams`, `level`, `minPermission`) VALUES ('NOWPLAYING', (SELECT `id` FROM `localisation` WHERE `name` = 'COMMAND_DESC_NOWPLAYING'), 0, 0, 0, 3072);
INSERT INTO `command` (`name`, `description`, `paramCount`, `optionalParams`, `level`, `minPermission`) VALUES ('CLEAR', (SELECT `id` FROM `localisation` WHERE `name` = 'COMMAND_DESC_CLEAR'), 0, 0, 0, 3072);
INSERT INTO `command` (`name`, `description`, `paramCount`, `optionalParams`, `level`, `minPermission`) VALUES ('RESUME', (SELECT `id` FROM `localisation` WHERE `name` = 'COMMAND_DESC_RESUME'), 0, 0, 0, 3072);
INSERT INTO `command` (`name`, `description`, `paramCount`, `optionalParams`, `level`, `minPermission`) VALUES ('PAUSE', (SELECT `id` FROM `localisation` WHERE `name` = 'COMMAND_DESC_PAUSE'), 0, 0, 0, 3072);
INSERT INTO `command` (`name`, `description`, `paramCount`, `optionalParams`, `level`, `minPermission`) VALUES ('VOLUME', (SELECT `id` FROM `localisation` WHERE `name` = 'COMMAND_DESC_VOLUME'), 1, 0, 1, 3072);
INSERT INTO `command` (`name`, `description`, `paramCount`, `optionalParams`, `level`, `minPermission`) VALUES ('TOGGLECOMMAND', (SELECT `id` FROM `localisation` WHERE `name` = 'COMMAND_DESC_TOGGLECOMMAND'), 1, 0, 1, 3072);
INSERT INTO `command` (`name`, `description`, `paramCount`, `optionalParams`, `level`, `minPermission`) VALUES ('SHUFFLE', (SELECT `id` FROM `localisation` WHERE `name` = 'COMMAND_DESC_SHUFFLE'), 0, 0, 0, 3072);
INSERT INTO `command` (`name`, `description`, `paramCount`, `optionalParams`, `level`, `minPermission`) VALUES ('REMOVE', (SELECT `id` FROM `localisation` WHERE `name` = 'COMMAND_DESC_REMOVE'), 1, 0, 0, 3072);
INSERT INTO `command` (`name`, `description`, `paramCount`, `optionalParams`, `level`, `minPermission`) VALUES ('MOVE', (SELECT `id` FROM `localisation` WHERE `name` = 'COMMAND_DESC_MOVE'), 2, 0, 0, 3072);
INSERT INTO `command` (`name`, `description`, `paramCount`, `optionalParams`, `level`, `minPermission`) VALUES ('JUMPTO', (SELECT `id` FROM `localisation` WHERE `name` = 'COMMAND_DESC_JUMPTO'), 1, 0, 0, 3072);
INSERT INTO `command` (`name`, `description`, `paramCount`, `optionalParams`, `level`, `minPermission`) VALUES ('ONJOINROLE', (SELECT `id` FROM `localisation` WHERE `name` = 'COMMAND_DESC_ONJOINROLE'), 1, 0, 1, 268438528);
INSERT INTO `command` (`name`, `description`, `paramCount`, `optionalParams`, `level`, `minPermission`) VALUES ('FLIP', (SELECT `id` FROM `localisation` WHERE `name` = 'COMMAND_DESC_FLIP'), 0, 0, 0, 3072);
INSERT INTO `command` (`name`, `description`, `paramCount`, `optionalParams`, `level`, `minPermission`) VALUES ('TEAMS', (SELECT `id` FROM `localisation` WHERE `name` = 'COMMAND_DESC_TEAMS'), 3, -1, 0, 3072);
INSERT INTO `command` (`name`, `description`, `paramCount`, `optionalParams`, `level`, `minPermission`) VALUES ('UPDATEYTTOKEN', (SELECT `id` FROM `localisation` WHERE `name` = 'COMMAND_DESC_UPDATEYTTOKEN'), 2, 0, 2, 3072);
INSERT INTO `command` (`name`, `description`, `paramCount`, `optionalParams`, `level`, `minPermission`) VALUES ('VOTE', (SELECT `id` FROM `localisation` WHERE `name` = 'COMMAND_DESC_VOTE'), 1, -1, 0, 3136);
INSERT INTO `command` (`name`, `description`, `paramCount`, `optionalParams`, `level`, `minPermission`) VALUES ('BIND', (SELECT `id` FROM `localisation` WHERE `name` = 'COMMAND_DESC_BIND'), 2, 0, 1, 3072);

INSERT INTO `global_setting` (`name`, `value`) VALUES ('VERSION', '2.0.0-alpha.3');

INSERT INTO `alias` (`commandFK`, `alias`) VALUES ((SELECT `id` FROM `command` WHERE `name` = 'PLAY'), 'P');
INSERT INTO `alias` (`commandFK`, `alias`) VALUES ((SELECT `id` FROM `command` WHERE `name` = 'QUEUE'), 'Q');
INSERT INTO `alias` (`commandFK`, `alias`) VALUES ((SELECT `id` FROM `command` WHERE `name` = 'NOWPLAYING'), 'NP');
INSERT INTO `alias` (`commandFK`, `alias`) VALUES ((SELECT `id` FROM `command` WHERE `name` = 'LOOPQUEUE'), 'LQ');
INSERT INTO `alias` (`commandFK`, `alias`) VALUES ((SELECT `id` FROM `command` WHERE `name` = 'CLEAR'), 'CLR');
INSERT INTO `alias` (`commandFK`, `alias`) VALUES ((SELECT `id` FROM `command` WHERE `name` = 'DELETEMESSAGES'), 'DELMSG');

INSERT INTO `binding_type` (`type`, `localisationFK`, `isGlobal`) VALUES ('MUSIC', (SELECT `id` FROM `localisation` WHERE `name` = 'BINDING_MUSIC'), 0);
INSERT INTO `binding_type` (`type`, `localisationFK`, `isGlobal`) VALUES ('ANNOUNCEMENT', (SELECT `id` FROM `localisation` WHERE `name` = 'BINDING_ANNOUNCEMENT'), 0);
INSERT INTO `binding_type` (`type`, `localisationFK`, `isGlobal`) VALUES ('REPORT', (SELECT `id` FROM `localisation` WHERE `name` = 'BINDING_REPORT'), 0);
