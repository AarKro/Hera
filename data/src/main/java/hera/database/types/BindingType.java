package hera.database.types;

public enum BindingType {
	MUSIC(1),
	ANNOUNCEMENT(2),
	REPORT(3);

	private int value;

	BindingType(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}
}
