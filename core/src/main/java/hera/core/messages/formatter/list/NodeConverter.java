package hera.core.messages.formatter.list;

import java.util.List;

public interface NodeConverter {
	List<ListFormatNode> getNodeList(String format);
}
