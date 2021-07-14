package hera.core.messages.formatter.list;

import hera.core.messages.formatter.list.format.converter.FormatingList;
import hera.core.messages.formatter.list.format.converter.NodeConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.function.Function;

public class ListGen<T> {
	private static final Logger LOG = LoggerFactory.getLogger(ListGen.class);

	public static final class ItemWrapper<T> {
		private final T item;
		private final int index;

		public ItemWrapper(T item, int index) {
			this.item = item;
			this.index = index;
		}

		public T getItem() {
			return item;
		}

		public int getIndex() {
			return index;
		}
	}

	//required
	private List<ListFormatNode> nodes;
	private List<T> items;
	private ParameterList<ItemWrapper<T>> itemConverters;
	private String lineBreak = "\n";

	//optional
	private HashMap<Function<ItemWrapper<T>, Boolean>, List<Function<String, String>>> specialLineFormats;
	private static final NodeConverter DEFAULT_NODE_CONVERTER = new FormatingList();

	public ListGen() {
		nodes = new ArrayList<>();
		items = new ArrayList<>();
		itemConverters = new ParameterList<ItemWrapper<T>>();
		specialLineFormats = new HashMap<>();
	}

	public ListGen(List<ListFormatNode> nodes, List<T> items, ParameterList<ItemWrapper<T>> itemConverters) {
		this();
		this.nodes = nodes;
		this.items = items;
		this.itemConverters = itemConverters;
	}

	public ListGen(String format, List<T> items, ParameterList<ItemWrapper<T>> itemConverters) {
		this(format, DEFAULT_NODE_CONVERTER, items, itemConverters);
	}

	public ListGen(String format, NodeConverter nodeConverter, List<T> items, ParameterList<ItemWrapper<T>> itemConverters) {
		this(nodeConverter.getNodeList(format), items, itemConverters);
	}

	public ListGen<T> setNodes(List<ListFormatNode> nodes) {
		this.nodes = nodes;
		return this;
	}

	public ListGen<T> setNodes(String format) {
		return setNodes(format, DEFAULT_NODE_CONVERTER);
	}

	public ListGen<T> setNodes(String format, NodeConverter nodeConverter) {
		return setNodes(nodeConverter.getNodeList(format));
	}

	public ListGen<T> setItems(List<T> items) {
		this.items = items;
		return this;
	}

	public ListGen<T> addItem(T item) {
		this.items.add(item);
		return this;
	}

	public ListGen<T> addItems(T... items) {
		return addItems(Arrays.asList(items));
	}

	public ListGen<T> addItems(List<T> items) {
		this.items.addAll(items);
		return this;
	}

	public ListGen<T> setItemConverters(ParameterList<ItemWrapper<T>> itemConverters) {
		this.itemConverters = itemConverters;
		return this;
	}

	public ListGen<T> addItemWrapperConverter(Function<ItemWrapper<T>, String> itemConverter) {
		this.itemConverters.add(itemConverter);
		return this;
	}

	public ListGen<T> addItemWrapperConverters(Function<ItemWrapper<T>, String>[] itemConverters) {
		for (Function<ItemWrapper<T>, String> itemConverter : itemConverters) this.itemConverters.add(itemConverter);
		return this;
	}

	public ListGen<T> addItemWrapperConverters(List<Function<ItemWrapper<T>, String>> itemConverters) {
		for (Function<ItemWrapper<T>, String> itemConverter : itemConverters) this.itemConverters.add(itemConverter);
		return this;
	}

	public ListGen<T> addItemConverter(Function<T, String> itemConverter) {
		this.itemConverters.add(iw -> itemConverter.apply(iw.getItem()));
		return this;
	}

	public ListGen<T> addItemConverters(Function<T, String>[] itemConverters) {
		for (Function<T, String> itemConverter : itemConverters) this.itemConverters.add(iw -> itemConverter.apply(iw.getItem()));
		return this;
	}

	public ListGen<T> addItemConverters(List<Function<T, String>> itemConverters) {
		for (Function<T, String> itemConverter : itemConverters) this.itemConverters.add(iw -> itemConverter.apply(iw.getItem()));
		return this;
	}

	public ListGen<T> addIndexConverter(Function<Integer, String> itemConverter) {
		this.itemConverters.add(iw -> itemConverter.apply(iw.getIndex()));
		return this;
	}

