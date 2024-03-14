package hera.core.messages;

import discord4j.rest.util.Color;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.Consumer;

public final class MessageSpec implements Cloneable {

	private static final Logger LOG = LoggerFactory.getLogger(MessageSpec.class);

	private static final MessageSpec ERROR_SPEC = new MessageSpec();

	private static final MessageSpec CONFIRMATION_SPEC = new MessageSpec();

	private static final MessageSpec DEFAULT_SPEC = new MessageSpec();

	static {
		ERROR_SPEC.setColor(Color.RED);
		CONFIRMATION_SPEC.setColor(Color.GREEN);
		DEFAULT_SPEC.setColor(Color.ORANGE);
	}

	private String title;

	private String description;

	private Color color;

	private String footerText;

	private String footerIconUrl;

	private MessageSpec() {
	}

	public static MessageSpec getDefaultSpec(Consumer<MessageSpec> specBuilder) {
		MessageSpec spec = DEFAULT_SPEC.clone();
		specBuilder.accept(spec);
		return spec;
	}

	public static MessageSpec getErrorSpec(Consumer<MessageSpec> specBuilder) {
		MessageSpec spec = ERROR_SPEC.clone();
		specBuilder.accept(spec);
		return spec;
	}

	public static MessageSpec getConfirmationSpec(Consumer<MessageSpec> specBuilder) {
		MessageSpec spec = CONFIRMATION_SPEC.clone();
		specBuilder.accept(spec);
		return spec;
	}

	protected MessageSpec clone() {
		try {
			return (MessageSpec) super.clone();
		} catch (CloneNotSupportedException e) {
			// We should never ever get here
			LOG.error("The cloneable interface has not been implemented properly");
			LOG.error("Stacktrace:", e);
		}

		return null;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public String getFooterText() {
		return footerText;
	}

	public String getFooterIconUrl() {
		return footerIconUrl;
	}

	public void setFooter(String footerText, String footerIconUrl) {
		this.footerText = footerText;
		this.footerIconUrl = footerIconUrl;
	}
}
