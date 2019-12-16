package hera.database.types;

public enum GlobalSettingKey {
	VERSION(0);

	private int value;

	GlobalSettingKey(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}
}
