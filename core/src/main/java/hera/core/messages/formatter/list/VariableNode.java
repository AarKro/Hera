package hera.core.messages.formatter.list;

public class VariableNode extends hera.core.messages.formatter.list.ListFormatNode {
	public VariableNode() {
		super(true, false);
	}

	@Override
	public boolean isVariable() {
		return true;
	}

	@Override
	protected String makeOutput(String... input) {
		return input[0];
	}
}
