package hera.core.messages.formatter;

public enum DefaultStrings {
	NEW_LINE("\n"),
	BOLD("**"),
	ITALICS1("_"),
	ITALICS2("*"),
	UNDERLINE("__"),
	STRIKE_THROUGH("~~"),
	SINGLE_LINE_CODE("`"),
	MULTI_LINE_CODE("```"),
	SINGLE_LINE_BLOCK(">"),
	MULTI_LINE_BLOCK(">>>");


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
