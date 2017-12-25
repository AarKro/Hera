This is a Discord Bot written in Java using the Discord4Java library.

Bot-prefix: $

Available commands:

>**help**
>
>Returns a list of all currently available commands.
>
>Syntax: $help

>**flip**
>
>Flips a coin. Return "Heads" or "Tails" with a 50% chance.
>
>Syntax: $flip

>**bind**\*
>
>Binds the output-channel for reports to the channel the command was written in.
>
>Syntax: $bind

>**report**
>
>Sendes a report to the bound channel. Channel needs to be bound in before, else an appropriate error message will appear.
>
>Syntax: $report \<@userToReport\> \<reportMessage\>

>**teams**
>
>Returns the given parameters / names randomly split into 2 teams.
>
>Syntax: $teams \<parameter\> \<parameter\> \<parameter\> ...

\* needs Administrator rights.
