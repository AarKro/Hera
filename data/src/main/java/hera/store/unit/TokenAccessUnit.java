package hera.store.unit;

import hera.database.entities.Token;
import hera.database.types.TokenKey;
import hera.store.exception.FailedAfterRetriesException;
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

	public void update(Token token) {
		try {
			List<Token> foundTokens = forKey(token.getKey());

			foundTokens.get(0).setToken(token.getToken());
			retryOnFail(() -> dao.update(TokenPO.class, foundTokens.get(0), foundTokens.get(0).getId()));

		} catch(FailedAfterRetriesException e) {
			LOG.error("Error while trying to add entity of type GuildSettingsPO");
			LOG.debug("Stacktrace:", e);
		}
	}
}
