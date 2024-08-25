package org.gaslang.script.lib.boot;

import static org.gaslang.script.api.ScriptAPI.*;

import java.util.*;
import java.util.function.Predicate;

import org.gaslang.script.*;
import org.gaslang.script.lib.*;
import org.gaslang.script.lib.annotation.*;

@GasType
public class Set
{
	public HashSet<Value<?>> values;
	
	@GasFunction
	public void constructor(Tuple args) {
		values = new HashSet<>(args.getValues());
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
	public Boolean add(Tuple args) {
		return values.addAll(args.getValues());
	}

	@GasFunction
	public Boolean contains(Value<?> index) {
		return values.contains(index);
	}

	@GasFunction
	public Boolean remove(Value<?> index) {
		return values.remove(index);
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
