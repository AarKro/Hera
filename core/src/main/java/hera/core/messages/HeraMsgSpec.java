package hera.core.messages;

import discord4j.core.object.entity.MessageChannel;

import java.awt.*;

public class HeraMsgSpec {

	private MessageChannel channel;

	private MessageType messageType;

	private String title;

	private String description;

	private Color color;

	private String footerText;

	private String footerIconUrl;

	public HeraMsgSpec(MessageChannel channel) {
		this.channel = channel;
	}

	public MessageChannel getChannel() {
		return channel;
	}

	public MessageType getMessageType() {
		return messageType;
	}

	public HeraMsgSpec setMessageType(MessageType messageType) {
		this.messageType = messageType;
		return this;
	}

	public String getTitle() {
		return title;
	}

	public HeraMsgSpec setTitle(String title) {
		this.title = title;
		return this;
	}

	public String getDescription() {
		return description;
	}

	public HeraMsgSpec setDescription(String description) {
		this.description = description;
		return this;
	}

	public Color getColor() {
		return color;
	}

	public HeraMsgSpec setColor(Color color) {
		this.color = color;
		return this;
	}

	public String getFooterText() {
		return footerText;
	}

	public String getFooterIconUrl() {
		return footerIconUrl;
	}

	public HeraMsgSpec setFooter(String footerText, String footerIconUrl) {
		this.footerText = footerText;
		this.footerIconUrl = footerIconUrl;
		return this;
	}
}
