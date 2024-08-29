package org.gaslang.script.lib.boot;

import org.gaslang.script.FunctionValue;
import org.gaslang.script.Iterable;
import org.gaslang.script.Tuple;
import org.gaslang.script.Value;
import org.gaslang.script.lib.annotation.GasFunction;
import org.gaslang.script.lib.annotation.GasType;

import java.util.ArrayList;
import java.util.HashMap;

import static org.gaslang.script.api.ScriptAPI.array;
import static org.gaslang.script.api.ScriptAPI.tuple;

@GasType
public class List implements Iterable
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

	@Override
	public HashMap<?, ?> getMap() {
		return array(values.toArray(new Value[0])).getMap();
	}
}
