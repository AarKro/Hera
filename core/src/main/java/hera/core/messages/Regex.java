package hera.core.messages;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Regex {
	public static class RegexMatch {
		private int start;
		private int end;
		private boolean hasMatch;

		public boolean hasMatch() {
			return hasMatch;
		}

		public int getStart() {
			return start;
		}

		public int getEnd() {
			return end;
		}
	}

	public static RegexMatch getMatch(String find, String text) {
		Pattern regex = Pattern.compile(find);
		Matcher matcher = regex.matcher(text);
		matcher.reset();
		RegexMatch out = new RegexMatch();
		out.hasMatch = matcher.find();
		matcher.reset();
		out.hasMatch = matcher.find();
		if (out.hasMatch) {
			out.start = matcher.start();
			out.end = matcher.end();
		} else {
			out.start = -1;
			out.end = -1;
		}
		return out;
	}

}
