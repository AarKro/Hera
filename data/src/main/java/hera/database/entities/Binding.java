package hera.database.entities;

import hera.database.types.BindingType;

import javax.persistence.*;

@Entity
@Table(name = "binding")
public class Binding implements PersistenceEntity {

	public static final String ENTITY_NAME = "Binding";

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "guildFK")
	private Long guild;

	@Enumerated(EnumType.STRING)
	@Column(name = "bindingTypeFK")
	private BindingType bindingType;

	private Long channelSnowflake;

	public Binding() {
	}

	public Binding(Long guild, BindingType bindingType, Long channelSnowflake) {
		this.guild = guild;
		this.bindingType = bindingType;
		this.channelSnowflake = channelSnowflake;
	}

	public Binding(Long id, Long guild, BindingType bindingType, Long channelSnowflake) {
		this.id = id;
		this.guild = guild;
		this.bindingType = bindingType;
		this.channelSnowflake = channelSnowflake;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getGuild() {
		return guild;
	}

	public void setGuild(Long guild) {
		this.guild = guild;
	}

	public BindingType getBindingType() {
		return bindingType;
	}

	public void setBindingType(BindingType bindingType) {
		this.bindingType = bindingType;
	}

	public Long getChannelSnowflake() {
		return channelSnowflake;
	}

	public void setChannelSnowflake(Long channelSnowflake) {
		this.channelSnowflake = channelSnowflake;
	}
}
