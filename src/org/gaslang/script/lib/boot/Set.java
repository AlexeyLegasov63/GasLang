package org.gaslang.script.lib.boot;

import org.gaslang.script.FunctionValue;
import org.gaslang.script.Iterable;
import org.gaslang.script.Tuple;
import org.gaslang.script.Value;
import org.gaslang.script.lib.annotation.GasFunction;
import org.gaslang.script.lib.annotation.GasType;

import java.util.HashMap;
import java.util.HashSet;

import static org.gaslang.script.api.ScriptAPI.array;
import static org.gaslang.script.api.ScriptAPI.tuple;

@GasType
public class Set implements Iterable
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
	public void clear() {
		values.clear();
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

	@Override
	public HashMap<?, ?> getMap() {
		return array(values.toArray(new Value[0])).getMap();
	}
}
