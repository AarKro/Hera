package hera.core.messages.formatter.list.format.nodes;

public class TextNode extends hera.core.messages.formatter.list.ListFormatNode {
	String value;

	public TextNode(String value)
	{
		super(0, false);
		this.value = value;
	}


	@Override
	protected String makeOutput(String... input) {
		return value;
	}
}
