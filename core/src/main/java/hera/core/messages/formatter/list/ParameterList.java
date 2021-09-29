package hera.core.messages.formatter.list;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class ParameterList<T> {
	List<Function<T, String>> parameters;

	public ParameterList() {
		parameters = new ArrayList<>();
	}

	public ParameterList(Function<T, String> function) {
		this();
		parameters.add(function);
	}

	private ParameterList(List<Function<T, String>> parameters, Function<T, String> function) {
		this.parameters = parameters;
		this.parameters.add(function);
	}

	public ParameterList<T> add(Function<T, String> function) {
		parameters.add(function);
		return this;
	}


	public List<Function<T, String>> getList() {
		return parameters;
	}
}
