package hera.store.unit;

import hera.database.entities.mapped.Token;
import hera.database.entities.persistence.TokenPO;
import hera.database.types.TokenKey;

import java.util.List;
import java.util.stream.Collectors;

public class TokenAccessUnit extends StorageAccessUnit<TokenPO, Token>{

	public TokenAccessUnit() {
		super(TokenPO.ENTITY_NAME);
	}

	public List<Token> forKey(TokenKey key) {
		return data.stream().filter((t) -> t.getKey().equals(key)).collect(Collectors.toList());
	}
}
