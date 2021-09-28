package hera.core.messages.formatter.list;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.Function;

public class ListMaker<T> {
	private List<T> items = new ArrayList();
	private String lineDelimiter = "\n";
	private List<LineFormatter<T>> specialFormats = new ArrayList<>();
	private String format;
	private BiFunction<Integer, T, List<Object>> argumentMaker;

	public static List<Object> argumentMaker(Object... arguments) {
		return new ArrayList<>(Arrays.asList(arguments));
	}

	public static class LineFormatter<K> {
		public LineFormatter(BiPredicate<Integer, K> testIfFormat, Function<String, String> format) {
			this.testIfFormat = testIfFormat;
			this.format = format;
		}

		BiPredicate<Integer, K> testIfFormat;
		Function<String, String> format;
	}

	public ListMaker(List<T> items) {
		this.items = items;
	}

	public ListMaker(String format, BiFunction<Integer, T, List<Object>> argumentMaker) {
		this.format = format;
		this.argumentMaker = argumentMaker;
	}

	public ListMaker(List<T> items, String format, BiFunction<Integer, T, List<Object>> argumentMaker) {
		this.items = items;
		this.format = format;
		this.argumentMaker = argumentMaker;
	}

	public ListMaker<T> addSpecialFormat(BiPredicate<Integer, T> testIfFormat, Function<String, String> format) {
		specialFormats.add(new LineFormatter<>(testIfFormat, format));
		return this;
	}

	public ListMaker<T> setItems(List<T> items) {
		this.items = items;
		return this;
	}

	public ListMaker<T> setLineDelimiter(String lineDelimiter) {
		this.lineDelimiter = lineDelimiter;
		return this;
	}

	public ListMaker<T> setSpecialFormats(List<LineFormatter<T>> specialFormats) {
		this.specialFormats = specialFormats;
		return this;
	}

	public ListMaker<T> setFormat(String format) {
		this.format = format;
		return this;
	}

	public ListMaker<T> setArgumentMaker(BiFunction<Integer, T, List<Object>> argumentMaker) {
		this.argumentMaker = argumentMaker;
		return this;
	}

	public String makeList() {
		List<String> lines = new ArrayList<>();
		for (int i = 0; i < items.size();i++) {
			List<Object> args = argumentMaker.apply(i, items.get(i));
			String line = format(format, args);
			for (LineFormatter<T> formatter : specialFormats) {
				if (formatter.testIfFormat.test(i, items.get(i))) line = formatter.format.apply(line);
			}
			lines.add(line);
		}
		return String.join(lineDelimiter, lines);
	}

	private String format(String format, List<Object> arguments) {
		String[] parameters = format.split("(?!=[^\\\\]\\\\)\\$\\$");

		int argCount = 0;
		for (int i = 0; i<parameters.length; i++) {

			//this replaces the escape sequences you may use for the optional parameters with the intended values
			parameters[i] = parameters[i].replaceAll("\\\\$", "\\").replaceAll("\\\\\\$\\$", "$$");

			long argsInSplit = parameters[i].replace("%%", "").chars().filter(ch -> ch == '%').count();
			if (i%2==1) {
				if (argsInSplit != 1) throw new RuntimeException("Wrong use of the optional parameter. Optional parameter must have exactly 1 argument encased in it");
				Object arg = arguments.get(argCount);
				if (arg == null) {
					parameters[i] = "";
					arguments.remove(argCount);
					argsInSplit = 0;
				} else {
					arguments.set(argCount, String.format(parameters[i], arguments.get(argCount)));
					parameters[i] = "%s";
				}
			}
			argCount += argsInSplit;
		}

		return String.format(String.join("", parameters), arguments.toArray());
	}

}
