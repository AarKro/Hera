package d4jbots.enums;

public enum BotPrefix {
	BOT_PREFIX("$");
	
	private String botPrefix;
	
	// constructor
	private BotPrefix(String botPrefix) {
		this.botPrefix = botPrefix;
	}

	// getters & setters
	public String getBotPrefix() {
		return botPrefix;
	}

	public void setBotPrefix(String botPrefix) {
		this.botPrefix = botPrefix;
	}
}
