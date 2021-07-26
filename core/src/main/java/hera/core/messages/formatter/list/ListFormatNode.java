package hera.core.messages.formatter.list;

public abstract class ListFormatNode {
	private int parameterCount;
	private boolean optional;

	public ListFormatNode(int parameterCount, boolean optional) {
		this.parameterCount = parameterCount;
		this.optional = optional;
	}

	public String getOutput(String... input) {
		if (parameterCount != input.length) throw new RuntimeException("Wrong parameter count");
		return makeOutput(input);
	}

	protected abstract String makeOutput(String... input);

	public int getParameterCount() {
		return parameterCount;
	}

	public boolean isOptional() {
		return optional;
	}
}
