package org.gaslang.script.lib.boot;

import static org.gaslang.script.api.ScriptAPI.*;

import java.util.*;
import java.util.function.Predicate;

import org.gaslang.script.*;
import org.gaslang.script.lib.*;
import org.gaslang.script.lib.annotation.*;

@GasType
public class List
{
	public ArrayList<Value<?>> values;
	
	@GasFunction
	public void constructor(Tuple args) {
		values = args.getValues();
	}

	@GasFunction
	public Integer size() {
		return values.size();
	}

	@GasFunction
	public Boolean isEmpty() {
		return values.isEmpty();
	}

	@GasFunction
	public String toString() {
		return values.toString();
	}

	@GasFunction
	public Value<?> get(Integer index) {
		return values.get(index);
	}

	@GasFunction
	public Value<?> set(Integer index, Value<?> element) {
		return values.set(index, element);
	}

	@GasFunction
	public Boolean add(Tuple args) {
		return values.addAll(args.getValues());
	}

	@GasFunction
	public void remove(Integer index) {
		values.remove(index.intValue());
	}
	
	@GasFunction
	public Boolean removeIf(FunctionValue filter) {
		return values.removeIf((value) -> {
			return filter.call(Tuple.valueOf(value)).asBoolean();
		});
	}

	@GasFunction
	public void forEach(FunctionValue function) {
		values.forEach(v -> function.call(tuple(v)));
	}
}
