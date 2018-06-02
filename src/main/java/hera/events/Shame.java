package hera.events;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hera.enums.BotSettings;
import hera.eventSupplements.MessageSender;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;
import sx.blah.discord.handle.obj.IRole;
import sx.blah.discord.handle.obj.IUser;
import sx.blah.discord.handle.obj.IVoiceChannel;
import sx.blah.discord.handle.obj.Permissions;

public class Shame implements Command {

	private static final Logger LOG = LoggerFactory.getLogger(Shame.class);
	
	private static Shame instance;

	public static Shame getInstance() {
		if (instance == null)
			instance = new Shame();
		return instance;
	}

	private MessageSender ms;

	private Shame() {
		this.ms = MessageSender.getInstance();
	}

	public void execute(MessageReceivedEvent e) {
		LOG.debug("Start of: Shame.execute");
		if (e.getAuthor().getPermissionsForGuild(e.getGuild()).contains(Permissions.ADMINISTRATOR)) {

			String[] args = e.getMessage().getContent().split(" ");

			if (args.length > 1) {
				LOG.debug("Enough parameters to interpret command: " + args.length);
				
				String username = "";
				for (int i = 1; i < args.length; i++) {
					username += args[i] + " ";
				}
				username = username.trim();
				
				LOG.debug(e.getAuthor() + " wants to shame the user " + username);

				List<IUser> users = e.getGuild().getUsersByName(username, true);
				if (!users.isEmpty()) {

					if (users.get(0).getRolesForGuild(e.getGuild())
							.contains(e.getGuild().getRolesByName("Casual").get(0))) {

						Runnable runnable = new Runnable() {

							public void run() {
								try {
									LOG.debug("Start of: Shame.Thread");
									IRole casual = e.getGuild().getRolesByName("Casual").get(0);
									IRole shameOnYou = e.getGuild().getRolesByName("Schäm dich").get(0);
									IVoiceChannel current = e.getAuthor().getVoiceStateForGuild(e.getGuild())
											.getChannel();
									IVoiceChannel shameCorner = e.getGuild().getVoiceChannelsByName("Schämdicheggli")
											.get(0);

									removeCasualRoleAndMoveUser(users.get(0), casual, shameOnYou, shameCorner);
									LOG.info(e.getAuthor() + " has put " + users.get(0) + " to shame");

									LOG.info("Putting Shame.Thread to sleep");
									Thread.sleep(Long.parseLong(BotSettings.SHAME_TIME.getPropertyValue()));
									LOG.info("Waking Shame.Thread up from sleep");
									
									ms.sendMessage(e.getChannel(), "", users.get(0).mention() + " has been put to shame.");
									addCasualRole(users.get(0), casual, shameOnYou, current);
									LOG.info(users.get(0) + " will no longer be shamed... for now");
									LOG.debug("End of: Shame.Thread");
								} catch (Exception e) {
									LOG.error("Exception in Shame.Thread");
									LOG.error(e.getMessage() + " : " + e.getCause());
								} finally {
									LOG.info("Shame.Thread ended");
								}
							}

						};

						Thread thread = new Thread(runnable);
						LOG.info("Shame.Thread created");
						thread.start();
						LOG.info("Shame.Thread started");

					} else {
						ms.sendMessage(e.getChannel(), "", users.get(0).getName() + " can't be put to shame.");
						LOG.debug(e.getAuthor() + " tried to put " + users.get(0).getName() + " to shame, but couldn't for some reason");
					}
				} else {
					ms.sendMessage(e.getChannel(), "", "No user with the name " + username + " found.");
					LOG.debug("No user with the name " + username + " found");
				}
			} else {
				ms.sendMessage(e.getChannel(), "", "Invalid usage of $shame\nSyntax: $shame <user>");
				LOG.debug(e.getAuthor() + " used command shame wrong");
			}
		} else {
			ms.sendMessage(e.getChannel(), "", "You need to be an Administrator of this server to use this command.");
			LOG.debug(e.getAuthor() + " used command shame but is not an admin on this server");
		}
		LOG.debug("End of: Shame.execute");
	}

	private void removeCasualRoleAndMoveUser(IUser user, IRole casual, IRole shameOnYou, IVoiceChannel shameCorner) {
		LOG.debug("Start of: Shame.removeCasualRoleAndMoveUser");
		user.moveToVoiceChannel(shameCorner);
		user.addRole(shameOnYou);
		user.removeRole(casual);
		LOG.debug("End of: Shame.removeCasualRoleAndMoveUser");
	}

	private void addCasualRole(IUser user, IRole casual, IRole shameOnYou, IVoiceChannel current) {
		LOG.debug("Start of: Shame.addCasualRole");
		user.moveToVoiceChannel(current);
		user.addRole(casual);
		user.removeRole(shameOnYou);
		LOG.debug("End of: Shame.addCasualRole");
	}
}
