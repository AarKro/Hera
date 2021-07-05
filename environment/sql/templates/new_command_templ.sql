--	These two lines will be needed every time you add a new command.
--	AuthLevel can be
--		0 -> Normal
-- 		1 -> Moderator
--		2 -> Owner
--	For the basePermission value you can use https://discordapi.com/permissions.html
--	Common values are:
-- 		3072 -> read and write messages
--		3136 -> read, write and react to messages
-- !!!IMPORTANT!!! ALWAYS MAKE SURE THE DESCRIPTION IS ABOVE THE COMMAND INSERT OTHERWISE THE NESTED SQL WILL FAIL
INSERT INTO `localisation` (`language`, `name`, `value`)
	VALUES ('en', 'COMMAND_DESC_$name$', '$decription$');
INSERT INTO `command` (`name`, `description`, `paramCount`, `optionalParams`, `level`, `minPermission`)
	VALUES ('$name$', (SELECT `id` FROM `localisation` WHERE `name` = 'COMMAND_DESC_$name$'), $neededParams$, $optionalParams$, $authLevel$, $basePermission$);

-- Make new localisation, self explanatory
INSERT INTO `localisation` (`language`, `name`, `value`) VALUES ('en', '$fieldName$', '$output$');

-- Make an alias