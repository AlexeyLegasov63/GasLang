package org.gaslang.script.lib.boot;

import org.gaslang.script.*;
import org.gaslang.script.lib.annotation.GasFunction;
import org.gaslang.script.lib.annotation.GasModule;
import org.gaslang.script.lib.nativetypes.StrictArrayValue;

import java.util.Optional;

import static org.gaslang.script.api.ScriptAPI.*;

@GasModule(name = "array")
public class ArrayModule
{
	@GasFunction
	public NumberValue size(ArrayValue array) {
		return number(array.getSize());
	}

	@GasFunction
	public StrictArrayValue strict(Tuple tuple) {
		Value<?>[] array = tuple.getValuesAsArray(tuple.hasValue("len") ? tuple.getValue("len").asNumber().intValue() : -1);
		return new StrictArrayValue(array);
	}
	
	@GasFunction
	public void clear(Array array) {
		fill(array, Optional.empty());
	}
	
	@GasFunction
	public void fill(Array raw, Optional<Value<?>> value) {
		
		if (raw instanceof StrictArrayValue) {
			Value<?>[] array = ((StrictArrayValue)raw).jValue();
			
			for (int i = 0, l = array.length; i < l; i++) {
				array[i] = value.orElse(NULL);
			}
			
			return;
		}
		
		ArrayValue array = (ArrayValue)raw;
		
		array.getMap().keySet()
			.forEach(k -> array.getMap()
					.replace(k, value.orElse(NULL)));
	}
	
	@GasFunction
	public void forEach(Array array, FunctionValue iterator) {
		array.getMap().keySet()
			.forEach(index -> iterator.call(tuple(value(index), value(array.getMap().get(index)))));
	}
}
