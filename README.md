<h1>Hera</h1>

Bot-prefix: ```$``` | Version ```v1.0.0```

Hera is a Discord chatbot written in Java using the Discord4Java library.  
She is programmed as an all-around Discord bot, thus finds its functionality in various areas.

Some of these areas are:  
* **Discord guild management**  
Hera supports auto role assign on join and a message of the day functionality.
* **Guild member management**  
Report, timeout or randomly move other users with ```$report```, ```$shame``` & ```$begone```.
* **Music player functionality**  
Queue your favorite songs via ```URL``` or just give Hera a few keywords to search with on YouTube.
* **General convenience**  
Use various little commands such as ```$flip``` to flip a coin, or ```$vote``` to start a guild wide vote.

<h2>Full command list</h2>  

<h3>Begone</h3>

Moves another user to a random voice channel, for which they have the right permissions for.

Can only be used by Admins and the role BeGone.  
Syntax: ```$begone <username | usernickname>```

<h3>Bind</h3>

Binds a channel to a specific message output group from Hera.  
For example: ```$bind music``` binds all of Heras output messages regarding music to the channel in which the command was written in.

Output message groups:  
* ```report```
* ```music```
* ```announcements```

Can only be used by Admins.  
Syntax: ```$bind <output message group>```

<h3>Clear</h3>

Clears all songs from the current music queue.

Can be used by all guild members.  
Syntax: ```$clear```

<h3>End</h3>

Ends an ongoing vote.

Can only be used by Admins and the user who started the vote.  
Syntax: ```$end```

<h3>Flip</h3>

This command simulates a coin flip. The outcome of it is randomly chosen between ```Heads``` & ```Tails```.

Can be used by all guild members.  
Syntax: ```$flip```

<h3>Help</h3>

Displays a list of all available commands and a link to https://chromeroni.github.io/Hera-Chatbot/ for more information.

Can be used by all guild members.  
Syntax: ```$help```

<h3>Join</h3>

Hera joins the voice channel to which the user who issued the command is connected.

Can be used by all guild members.  
Syntax: ```$join```

<h3>Leave</h3>

Hera leaves the voice channel to which she is currently connected.

Can be used by all guild members.  
Syntax: ```$leave```

<h3>Lq</h3>

Loops the music queue, so that at the end of a song it is simply requeued.

Can be used by all guild members.  
Syntax: ```$lq```

<h3>Motd</h3>

Lets you choose your own message of the day and override the current one.

Can only be used by Admins.  
Syntax: ```$motd <message of the day>```

<h3>Move</h3>

Move a queued song to another position in the queue. To identify a song and the position to which it should be moved, use song IDs. Song IDs can be displayed through ```$queue```.

Can be used by all guild members.  
Syntax: ```$move <song ID> <song ID>```

<h3>No</h3>

Vote no on the currently active vote.

Can be used by all guild members.  
Syntax: ```$no```

<h3>Np</h3>

Display the currently playing song.

Can be used by all guild members.  
Syntax: ```$np```

<h3>Pause</h3>

Pauses the music player.

Can be used by all guild members.  
Syntax: ```$pause```

<h3>Play</h3>

Queues a song through an ```URL``` or a few keywords with which Hera will search and queue the first search result on YouTube.  
If there is no song playing at the moment, play will start the player by itself.  
If Hera has not join a voice channel yet, she will automatically join the voice channel of the user who issued the command.

Can be used by all guild members.  
Syntax: ```$play <url | keywords>```

<h3>Queue</h3>

Displays all song that are in the music queue. If the message to display the queue exeeds the discord message character limit of 2000, a compact version of the message will be dispalyed instead.  
If the message still exeeds the character limit, only the first 5 song will be displayed in normal view.

Can be used by all guild members.  
Syntax: ```$queue```

<h3>Remove</h3>

Removes a song from the music queue. To identify the song which should be removed, use song IDs. Song IDs can be displayed through ```$queue```.

Can be used by all guild members.  
Syntax: ```$remove <song ID>```

<h3>Replay</h3>

Requeues the currently playing song.

Can be used by all guild members.  
Syntax: ```$replay```

<h3>Report</h3>

Reports the specified user to the Admins.

Can be used by all guild members.  
Syntax: ```$report <@user> <report message>```

<h3>Resume</h3>

Resumes the music player.

Can be used by all guild members.  
Syntax: ```$resume```

<h3>Shame</h3>

Removes the casual role from the specified user and replaces it with the "schäm dich" role. Also moves the user to the "schämdicheggli" voice channel for a by the Admins specified time period.

Can only be used by Admins.  
Syntax: ```$shame <username | usernickname>```

<h3>Skip</h3>

Skips the currently playing song and starts the next one in the queue.

Can be used by all guild members.  
Syntax: ```$skip```

<h3>Teams</h3>

Creates two teams of its parameters. There need to be at least 3 parameters to use this command.

Can be used by all guild members.  
Syntax: ```$teams <name> <name> <name> ...```

<h3>Version</h3>

Displays the current version of Hera.

Can be used by all guild members.  
Syntax: ```$version```

<h3>Volume</h3>

Sets the volume of the music player. Valid values are numbers between 0 and 150.

Can only be used by Admins.  
Syntax: ```$volume <value>```

<h3>Vote</h3>

Starts a vote. Only 1 vote can be active at a time. All guild members can vote 1 times on a vote.

Can be used by all guild members.  
Syntax: ```$vote <vote topic>```

<h3>Yes</h3>

Vote yes on the currently active vote.

Can be used by all guild members.  
Syntax: ```$yes```

<h2>Property files</h2>
Hera reads various property files on startup. In them are values stored, which are relevant on runtime.  
The property files also get updated automatically by Hera, if such a command is issued.

Example values that are stored in property files are channel IDs for the bound channels or when the last message of the day was posted. This way channels don't have to be rebound on every startup and the message of the day is only posted once a day.

Property files Hera uses:  
* binding.properties
* settings.properties
* client.properties
* youtube.properties

<h2>License</h2>
This project is licensed under the open-source license [Apache License, Version 2.0](https://opensource.org/licenses/Apache-2.0).
