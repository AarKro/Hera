package hera.entity.persistence;

import hera.entity.mapped.Token;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "token")
public class TokenPO {

	public static final String ENTITY_NAME = "TokenPO";

	@Id
	@GeneratedValue
	private int id;

	private String token;

	private String name;

	private String description;

	public TokenPO() {
	}

	public TokenPO(String token, String name, String description) {
		this.token = token;
		this.name = name;
		this.description = description;
	}

	public Token mapToNonePO() {
		return new Token(
				this.id,
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
