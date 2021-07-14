package hera.core.events.reactions;

import java.util.HashMap;

public class ReactionHandler {
	private IReactionHandler handler;
	private MetaData metaData = new MetaData();

	public static class MetaData extends HashMap<String, Object> {
		public <T> T getParsed(String key, Class<T> toParse) {
			return toParse.cast(get(key));
		}
	}

	public ReactionHandler(IReactionHandler handler) {
		this.handler = handler;
	}

	public IReactionHandler getHandler() {
		return handler;
	}

	public boolean containsKey(String key) {
		return metaData.containsKey(key);
	}

	public Object getValue(String key) {
		return metaData.get(key);
	}

	public <T> T getParsedValue(String key, Class<T> toParse) {
		return metaData.getParsed(key, toParse);
	}

	public ReactionHandler addData(String key, Object value) {
		metaData.put(key, value);
		return this;
	}

	public MetaData getMetaData() {
		return metaData;
	}
}
