package hera.database.types;

public enum SnowflakeType {
	GUILD(0),
	CHANNEL(1),
	USER(2);

	private int value;

	SnowflakeType(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}
}
