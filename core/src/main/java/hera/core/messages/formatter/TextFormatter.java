package hera.core.messages.formatter;

public class TextFormatter {
	public static String encaseWith(String text, String encaseWith) {
		return String.format("%s%s%s", encaseWith, text, encaseWith);
	}

	public static String encaseWith(String text, IDefaultStrings encaseWith) {
		return String.format("%s%s%s", encaseWith, text, encaseWith);
	}

	public static String encaseWith(String text, String... encaseWith) {
		String out = text;
		for (String encase : encaseWith) {
			out = encaseWith(out, encase);
		}
		return out;
	}

	public static String encaseWith(String text, IDefaultStrings... encaseWith) {
		String out = text;
		for (IDefaultStrings encase : encaseWith) {
			out = encaseWith(out, encase);
		}
		return out;
	}

	public static String prefixWith(String text, String prefixWith) {
		return String.format("%s%s", prefixWith, text);
	}

	public static String prefixWith(String text, IDefaultStrings prefixWith) {
		return String.format("%s%s", prefixWith, text);
	}

	public static String prefixWith(String text, String... prefixWith) {
		String out = text;
		for (String prefix : prefixWith) {
			out = prefixWith(out, prefix);
		}
		return out;
	}

	public static String prefixWith(String text, IDefaultStrings... prefixWith) {
		String out = text;
		for (IDefaultStrings prefix : prefixWith) {
			out = prefixWith(out, prefix);
		}
		return out;
	}

	public static String suffixWith(String text, String suffixWith) {
		return String.format("%s%s", text, suffixWith);
	}

	public static String suffixWith(String text, IDefaultStrings suffixWith) {
		return String.format("%s%s", text, suffixWith);
	}

	public static String suffixWith(String text, String... suffixWith) {
		String out = text;
		for (String suffix : suffixWith) {
			out = suffixWith(out, suffix);
		}
		return out;
	}

	public static String suffixWith(String text, IDefaultStrings... suffixWith) {
		String out = text;
		for (IDefaultStrings suffix : suffixWith) {
			out = suffixWith(out, suffix);
		}
		return out;
	}


}
