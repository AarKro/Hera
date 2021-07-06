package hera.core.messages.formatter;

public class TextFormatter {
	public static String withMarkdown(String text, String markdown) {
		return String.format("%s%s%s", markdown, text, markdown);
	}

	public static String withMarkdown(String text, String... markdown) {
		String out = text;
		for (String md : markdown) {
			out = withMarkdown(out, md);
		}
		return out;
	}

	public static String withMarkdown(String text, DefaultStrings markdown) {
		return String.format("%s%s%s", markdown.getStr(), text, markdown.getStr());
	}

	public static String withMarkdown(String text, DefaultStrings... markdown) {
		String out = text;
		for (DefaultStrings md : markdown) {
			out = withMarkdown(out, md);
		}
		return out;
	}
}
