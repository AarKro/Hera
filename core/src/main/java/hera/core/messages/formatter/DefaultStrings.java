package hera.core.messages.formatter;

public enum DefaultStrings implements IDefaultStrings {
	NEW_LINE("\n"),
	TABULATOR("\t"),
	WHITESPACE(" ");



	private final String str;

	DefaultStrings(String str) {
		this.str = str;
	}

	public String getStr() {
		return str;
	}

	@Override
	public String toString() {
		return str;
	}
}
