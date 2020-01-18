package hera.database.entities;

import hera.database.types.SnowflakeType;

import javax.persistence.*;

@Entity
@Table(name = "role_member")
public class RoleMember implements PersistenceEntity {

	public static final String ENTITY_NAME = "RoleMember";

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private Long snowflake;

	@ManyToOne
	@JoinColumn(name = "roleFK")
	private Role role;

	@Enumerated(EnumType.STRING)
	@Column(name = "snowflakeTypeFK")
	private SnowflakeType snowflakeType;

	public RoleMember() {
	}

	public RoleMember(Long snowflake, Role role, SnowflakeType snowflakeType) {
		this.snowflake = snowflake;
		this.role = role;
		this.snowflakeType = snowflakeType;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getSnowflake() {
		return snowflake;
	}

	public void setSnowflake(Long snowflake) {
		this.snowflake = snowflake;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public SnowflakeType getSnowflakeType() {
		return snowflakeType;
	}

	public void setSnowflakeType(SnowflakeType snowflakeType) {
		this.snowflakeType = snowflakeType;
	}
}
