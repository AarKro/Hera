package hera.entity.mapped;

public class Binding {

	private Long guild;

	private int bindingType;

	private Long channelSnowflake;

	public Binding() {
	}

	public Binding(Long guild, int bindingType, Long channelSnowflake) {
		this.guild = guild;
		this.bindingType = bindingType;
		this.channelSnowflake = channelSnowflake;
	}

	public Long getGuild() {
		return guild;
	}

	public void setGuild(Long guild) {
		this.guild = guild;
	}

	public int getBindingType() {
		return bindingType;
	}

	public void setBindingType(int bindingType) {
		this.bindingType = bindingType;
	}

	public Long getChannelSnowflake() {
		return channelSnowflake;
	}

	public void setChannelSnowflake(Long channelSnowflake) {
		this.channelSnowflake = channelSnowflake;
	}
}
