package hera.events;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hera.eventSupplements.CompChannelManager;
import hera.eventSupplements.MessageSender;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;
import sx.blah.discord.handle.obj.ICategory;
import sx.blah.discord.handle.obj.IGuild;
import sx.blah.discord.handle.obj.IVoiceChannel;

public class CompChannel implements Command {

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
		this.ms = MessageSender.getInstance();
		this.ccm = CompChannelManager.getInstance();
	}

	public void execute(MessageReceivedEvent e) {
		LOG.debug("Start of: CompChannel.execute");
		
		g = e.getGuild();
		ccm.setGuild(g);
		
		String[] args = e.getMessage().getContent().split(" ");
		if (args.length == 4) {			
			LOG.debug("CompChannel.execute is Fetching Channels and Nodes");
			channels = g.getVoiceChannels();
			categories = g.getCategories();
			
			LOG.debug("CompChannel.execute is Checking Channels and Nodes");
			boolean nameUnique = true;
			boolean categoryExists = false;
			ICategory category = null;
			
			for(IVoiceChannel vc : channels) {
				if(vc.getName().equals("Comptryhard " + args[1])) {
					nameUnique = false;
				}
			}
			
			for(ICategory c : categories) {
				if(c.getName().toLowerCase().equals(args[2].toLowerCase())) {
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
				IVoiceChannel vc = g.createVoiceChannel("Comptryhard " + args[1]);
				vc.changeCategory(category);
				vc.changeUserLimit(Integer.parseInt(args[3]));
			}		
			
			LOG.info(e.getAuthor() + " has created a compChannel " + args[1]);
			
		} else {
			ms.sendMessage(e.getChannel(), "", "Invalid usage of $compChannel .\nSyntax: $compChannel channelName category maxUsers");
			LOG.debug(e.getAuthor() + " used command compChannel wrong");
		}		
			
		LOG.debug("End of: CompChannel.execute");
	}
}
