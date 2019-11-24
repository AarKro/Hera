package hera.database.entity.mapped;

import hera.database.entity.persistence.TokenPO;

public class Token implements IMappedEntity<TokenPO> {

	public static final String NAME = "Token";

	private int id;

	private String token;

	private String name;

	private String description;

	public Token() {
	}

	public Token(String token, String name, String description) {
		this.token = token;
		this.name = name;
		this.description = description;
	}

	public Token(int id, String token, String name, String description) {
		this.id = id;
		this.token = token;
		this.name = name;
		this.description = description;
	}

	public TokenPO mapToPO() {
		return new TokenPO(
				this.token,
				this.name,
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
