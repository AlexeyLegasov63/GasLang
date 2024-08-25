package org.gaslang.script.lib;

import java.util.*;

import org.gaslang.script.FunctionBlock;
import org.gaslang.script.NullValue;
import org.gaslang.script.Tuple;
import org.gaslang.script.Value;
import org.gaslang.script.ValueType;
import org.gaslang.script.ast.*;
import org.gaslang.script.run.*;

public class NativeFunctionValue extends Value<FunctionBlock>
{
	private final String name;
	
	public NativeFunctionValue(String name, FunctionBlock basicBlock) {
		super(basicBlock, ValueType.FUNCTION);
		this.name = name;
	}
	public NativeFunctionValue(FunctionBlock basicBlock) {
		this("Anonymous", basicBlock);
	}

	@Override
	public Value<?> call(Tuple args) {
		try {
			Value<?> result = jValue().execute(args);
			return result == null ? NullValue.NIL_VALUE : result;
		} catch(Throwable t) {
			t.printStackTrace();
			return NullValue.NIL_VALUE;
		}
	}

	public String getName() {
		return name;
	}
}
