package hera.core.messages.formatter.list.format.converter;

import hera.core.messages.formatter.list.ListFormatNode;

import java.util.List;

public interface NodeConverter {
	List<ListFormatNode> getNodeList(String format);
}
