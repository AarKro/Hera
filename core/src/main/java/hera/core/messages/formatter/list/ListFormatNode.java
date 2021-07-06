package hera.core.messages.formatter.list;

public abstract class ListFormatNode {
	private boolean variable;
	private boolean optional;

	public ListFormatNode(boolean variable, boolean optional) {
		this.variable = variable;
		this.optional = optional;
	}

	public String getOutput(String... input) {
		if ((variable && input.length != 1) || (!variable && input.length != 0)) throw new RuntimeException("wrong parameter count");
		return makeOutput(input);
	}

	//This is not totally clean but since there is no optional parameters in java and every node that doesnt need an input can still take a dummy input this will do
	protected abstract String makeOutput(String... input);

	public boolean isVariable() {
		return variable;
	}

	public boolean isOptional() {
		return optional;
	}
}
