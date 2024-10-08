package org.gaslang.script.lib.boot;

import org.gaslang.script.*;
import org.gaslang.script.lib.annotation.GasFunction;
import org.gaslang.script.lib.annotation.GasModule;
import org.gaslang.script.lib.annotation.GasVariable;

import java.util.Optional;

import static org.gaslang.script.api.ScriptAPI.*;

@GasModule(name = "std", shared = true)
public class GlobalModule
{
	@GasVariable
	public char endl = '\n';

	@GasVariable
	public TableValue _g = table();
	 
	@GasFunction
	public void print(Tuple tuple) {
		System.out.print(tuple.concat(" "));
	}

	@GasFunction
	public void println(Tuple tuple) {
		System.out.println(tuple.concat(" "));
	}
	
	@GasFunction
	public NumberValue sizeOf(Lengthable lengthable) {
		return number(lengthable.getSize());
	}

	@GasFunction
	public StringValue scopeOf(FunctionValue value) {
		return string(value.localStack.toString());
	}
	
	@GasFunction
	public StringValue classOf(Value<?> value) {
		return string(value.getClass().getSimpleName());
	}
	
	@GasFunction
	public StringValue typeOf(Value<?> value) {
		return string(value.getValueType().name());
	}

	@GasFunction
	public ScriptValue require(String fileName) {
		return new ScriptValue(GasScript.loadScript(fileName));
	}

	@GasFunction
	public ArrayValue range(Optional<Integer> startn, Optional<Integer> endn) {
		int start = startn.orElse(0);
		int end = endn.orElse(start);

		if (endn.isEmpty()) start = 0;
		
		Number[] array = new Number[Math.abs(end-start)];
		
		for (int i = 0; start != end; i++, start++) {
			array[i] = start;
		}
		
		return array(array);
	}
}
