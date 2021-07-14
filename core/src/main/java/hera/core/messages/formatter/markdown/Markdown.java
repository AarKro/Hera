package hera.core.messages.formatter.markdown;

import hera.core.messages.formatter.IDefaultStrings;

import java.util.function.Function;

public enum Markdown implements IDefaultStrings {
	BOLD("**", MarkdownHelper::makeBold),
	ITALICS1("_", MarkdownHelper::makeItalics1),
	ITALICS2("*", MarkdownHelper::makeItalics2),
	UNDERLINE("__", MarkdownHelper::underline),
	STRIKE_THROUGH("~~", MarkdownHelper::strikeThrough),
	SINGLE_LINE_CODE("`", MarkdownHelper::makeSingleLineCodeBlock),
	MULTI_LINE_CODE("```", MarkdownHelper::makeMultiLineCodeBlock),
	SINGLE_LINE_BLOCK_QUOTE(">", MarkdownHelper::makeSingleLineBlockQuote),
	MULTI_LINE_BLOCK_QUOTE(">>>", MarkdownHelper::makeMultiLineBlockQuote);

	private final String str;
	private final Function<String, String> applier;

	Markdown(String str, Function<String, String> applier) {
		this.str = str;
		this.applier = applier;
	}

	public String getStr() {
		return str;
	}

	public String apply(String s) {
		return applier.apply(s);
	}

	@Override
	public String toString() {
		return str;
	}

}
