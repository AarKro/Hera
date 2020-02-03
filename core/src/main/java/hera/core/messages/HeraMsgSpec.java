package hera.core.messages;

import discord4j.core.object.entity.MessageChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;

public class HeraMsgSpec implements Cloneable {

	private static final Logger LOG = LoggerFactory.getLogger(HeraMsgSpec.class);

	private static final HeraMsgSpec INFO_SPEC = new HeraMsgSpec();

	private static final HeraMsgSpec WARNING_SPEC = new HeraMsgSpec();

	private static final HeraMsgSpec ERROR_SPEC = new HeraMsgSpec();

	private static final HeraMsgSpec CONFIRMATION_SPEC = new HeraMsgSpec();

	private static final HeraMsgSpec DEFAULT_SPEC = new HeraMsgSpec();

	static {
		INFO_SPEC.setColor(Color.BLUE);
		WARNING_SPEC.setColor(Color.YELLOW);
		ERROR_SPEC.setColor(Color.RED);
		CONFIRMATION_SPEC.setColor(Color.GREEN);
		DEFAULT_SPEC.setColor(Color.ORANGE);
	}

	private MessageChannel channel;

	private String title;

	private String description;

	private Color color;

	private String footerText;

	private String footerIconUrl;

	private HeraMsgSpec() {
	}

	public static HeraMsgSpec getDefaultSpec(MessageChannel channel) {
		return ((HeraMsgSpec) DEFAULT_SPEC.clone()).setChannel(channel);
	}

	public static HeraMsgSpec getInfoSpec(MessageChannel channel) {
		return ((HeraMsgSpec) INFO_SPEC.clone()).setChannel(channel);
	}

	public static HeraMsgSpec getWarningSpec(MessageChannel channel) {
		return ((HeraMsgSpec) WARNING_SPEC.clone()).setChannel(channel);
	}

	public static HeraMsgSpec getErrorSpec(MessageChannel channel) {
		return ((HeraMsgSpec) ERROR_SPEC.clone()).setChannel(channel);
	}

	public static HeraMsgSpec getConfirmationSpec(MessageChannel channel) {
		return ((HeraMsgSpec) CONFIRMATION_SPEC.clone()).setChannel(channel);
	}

	public Object clone() {
		try {
			return super.clone();
		} catch (CloneNotSupportedException e) {
			// We should never ever get here
			LOG.error("The cloneable interface has not been implemented properly");
			LOG.error("Stacktrace:", e);
		}

		return null;
	}

	public MessageChannel getChannel() {
		return channel;
	}

	public HeraMsgSpec setChannel(MessageChannel channel) {
		this.channel = channel;
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
