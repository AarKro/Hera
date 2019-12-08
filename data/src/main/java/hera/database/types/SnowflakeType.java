package hera.database.types;

public enum SnowflakeType {
	GUILD(1),
	CHANNEL(2),
	USER(3);

	private int value;

	SnowflakeType(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}
}