	public ListGen<T> addIndexConverters(Function<Integer, String>[] itemConverters) {
		for (Function<Integer, String> itemConverter : itemConverters) this.itemConverters.add(iw -> itemConverter.apply(iw.getIndex()));
		return this;
	}

	public ListGen<T> addIndexConverters(List<Function<Integer, String>> itemConverters) {
		for (Function<Integer, String> itemConverter : itemConverters) this.itemConverters.add(iw -> itemConverter.apply(iw.getIndex()));
		return this;
	}

	public ListGen<T> addSpecialLineFormat(Function<ItemWrapper<T>, Boolean> applySpecialLineFormat, Function<String, String>... specialLineFormats) {
		this.specialLineFormats.put(applySpecialLineFormat, Arrays.asList(specialLineFormats));
		return this;
	}

	public ListGen<T> setLineBreak(String lineBreak) {
		this.lineBreak = lineBreak;
		return this;
	}

	public ListGen<T> sortItems(Comparator<T> comparator) {
		items.sort(comparator);
		return this;
	}

	public String makeList() {
		if (nodes.isEmpty() || items.isEmpty() || itemConverters.getList().isEmpty()) throw new RuntimeException("Generator Object was constructed incorrectly");
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < items.size(); i++) {
			List<String> arguments = new ArrayList<>();
			ItemWrapper item = new ItemWrapper<>(items.get(i), i);
			itemConverters.getList().forEach(f -> arguments.add(f.apply(item)));
			String line = makeLine(arguments);
			if (!specialLineFormats.isEmpty()) {
				Set<Map.Entry<Function<ItemWrapper<T>, Boolean>, List<Function<String, String>>>> singleLineFormatEntries = specialLineFormats.entrySet();
				for (Map.Entry<Function<ItemWrapper<T>, Boolean>, List<Function<String, String>>> singleLineFormatEntry : singleLineFormatEntries) {
					if (singleLineFormatEntry.getKey().apply(item)) {
						for (Function<String, String> singleLineFormat : singleLineFormatEntry.getValue()) line = singleLineFormat.apply(line);
					}
				}
			}
			sb.append(line);
			sb.append(lineBreak);
		}
		return sb.toString();
	}

	 private String makeLine(List<String> arguments) {
		StringBuilder sb = new StringBuilder();
		int argumentPointer = 0;
		boolean optionalMissing = false;
		for (int i = 0; i < nodes.size(); i++) {
			ListFormatNode node = nodes.get(i);
			if (node.isOptional()) if (argumentPointer + node.getParameterCount() >= arguments.size() || (arguments.get(argumentPointer) == null)) optionalMissing = true;
			if (!node.isOptional() || !optionalMissing) {
				int paramCount = node.getParameterCount();
				sb.append(node.getOutput(getArgumentsFromList(arguments, argumentPointer, paramCount)));
				argumentPointer += paramCount;
			}
		}
		return sb.toString();
	}

	private static String[] getArgumentsFromList(List<String> arguments, int pointer, int parameterCount) {
		String[] out = new String[parameterCount];
		for (int i = 0; i < parameterCount ; i++) {
			out[i] = arguments.get(pointer + i);
		}
		return out;
	}

	public static String makeListSimple(String format, List<List<String>> items) {
		return makeListSimple(format, DEFAULT_NODE_CONVERTER, items);
	}

	public static String makeListSimple(String format, NodeConverter converter, List<List<String>> items) {
		List<ListFormatNode> nodes = converter.getNodeList(format);
		return makeListSimple(nodes, items);
	}

	public static String makeListSimple(List<ListFormatNode> nodes, List<List<String>> items) {
		StringBuilder sb = new StringBuilder();
		for (List<String> arguments : items) {
			int argumentPointer = 0;
			boolean optionalMissing = false;
			for (int i = 0; i < nodes.size(); i++) {
				ListFormatNode node = nodes.get(i);
				if (node.isOptional())
					if (argumentPointer + node.getParameterCount() >= arguments.size() || (arguments.get(argumentPointer) == null))
						optionalMissing = true;
				if (!node.isOptional() || !optionalMissing) {
					int paramCount = node.getParameterCount();
					sb.append(node.getOutput(getArgumentsFromList(arguments, argumentPointer += paramCount, paramCount)));
				}
			}
		}
		return sb.toString();
	}

	@Override
	public String toString() {
		//TODO check if this is actually the case
		LOG.warn("While to String might work it should not be done. Please change the way the list is generated to use the makeList() method", new Throwable("Warning: ToString call on ListGen"));
		return makeList();
	}
}
