<h1>Hera</h1>

Latest version [`v1.0.0`](https://github.com/Chromeroni/Hera-Chatbot/releases/latest)| Default bot-prefix: `$`

Hera is a Discord chatbot written in Java using the Discord4Java library.  
She is programmed as an all-around Discord bot, thus finds its functionality in various areas.

Some of these areas are:  
* **Discord guild management**  
Hera supports auto role assign on join and a message of the day functionality.
* **Guild member management**  
Report, timeout or randomly move other users with `$report`, `$shame` & `$begone`.
* **Music player functionality**  
Queue your favorite songs via `URL` or just give Hera a few keywords to search with on YouTube.
* **General convenience**  
Use various little commands such as `$flip` to flip a coin, or `$vote` to start a guild wide vote.

<h2>Commands</h2>  

<h3>Short command listing</h3>

| Command | Usable for | Quick syntax |
| ------- | ---------- | ------------ |
| [Alias](#alias)| Admins only | `$alias <command> <alias>` |
| [Begone](#begone) | Admins and role BeGone | `$begone <username \| usernickname>` |
| [Bind](#bind) | Admins only | `$bind <output message group>` |
| [Clear](#clear) | All users | `$clear` |
| [End](#end) | All users | `$end` |
| [Flip](#flip) | All users | `$flip` |
| [Help](#help) | All users | `$help` |
| [Join](#join) | All users | `$join` |
| [Leave](#leave) | All users | `$leave` |
| [Lq](#lq) | All users | `$lq` |
| [Motd](#motd) | Admins only | `$motd <message of the day>` |
| [Move](#move) | All users | `$move <song ID> <song ID>` |
| [No](#no) | All users | `$no` |
| [Np](#np) | All users | `$np` |
| [Pause](#pause) | All users | `$pause` |
| [Play](#play) | All users | `$play <url \| keywords>` |
| [Queue](#queue) | All users | `$queue` |
| [Remove](#remove) | All users | `$remove <song ID>` |
| [Replay](#replay) | All users | `$replay` |
| [Report](#report) | All users | `$report <@user> <report message>` |
| [Resume](#resume) | All users | `$resume` |
| [Shame](#shame) | Admins only | `$shame <username \| usernickname>` |
| [Skip](#skip) | All users | `$skip` |
| [Teams](#teams) | All users | `$teams <name> <name> <name> ...` |
| [Version](#version) | All users | `$version` |
| [Volume](#volume) | Admins only | `$volume <value>` |
| [Vote](#vote) | All users | `$vote <vote topic>` |
| [Yes](#yes) | All users | `$yes` |

<h3>Detailed command listing</h3>

<h4>Alias</h4>

Creates an alias for a command. The command can now be used with both, its alias or with its original name.

Can only be used by Admins.  
Syntax: `$alias <command> <alias>`

<h4>Begone</h4>

Moves another user to a random voice channel, for which they have the right permissions for.

Can only be used by Admins and the role BeGone.  
Syntax: `$begone <username | usernickname>`

<h4>Bind</h4>

Binds a channel to a specific output message group from Hera.  
For example: `$bind music` binds all of Heras output messages regarding music to the channel in which the command was written in.

Output message groups:  
* `report`
* `music`
* `announcements`

Can only be used by Admins.  
Syntax: `$bind <output message group>`

<h4>Clear</h4>

Clears all songs from the current music queue.

Can be used by all guild members.  
Syntax: `$clear`

<h4>End</h4>

Ends an ongoing vote.

Can only be used by Admins and the user who started the vote.  
Syntax: `$end`

<h4>Flip</h4>

This command simulates a coin flip. The outcome of it is randomly chosen between `Heads` & `Tails`.

Can be used by all guild members.  
Syntax: `$flip`

<h4>Help</h4>

Displays a list of all available commands and a link to https://github.com/Chromeroni/Hera-Chatbot#commands for more information.

Can be used by all guild members.  
Syntax: `$help`

<h4>Join</h4>

Hera joins the voice channel to which the user who issued the command is connected.

Can be used by all guild members.  
Syntax: `$join`

<h4>Leave</h4>

Hera leaves the voice channel to which she is currently connected.

Can be used by all guild members.  
Syntax: `$leave`

<h4>Lq</h4>

Loops the music queue, so that at the end of a song it is simply requeued.

Can be used by all guild members.  
Syntax: `$lq`

<h4>Motd</h4>

Lets you choose your own message of the day and override the current one.

Can only be used by Admins.  
Syntax: `$motd <message of the day>`

<h4>Move</h4>

Move a queued song to another position in the queue. To identify a song and the position to which it should be moved, use song IDs. Song IDs can be displayed through `$queue`.

Can be used by all guild members.  
Syntax: `$move <song ID> <song ID>`

<h4>No</h4>

Vote no on the currently active vote.

Can be used by all guild members.  
Syntax: `$no`

<h4>Np</h4>

Display the currently playing song.

Can be used by all guild members.  
Syntax: `$np`

<h4>Pause</h4>

Pauses the music player.

Can be used by all guild members.  
Syntax: `$pause`

<h4>Play</h4>

Queues a song through an `URL` or a few keywords with which Hera will search and queue the first search result on YouTube.  
If there is no song playing at the moment, play will start the player by itself.  
If Hera has not join a voice channel yet, she will automatically join the voice channel of the user who issued the command.

Can be used by all guild members.  
Syntax: `$play <url | keywords>`

<h4>Queue</h4>

Displays all songs that are in the music queue. If the message to display the queue exeeds the discord message character limit of 2000, a compact version of the message will be dispalyed instead.  
If the message still exeeds the character limit, only the first 5 songs will be displayed in normal view.

Can be used by all guild members.  
Syntax: `$queue`

<h4>Remove</h4>

Removes a song from the music queue. To identify the song which should be removed, use song IDs. Song IDs can be displayed through `$queue`.

Can be used by all guild members.  
Syntax: `$remove <song ID>`

<h4>Replay</h4>

Requeues the currently playing song.

Can be used by all guild members.  
Syntax: `$replay`

<h4>Report</h4>

Reports the specified user to the Admins.

Can be used by all guild members.  
Syntax: `$report <@user> <report message>`

<h4>Resume</h4>

Resumes the music player.

Can be used by all guild members.  
Syntax: `$resume`

<h4>Shame</h4>

Removes the casual role from the specified user and replaces it with the "schäm dich" role. Also moves the user to the "schämdicheggli" voice channel for a by the Admins specified time period.

Can only be used by Admins.  
Syntax: `$shame <username | usernickname>`

<h4>Skip</h4>

Skips the currently playing song and starts the next one in the queue.

Can be used by all guild members.  
Syntax: `$skip`

<h4>Teams</h4>

Creates two teams of its parameters. There need to be at least 3 parameters to use this command.

Can be used by all guild members.  
Syntax: `$teams <name> <name> <name> ...`

<h4>Version</h4>

Displays the current version of Hera.

Can be used by all guild members.  
Syntax: `$version`

<h4>Volume</h4>

Sets the volume of the music player. Valid values are numbers between 0 and 150.

Can only be used by Admins.  
Syntax: `$volume <value>`

<h4>Vote</h4>

Starts a vote. Only 1 vote can be active at a time. All guild members can vote 1 times on a vote.

Can be used by all guild members.  
Syntax: `$vote <vote topic>`

<h4>Yes</h4>

Vote yes on the currently active vote.

Can be used by all guild members.  
Syntax: `$yes`

<h2>License</h2> 

This project is licensed under the open-source license [Apache License, Version 2.0](http://www.apache.org/licenses/LICENSE-2.0).
