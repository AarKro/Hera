package d4jbot.enums;

import sx.blah.discord.handle.obj.IChannel;

public enum BoundChannel {
	REPORT(null), MUSIC(null);
	
	private IChannel boundChannel;
	
	// constructor
	private BoundChannel(IChannel boundChannel) {
		this.boundChannel = boundChannel;
	}

	// getters & setters
	public IChannel getBoundChannel() {
		return boundChannel;
	}

	public void setBoundChannel(IChannel boundChannel) {
		this.boundChannel = boundChannel;
	}
}