package hera.core.messages;

import java.util.ArrayList;
import java.util.List;

class FormatingList {

	public static final String OPTIONAL_STRING = "$$";
	public static final String VARIABLE_STRING = "%s";

	protected interface FormatNode {
		public boolean isVariable();
		//This is not totally clean but since there is no optional parameters in java and every node that doesnt need an input can still take a dummy input this will do
		public String getOutput(String input);
	}

	protected static class TextNode implements FormatNode {
		String value;

		public TextNode(String value) {
			this.value = value;
		}

		@Override
		public boolean isVariable() {
			return false;
		}

		@Override
		public String getOutput(String input) {
			return value;
		}
	}

	protected static class OptionalNode implements FormatNode {
		String before;
		String after;

		public OptionalNode(String before, String after) {
			this.before = before;
			this.after = after;
		}

		@Override
		public boolean isVariable() {
			return true;
		}

		@Override
		public String getOutput(String input) {
			StringBuilder sb = new StringBuilder();
			sb.append(before);
			sb.append(input);
			if (after != null && !after.isEmpty()) sb.append(after);
			return sb.toString();
		}
	}

	protected static class VariableNode implements FormatNode {
		@Override
		public boolean isVariable() {
			return true;
		}

		@Override
		public String getOutput(String input) {
			return input;
		}
	}

	public static List<FormatNode> getNodeList(String format) {
		List<FormatNode> out = new ArrayList<>();
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
			String[] optionalParameterSplit = optionalParameter.split("%s");
			if (optionalParameterSplit.length > 3) {
				throw new RuntimeException("Some idiot fucked up the optional parameter definition");
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

	public static int getVariableCount(List<FormatNode> list) {
		int variableCount = 0;
		for (FormatNode node : list) {
			if (node.isVariable()) variableCount++;
		}
		return variableCount;
	}

}
