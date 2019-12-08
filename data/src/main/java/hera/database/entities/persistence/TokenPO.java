package hera.database.entities.persistence;

import hera.database.entities.mapped.Token;
import hera.database.types.TokenKey;

import javax.persistence.*;

@Entity
@Table(name = "token")
public class TokenPO implements IPersistenceEntity<Token> {

	public static final String ENTITY_NAME = "TokenPO";

	@Id
	@GeneratedValue
	private int id;

	private String token;

	@Enumerated(EnumType.STRING)
	private TokenKey key;

	private String description;

	public TokenPO() {
	}

	public TokenPO(String token, TokenKey key, String description) {
		this.token = token;
		this.key = key;
		this.description = description;
	}

	public Token mapToNonePO() {
		return new Token(
				this.id,
				this.token,
				this.key,
				this.description
		);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
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
