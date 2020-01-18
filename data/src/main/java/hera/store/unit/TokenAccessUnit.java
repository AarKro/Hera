package hera.store.unit;

import hera.database.entities.Token;
import hera.database.types.TokenKey;

import java.util.Collections;
import java.util.List;

public class TokenAccessUnit extends StorageAccessUnit<Token>{

	public TokenAccessUnit() {
		super(Token.class, Token.ENTITY_NAME);
	}

	public List<Token> forKey(TokenKey key) {
		return get(Collections.singletonMap("name", key.name()));
	}
}
