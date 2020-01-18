package hera.database.entities;

import hera.database.types.TokenKey;

import javax.persistence.*;

@Entity
@Table(name = "token")
public class Token implements PersistenceEntity {

	public static final String ENTITY_NAME = "Token";

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String token;

	@Enumerated(EnumType.STRING)
	@Column(name = "name")
	private TokenKey key;

	private String description;

	public Token() {
	}

	public Token(String token, TokenKey key, String description) {
		this.token = token;
		this.key = key;
		this.description = description;
	}

	public Token(Long id, String token, TokenKey key, String description) {
		this.id = id;
		this.token = token;
		this.key = key;
		this.description = description;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public TokenKey getKey() {
		return key;
	}

	public void setKey(TokenKey key) {
		this.key = key;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
