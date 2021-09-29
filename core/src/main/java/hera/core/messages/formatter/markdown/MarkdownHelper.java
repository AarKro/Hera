package hera.core.messages.formatter.markdown;

import hera.core.messages.formatter.DefaultStrings;
import hera.core.messages.formatter.TextFormatter;

public class MarkdownHelper {
	public String applyMarkdown(String apply, Markdown... markdown) {
		String s = apply;
		for (Markdown m : markdown) {
			s = m.apply(s);
		}
		return s;
	}

	public static String makeBold(String s) {
		return TextFormatter.encaseWith(s, Markdown.BOLD);
	}

	public static String makeItalics1(String s) {
		return TextFormatter.encaseWith(s, Markdown.ITALICS1);
	}

	public static String makeItalics2(String s) {
		return TextFormatter.encaseWith(s, Markdown.ITALICS2);
	}

	public static String underline(String s) {
		return TextFormatter.encaseWith(s, Markdown.UNDERLINE);
	}

	public static String strikeThrough(String s) {
		return TextFormatter.encaseWith(s, Markdown.STRIKE_THROUGH);
	}

	public static String makeCodeBlock(String s) {
		if (hasMultipleLines(s)) {
			return makeMultiLineCodeBlock(s);
		} else {
			return makeSingleLineCodeBlock(s);
		}
	}

	public static String makeSingleLineCodeBlock(String s) {
		return TextFormatter.encaseWith(s, Markdown.SINGLE_LINE_BLOCK_QUOTE);
	}

	public static String makeMultiLineCodeBlock(String s) {
		return TextFormatter.encaseWith(s, Markdown.MULTI_LINE_CODE);
	}

	public static String makeBlockQuote(String s) {
		if (hasMultipleLines(s)) {
			return makeMultiLineBlockQuote(s);
		} else {
			return makeSingleLineBlockQuote(s);
		}
	}

	public static String makeSingleLineBlockQuote(String s) {
		return TextFormatter.prefixWith(s, Markdown.SINGLE_LINE_BLOCK_QUOTE);
	}

	public static String makeMultiLineBlockQuote(String s) {
		return TextFormatter.prefixWith(s, Markdown.MULTI_LINE_BLOCK_QUOTE);
	}

	private static boolean hasMultipleLines(String s) {
		return s.contains(DefaultStrings.NEW_LINE.getStr());
	}
}
