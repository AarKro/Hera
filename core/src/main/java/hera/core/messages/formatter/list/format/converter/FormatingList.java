package hera.core.messages.formatter.list.format.converter;


import hera.core.messages.formatter.list.ListFormatNode;
import hera.core.messages.formatter.list.format.nodes.OptionalNode;
import hera.core.messages.formatter.list.format.nodes.TextNode;
import hera.core.messages.formatter.list.format.nodes.VariableNode;

import java.util.ArrayList;
import java.util.List;

public class FormatingList implements NodeConverter {

	public static final String OPTIONAL_STRING = "$$";
	public static final String VARIABLE_STRING = "%s";

	public List<ListFormatNode> getNodeList(String format) {
		List<hera.core.messages.formatter.list.ListFormatNode> out = new ArrayList<>();
		String[] splitFormat = format.split("\\$\\$");

		String neededParameters = splitFormat[0];
		List<String> optionalParameters = new ArrayList<>();
		String afterParameter = null;
		if (splitFormat.length > 1) {
			boolean hasAfterString = format.lastIndexOf(OPTIONAL_STRING) < format.length() - OPTIONAL_STRING.length();
			int optionalParameterEnd = splitFormat.length;
			if (hasAfterString) {
				optionalParameterEnd--;
				afterParameter = splitFormat[optionalParameterEnd];
			}
			for (int i = 1; i < optionalParameterEnd; i++) {
				optionalParameters.add(splitFormat[i]);
			}
		}
		String[] neededParametersSplit = neededParameters.split("(?=" + VARIABLE_STRING + ")|(?<=" + VARIABLE_STRING + ")");
		for (int i = 0; i < neededParametersSplit.length; i++) {
			if (neededParametersSplit[i].equals(VARIABLE_STRING)) {
				out.add(new VariableNode());
			} else {
				out.add(new TextNode(neededParametersSplit[i]));
			}
		}
		for (String optionalParameter : optionalParameters) {
			String[] optionalParameterSplit = optionalParameter.split(VARIABLE_STRING);
			if (optionalParameterSplit.length > 3) {
				throw new RuntimeException("More than one variable in an optional parameter");
			} else if (optionalParameterSplit.length == 1) {
				out.add(new OptionalNode(optionalParameterSplit[0], ""));
			} else {
				out.add(new OptionalNode(optionalParameterSplit[0], optionalParameterSplit[1]));
			}
		}
		if (afterParameter != null) {
			out.add(new TextNode(afterParameter));
		}
		return out;

	}

	public int getVariableCount(List<ListFormatNode> list) {
		int variableCount = 0;
		for (hera.core.messages.formatter.list.ListFormatNode node : list) {
			if (node.getParameterCount() > 0) variableCount++;
		}
		return variableCount;
	}

}
