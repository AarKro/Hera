# Hera

[![release](https://img.shields.io/badge/latest%20release%20version-v1.0.0-004cc6.svg)](https://github.com/Chromeroni/Hera-Chatbot/releases/latest) [![Waffle.io - Columns and their card count](https://badge.waffle.io/Chromeroni/Hera-Chatbot.svg?columns=Inbox,To%20Be%20Reviewed,To%20Do,In%20Progress,To%20Be%20Merged)](https://waffle.io/Chromeroni/Hera-Chatbot)

Hera is a Discord chatbot written in Java using the [Discord4Java](https://github.com/Discord4J/Discord4J) library.  
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

## Commands  

### Short command listing

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
| [Playlists](#playlists) | All users | `$playlists` |
| [Prefix](#prefix) | Admins only | `$prefix <prefix>` |
| [Queue](#queue) | All users | `$queue` |
| [Remove](#remove) | All users | `$remove <song ID>` |
| [Replay](#replay) | All users | `$replay` |
| [Report](#report) | All users | `$report <@user> <report message>` |
| [Resume](#resume) | All users | `$resume` |
| [Save to playlist](#save-to-playlist) | All users | `$savetoplaylist <name>` |
| [Shame](#shame) | Admins only | `$shame <username \| usernickname>` |
| [Skip](#skip) | All users | `$skip` |
| [Start playlist](#start-playlist) | All users | `$startplaylist <name>` |
| [Teams](#teams) | All users | `$teams <name> <name> <name> ...` |
| [Version](#version) | All users | `$version` |
| [Volume](#volume) | Admins only | `$volume <value>` |
| [Vote](#vote) | All users | `$vote <vote topic>` |
| [Yes](#yes) | All users | `$yes` |

### Detailed command listing

#### Alias

Creates an alias for a command. The command can now be used with both, its alias or with its original name.

Can only be used by Admins.  
Syntax: `$alias <command> <alias>`

---

#### Begone

Moves another user to a random voice channel, for which they have the right permissions for.

Can only be used by Admins and the role BeGone.  
Syntax: `$begone <username | usernickname>`

---

#### Bind

Binds a channel to a specific output message group from Hera.  
For example: `$bind music` binds all of Heras output messages regarding music to the channel in which the command was written in.

Output message groups:  
* `report`
* `music`
* `announcements`

Can only be used by Admins.  
Syntax: `$bind <output message group>`

---

#### Clear

Clears all songs from the current music queue.

Can be used by all guild members.  
Syntax: `$clear`

---

#### End

Ends an ongoing vote.

Can only be used by Admins and the user who started the vote.  
Syntax: `$end`

---

#### Flip

This command simulates a coin flip. The outcome of it is randomly chosen between `Heads` & `Tails`.

Can be used by all guild members.  
Syntax: `$flip`

---

#### Help

Displays a list of all available commands and a link to https://github.com/Chromeroni/Hera-Chatbot#commands for more information.

Can be used by all guild members.  
Syntax: `$help`

---

#### Join

Hera joins the voice channel to which the user who issued the command is connected.

Can be used by all guild members.  
Syntax: `$join`

---

#### Leave

Hera leaves the voice channel to which she is currently connected.

Can be used by all guild members.  
Syntax: `$leave`

---

#### Lq

Loops the music queue, so that at the end of a song it is simply requeued.

Can be used by all guild members.  
Syntax: `$lq`

---

#### Motd

Lets you choose your own message of the day and override the current one.

Can only be used by Admins.  
Syntax: `$motd <message of the day>`

---

#### Move

Move a queued song to another position in the queue. To identify a song and the position to which it should be moved, use song IDs. Song IDs can be displayed through `$queue`.

Can be used by all guild members.  
Syntax: `$move <song ID> <song ID>`

---

#### No

Vote no on the currently active vote.

Can be used by all guild members.  
Syntax: `$no`

---

#### Np

Display the currently playing song.

Can be used by all guild members.  
Syntax: `$np`

---

#### Pause

Pauses the music player.

Can be used by all guild members.  
Syntax: `$pause`

---

#### Play

Queues a song through an `URL` or a few keywords with which Hera will search and queue the first search result on YouTube.  
If there is no song playing at the moment, play will start the player by itself.  
If Hera has not join a voice channel yet, she will automatically join the voice channel of the user who issued the command.

Can be used by all guild members.  
Syntax: `$play <url | keywords>`

---

#### Playlists

Retrieves and displays a list of all currently saved playlists.

Can be used by all guild members.  
Syntax: `$playlists`

---

#### Prefix

Change the default prefix ($) of hera to your character of choosing.

Can only be used by Admins. 
Syntax: `$prefix <prefix>`

---

#### Queue

Displays all songs that are in the music queue. If the message to display the queue exeeds the discord message character limit of 2000, a compact version of the message will be dispalyed instead.  
If the message still exeeds the character limit, only the first 5 songs will be displayed in normal view.

Can be used by all guild members.  
Syntax: `$queue`

---

#### Remove

Removes a song from the music queue. To identify the song which should be removed, use song IDs. Song IDs can be displayed through `$queue`.

Can be used by all guild members.  
Syntax: `$remove <song ID>`

---

#### Replay

Requeues the currently playing song.

Can be used by all guild members.  
Syntax: `$replay`

---

#### Report

Reports the specified user to the Admins.

Can be used by all guild members.  
Syntax: `$report <@user> <report message>`

---

#### Resume

Resumes the music player.

Can be used by all guild members.  
Syntax: `$resume`

---

#### Save to playlist

Save the current queue as a playlist. Saved playlists can be conveniently requeued at any time.

Can be used by all guild members.  
Syntax: `$savetoplaylist <name>`

---

#### Shame

Removes the casual role from the specified user and replaces it with the "schäm dich" role. Also moves the user to the "schämdicheggli" voice channel for a by the Admins specified time period.

Can only be used by Admins.  
Syntax: `$shame <username | usernickname>`

---

#### Skip

Skips the currently playing song and starts the next one in the queue.

Can be used by all guild members.  
Syntax: `$skip`

---

#### Start playlist

Queue all songs from a previously saved playlist.

Can be used by all guild members.  
Syntax: `$startplaylist <name>`

---

#### Teams

Creates two teams of its parameters. There need to be at least 3 parameters to use this command.

Can be used by all guild members.  
Syntax: `$teams <name> <name> <name> ...`

---

#### Version

Displays the current version of Hera.

Can be used by all guild members.  
Syntax: `$version`

---

#### Volume

Sets the volume of the music player. Valid values are numbers between 0 and 150.

Can only be used by Admins.  
Syntax: `$volume <value>`

---

#### Vote

Starts a vote. Only 1 vote can be active at a time. All guild members can vote 1 times on a vote.

Can be used by all guild members.  
Syntax: `$vote <vote topic>`

---

#### Yes

Vote yes on the currently active vote.

Can be used by all guild members.  
Syntax: `$yes`

---

## License 

This project is licensed under the open-source license [Apache License, Version 2.0](http://www.apache.org/licenses/LICENSE-2.0).
