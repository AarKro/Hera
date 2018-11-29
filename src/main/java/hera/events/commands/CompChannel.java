package hera.events.commands;

import hera.eventSupplements.CompChannelManager;
import hera.eventSupplements.MessageSender;
import hera.events.Command;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;
import sx.blah.discord.handle.obj.ICategory;
import sx.blah.discord.handle.obj.IGuild;
import sx.blah.discord.handle.obj.IVoiceChannel;

import java.util.List;

public class CompChannel extends Command {

	private static final Logger LOG = LoggerFactory.getLogger(CompChannel.class);
	
	private static CompChannel instance;
	private MessageSender ms;
	private CompChannelManager ccm;
	private IGuild g;
	
	private List<IVoiceChannel> channels;
	private List<ICategory> categories;
	
	public static CompChannel getInstance() {
		if (instance == null)
			instance = new CompChannel();
		return instance;
	}

	private CompChannel() {
		super(null, 3, false);
		this.ms = MessageSender.getInstance();
		this.ccm = CompChannelManager.getInstance();
	}

	@Override
	protected void commandBody(String[] params, MessageReceivedEvent e) {
		LOG.debug("Start of: CompChannel.execute");

		g = e.getGuild();
		ccm.setGuild(g);

			LOG.debug("CompChannel.execute is Fetching Channels and Nodes");
			channels = g.getVoiceChannels();
			categories = g.getCategories();

			LOG.debug("CompChannel.execute is Checking Channels and Nodes");
			boolean nameUnique = true;
			boolean categoryExists = false;
			ICategory category = null;

			for(IVoiceChannel vc : channels) {
				if(vc.getName().equals("Comptryhard " + params[0])) {
					nameUnique = false;
				}
			}

			for(ICategory c : categories) {
				if(c.getName().toLowerCase().equals(params[1].toLowerCase())) {
					category = c;
					categoryExists = true;
				}
			}

			if(!categoryExists) {
				LOG.debug("CompChannel.execute failed: category dosen't exist");
				ms.sendMessage(e.getChannel(), "", "CompChannel failed, couldn't find category");
				return;
			} else if(!nameUnique){
				LOG.debug("CompChannel.execute failed: name not unique");
				ms.sendMessage(e.getChannel(), "", "CompChannel failed, please chose a unique name");
				return;
			} else {
				LOG.debug("CompChannel.execute creating Channel");
				IVoiceChannel vc = g.createVoiceChannel("Comptryhard " + params[0]);
				vc.changeCategory(category);
				vc.changeUserLimit(Integer.parseInt(params[2]));
			}

			LOG.info(e.getAuthor() + " has created a compChannel " + params[0]);

		LOG.debug("End of: CompChannel.execute");
	}
}
