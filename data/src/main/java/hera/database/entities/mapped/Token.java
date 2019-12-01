package hera.database.entities.mapped;

import hera.database.entities.persistence.TokenPO;
import hera.database.types.TokenKey;

public class Token implements IMappedEntity<TokenPO> {

	public static final String NAME = "Token";

	private int id;

	private String token;

	private TokenKey key;

	private String description;

	public Token() {
	}

	public Token(String token, TokenKey key, String description) {
		this.token = token;
		this.key = key;
		this.description = description;
	}

	public Token(int id, String token, TokenKey key, String description) {
		this.id = id;
		this.token = token;
		this.key = key;
		this.description = description;
	}

	public TokenPO mapToPO() {
		return new TokenPO(
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
