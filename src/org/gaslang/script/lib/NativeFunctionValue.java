package org.gaslang.script.lib;

import org.gaslang.script.*;

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
