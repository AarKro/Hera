package hera.database.types;

public enum BindingType {
	MUSIC(0),
	ANNOUNCEMENT(1),
	REPORT(2);

	private int value;

	BindingType(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}
}
