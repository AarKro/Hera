package hera.store.unit;

import hera.database.entities.mapped.Guild;
import hera.database.entities.persistence.GuildPO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.stream.Collectors;

public class GuildAccessUnit extends StorageAccessUnit<GuildPO, Guild> {

	private static final Logger LOG = LoggerFactory.getLogger(GuildAccessUnit.class);

	public GuildAccessUnit() {
		super(GuildPO.ENTITY_NAME);
	}

	public void deleteById(Long id) {
		List<Guild> guilds = data.stream().filter(guild -> guild.getSnowflake().equals(id)).collect(Collectors.toList());
		if (!guilds.isEmpty()) {
			try {
				retryOnFail(() -> dao.delete(GuildPO.class, guilds.get(0)));
				data = data.stream().filter(guild -> !guild.getSnowflake().equals(id)).collect(Collectors.toList());
			} catch(Exception e) {
				LOG.error("Error while trying to remove guild for id {} ", id);
				LOG.debug("Stacktrace:", e);
			}
		}
	}
}
