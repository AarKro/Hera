package hera.events.commands;

import hera.eventSupplements.MessageSender;
import hera.events.Command;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;
import sx.blah.discord.handle.obj.IUser;
import sx.blah.discord.handle.obj.IVoiceChannel;
import sx.blah.discord.handle.obj.Permissions;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Begone extends Command {
	
	private static final Logger LOG = LoggerFactory.getLogger(Begone.class);
	
	private static Begone instance;

	public static Begone getInstance() {
		if (instance == null)
			instance = new Begone();
		return instance;
	}

	private MessageSender ms;

	private Begone() {
		super(Arrays.asList("ADMINISTRATOR", "BeGone"), 1, false);
		this.ms = MessageSender.getInstance();
	}

	@Override
	protected void commandBody(String[] params, MessageReceivedEvent e) {
		LOG.debug("Start of: Begone.execute");

		Random rnd = new Random();
		List<IUser> users = e.getGuild().getUsersByName(params[0]);
		if (!users.isEmpty()) {
			boolean success = false;
			while (!success) {
				IVoiceChannel moveTo = e.getGuild().getVoiceChannels().get(rnd.nextInt(e.getGuild().getVoiceChannels().size()));

				if (moveTo.getModifiedPermissions(users.get(0)).contains(Permissions.VOICE_CONNECT)) {
					users.get(0).moveToVoiceChannel(moveTo);
					success = true;
					ms.sendMessage(e.getChannel(), "", users.get(0).mention() + " moved to " + moveTo.getName());
					LOG.info("User " + users.get(0).getName() + " moved to " + moveTo.getName() + " : " + moveTo.getLongID());
				}
			}
		}

		LOG.debug("End of: Begone.execute");
	}
}
