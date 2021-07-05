package hera.core.messages;

import com.google.common.base.Function;
import hera.database.entities.Command;

import java.util.*;

import static hera.store.DataStore.STORE;

public class MessageFormatter {
	public static <T> String makeList(String format, List<T> items, Function<T, List<String>> convertToParameter) {
		StringBuilder sb = new StringBuilder();
		List<FormatingList.FormatNode> nodes = FormatingList.getNodeList(format);
		for (int i = 0; i < items.size(); i++) {
			List<String> arguments = convertToParameter.apply(items.get(i));
			int argumentPointer = 0;
			boolean optionalMissing = false;
			for (int j = 0; j < nodes.size(); j++) {
				FormatingList.FormatNode node = nodes.get(j);
				if (node instanceof FormatingList.TextNode) {
					sb.append(node.getOutput(""));
				} else if (node instanceof FormatingList.VariableNode) {
					sb.append(node.getOutput(arguments.get(argumentPointer++)));
				} else if (node instanceof FormatingList.OptionalNode && !optionalMissing) {
					if (argumentPointer < arguments.size()) {
						sb.append(node.getOutput(arguments.get(argumentPointer++)));
					} else {
						optionalMissing = true;
					}
				}
			}
		}
		return sb.toString();
	}

	public static <T> String makeList(String format, List<List<String>> items) {
		StringBuilder sb = new StringBuilder();
		List<FormatingList.FormatNode> nodes = FormatingList.getNodeList(format);
		for (List<String> arguments : items) {
			int argumentPointer = 0;
			boolean optionalMissing = false;
			for (int j = 0; j < nodes.size(); j++) {
				FormatingList.FormatNode node = nodes.get(j);
				if (node instanceof FormatingList.TextNode) {
					sb.append(node.getOutput(""));
				} else if (node instanceof FormatingList.VariableNode) {
					sb.append(node.getOutput(arguments.get(argumentPointer++)));
				} else if (node instanceof FormatingList.OptionalNode && !optionalMissing) {
					if (argumentPointer < arguments.size()) {
						sb.append(node.getOutput(arguments.get(argumentPointer++)));
					} else {
						optionalMissing = true;
					}
				}
			}
		}
		return sb.toString();
	}

	public static <T> String makeList(String format, List<T> items, Function<T, String>... convertToParameter) {
		StringBuilder sb = new StringBuilder();
		List<FormatingList.FormatNode> nodes = FormatingList.getNodeList(format);
		if (convertToParameter.length != FormatingList.getVariableCount(nodes)) throw new RuntimeException("The amount of variables isn't the amount of inputs");
		for (int i = 0; i < items.size(); i++) {
			int argumentPointer = 0;
			for (int j = 0; j < nodes.size(); j++) {
				FormatingList.FormatNode node = nodes.get(j);
				if (node instanceof FormatingList.TextNode) {
					sb.append(node.getOutput(""));
				} else if (node instanceof FormatingList.VariableNode) {
					sb.append(node.getOutput(convertToParameter[argumentPointer++].apply(items.get(i))));
				} else if (node instanceof FormatingList.OptionalNode) {
					String parameter = convertToParameter[argumentPointer++].apply(items.get(i));
					if (parameter != null && !parameter.isEmpty())
						sb.append(node.getOutput(parameter));
				}
			}
		}
		return sb.toString();
	}
}
