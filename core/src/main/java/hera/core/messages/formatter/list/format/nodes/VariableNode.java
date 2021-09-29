package hera.core.messages.formatter.list.format.nodes;

public class VariableNode extends hera.core.messages.formatter.list.ListFormatNode {
	public VariableNode() {
		super(1, false);
	}

	@Override
	protected String makeOutput(String... input) {
		return input[0];
	}
}
