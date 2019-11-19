package hera.database.entity.persistence;

import hera.database.entity.mapped.Binding;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "binding")
public class BindingPO implements IPersistenceEntity<Binding>, Serializable {

	public static final String ENTITY_NAME = "BindingPO";

	@Id
	private Long guildFK;

	@Id
	private int bindingTypeFK;

	private Long channelSnowflake;

	public BindingPO() {
	}

	public BindingPO(Long guildFK, int bindingTypeFK, Long channelSnowflake) {
		this.guildFK = guildFK;
		this.bindingTypeFK = bindingTypeFK;
		this.channelSnowflake = channelSnowflake;
	}

	public Binding mapToNonePO() {
		return new Binding(
				this.guildFK,
				this.bindingTypeFK,
				this.channelSnowflake
		);
	}

	public Long getGuildFK() {
		return guildFK;
	}

	public void setGuildFK(Long guildFK) {
		this.guildFK = guildFK;
	}

	public int getBindingTypeFK() {
		return bindingTypeFK;
	}

	public void setBindingTypeFK(int bindingTypeFK) {
		this.bindingTypeFK = bindingTypeFK;
	}

	public Long getChannelSnowflake() {
		return channelSnowflake;
	}

	public void setChannelSnowflake(Long channelSnowflake) {
		this.channelSnowflake = channelSnowflake;
	}
}
