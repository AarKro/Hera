package hera.store.unit;

import hera.database.entity.mapped.Token;
import hera.database.entity.persistence.TokenPO;

import java.util.List;
import java.util.stream.Collectors;

public class TokenAccessUnit extends StorageAccessUnit<TokenPO, Token>{

	public TokenAccessUnit() {
		super(TokenPO.ENTITY_NAME);
	}

	public List<Token> forName(String name) {
		return data.stream().filter((t) -> t.getName().equals(name)).collect(Collectors.toList());
	}
}
