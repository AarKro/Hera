DELETE FROM `command` WHERE `name` = 'ALIAS';
DELETE FROM `localisation` WHERE `name` = 'COMMANDS_DESC_ALIAS';

INSERT INTO `localisation` (`language`, `name`, `value`)
	VALUES ('en', 'COMMAND_DESC_CREATEALIAS', 'Creates an alias for a command that works in only the current guild');
INSERT INTO `command` (`name`, `description`, `paramCount`, `optionalParams`, `level`, `minPermission`)
	VALUES ('CREATEALIAS', (SELECT `id` FROM `localisation` WHERE `name` = 'COMMAND_DESC_CREATEALIAS'), 2, 0, 1, 3072);

INSERT INTO `localisation` (`language`, `name`, `value`)
	VALUES ('en', 'COMMAND_DESC_SHOWALIAS', 'Shows all available aliases');
INSERT INTO `command` (`name`, `description`, `paramCount`, `optionalParams`, `level`, `minPermission`)
	VALUES ('SHOWALIAS', (SELECT `id` FROM `localisation` WHERE `name` = 'COMMAND_DESC_SHOWALIAS'), 0, 0, 0, 3072);

