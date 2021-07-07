package hera.core.messages.formatter;

public class TextFormatter {
	public static String encaseWith(String text, String markdown) {
		return String.format("%s%s%s", markdown, text, markdown);
	}

	public static String encaseWith(String text, String... markdown) {
		String out = text;
		for (String md : markdown) {
			out = encaseWith(out, md);
		}
		return out;
	}

	public static String encaseWith(String text, DefaultStrings markdown) {
		return String.format("%s%s%s", markdown.getStr(), text, markdown.getStr());
	}

	public static String encaseWith(String text, DefaultStrings... markdown) {
		String out = text;
		for (DefaultStrings md : markdown) {
			out = encaseWith(out, md);
		}
		return out;
	}
}
