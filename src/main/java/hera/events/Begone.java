package d4jbot.events;

import java.util.Random;

import d4jbot.misc.MessageSender;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;
import sx.blah.discord.handle.obj.IUser;
import sx.blah.discord.handle.obj.IVoiceChannel;
import sx.blah.discord.handle.obj.Permissions;

public class Begone implements Command {
	private static Begone instance;

	public static Begone getInstance() {
		if (instance == null)
			instance = new Begone();
		return instance;
	}

	private MessageSender ms;

	private Begone() {
		this.ms = MessageSender.getInstance();
	}

	public void execute(MessageReceivedEvent e) {

		if (e.getAuthor().getPermissionsForGuild(e.getGuild()).contains(Permissions.ADMINISTRATOR) || e.getAuthor()
				.getRolesForGuild(e.getGuild()).contains(e.getGuild().getRolesByName("BeGone").get(0))) {
			String[] args = e.getMessage().getContent().split(" ");

			if (args.length > 1) {

				String username = "";
				for (int i = 1; i < args.length; i++) {
					username += args[i] + " ";
				}
				username = username.trim();

				/*
				 * StringBuilder sb = new StringBuilder(username); sb.insert(2, "!"); Does not
				 * work :/
				 */

				Random rnd = new Random();
				for (IUser user : e.getGuild().getUsers()) {

					if (username.equals(user.getName()) || username
							.equals(user.getNicknameForGuild(e.getGuild()))/* || sb.equals(user.mention()) */ ) {

						boolean success = false;
						while (!success) {

							IVoiceChannel moveTo = e.getGuild().getVoiceChannels()
									.get(rnd.nextInt(e.getGuild().getVoiceChannels().size()));
							if (moveTo.getModifiedPermissions(user).contains(Permissions.VOICE_CONNECT)) {
								user.moveToVoiceChannel(moveTo);
								success = true;
								ms.sendMessage(e.getChannel(), user.mention() + " moved to " + moveTo.getName());
							}

						}

					}

				}

			} else
				ms.sendMessage(e.getChannel(), "Invalid usage of $begone.\nSyntax: $begone <name/nickname>");
		} else
			ms.sendMessage(e.getChannel(),
					"You need to be an Administrator of this server or possess the BeGone role to use this command.");

	}
}
