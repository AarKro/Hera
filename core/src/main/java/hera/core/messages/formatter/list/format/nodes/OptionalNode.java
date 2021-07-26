package hera.core.messages.formatter.list.format.nodes;

public class OptionalNode extends hera.core.messages.formatter.list.ListFormatNode {
	String before;
	String after;

	public OptionalNode(String before, String after) {
		super(1, true);
		this.before = before;
		this.after = after;
	}

	@Override
	protected String makeOutput(String... input) {
		if (input.length != 1) throw new RuntimeException("wrong number of parameters");
		StringBuilder sb = new StringBuilder();
		sb.append(before);
		sb.append(input[0]);
		if (after != null && !after.isEmpty()) sb.append(after);
		return sb.toString();
	}


}
