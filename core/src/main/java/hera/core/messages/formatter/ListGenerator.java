package hera.core.messages.formatter;

import hera.core.messages.formatter.list.FormatingList;
import hera.core.messages.formatter.list.ListFormatNode;
import hera.core.messages.formatter.list.ParameterList;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

// think about changing this from all static methods
public class ListGenerator {
	public static <T> String makeList(String format, List<T> items, Function<T, List<String>> convertToParameter) {
		List<ListFormatNode> nodes = FormatingList.getNodeList(format);
		List<List<String>> itemsOut = items.stream().map(i -> convertToParameter.apply(i)).collect(Collectors.toList());
		return makeList(nodes, itemsOut);
	}


	public static <T> String makeList(String format, List<List<String>> items) {
		List<ListFormatNode> nodes = FormatingList.getNodeList(format);
		return makeList(nodes, items);
	}

	public static <T> String makeList(String format, List<T> items, Function<T, String>... convertToParameter) {
		List<ListFormatNode> nodes = FormatingList.getNodeList(format);
		if (convertToParameter.length != FormatingList.getVariableCount(nodes)) throw new RuntimeException("The amount of variables isn't the amount of inputs");
		List<List<String>> itemsOut = items.stream().map(i -> Arrays.asList(convertToParameter).stream().map(f -> f.apply(i)).collect(Collectors.toList())).collect(Collectors.toList());
		return makeList(nodes, itemsOut);
	}

	public static <T> String makeList(String format, List<T> items, ParameterList<T> converterList) {
		List<Function<T, String>> convertToParameter = converterList.getList();
		List<ListFormatNode> nodes = FormatingList.getNodeList(format);
		if (convertToParameter.size() != FormatingList.getVariableCount(nodes)) throw new RuntimeException("The amount of variables isn't the amount of inputs");
		List<List<String>> itemsOut = items.stream().map(i -> convertToParameter.stream().map(f -> f.apply(i)).collect(Collectors.toList())).collect(Collectors.toList());
		return makeList(nodes, itemsOut);
	}

	private static String makeList(List<ListFormatNode> nodes, List<List<String>> items) {
		StringBuilder sb = new StringBuilder();
		for (List<String> arguments : items) {
			int argumentPointer = 0;
			boolean optionalMissing = false;
			for (int j = 0; j < nodes.size(); j++) {
				ListFormatNode node = nodes.get(j);
				if (!node.isOptional()) {  // if node is required
					if (node.isVariable()) {
						sb.append(node.getOutput(arguments.get(argumentPointer++)));
					} else {
						sb.append(node.getOutput());
					}
				} else if (!optionalMissing) {  // if node is optional
					if (argumentPointer < arguments.size() && !(arguments.get(argumentPointer) == null || arguments.get(argumentPointer).isEmpty())) {  //if optional parameter is here
						sb.append(node.getOutput(arguments.get(argumentPointer++)));
					} else {  // if it isn't
						optionalMissing = true;
					}
				}
			}
		}
		return sb.toString();
	}
}
