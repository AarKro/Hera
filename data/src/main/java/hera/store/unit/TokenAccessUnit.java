package hera.store.unit;

import hera.database.entities.Token;
import hera.database.types.TokenKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.List;

public class TokenAccessUnit extends StorageAccessUnit<Token>{

	public TokenAccessUnit() {
		super(Token.class, Token.ENTITY_NAME);
	}
	private static final Logger LOG = LoggerFactory.getLogger(GuildSettingAccessUnit.class);

	public List<Token> forKey(TokenKey key) {
		return get(Collections.singletonMap("key", key));
	}
}
