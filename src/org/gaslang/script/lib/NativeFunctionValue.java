package org.gaslang.script.lib;

import org.gaslang.script.*;

import java.util.Arrays;

public class NativeFunctionValue extends Value<FunctionBlock>
{
	private final String module, name;
	
	public NativeFunctionValue(String module, String name, FunctionBlock basicBlock) {
		super(basicBlock, ValueType.FUNCTION);
		this.name = name;
		this.module = module;
	}
	public NativeFunctionValue(FunctionBlock basicBlock) {
		this("", "Anonymous", basicBlock);
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

	@Override
	public String toString() {
		var namespace = module.isEmpty() ? "" : module + ":";
		return String.format(namespace + "%s(%s)", name, super.value.desiredArgs());
	}
	@Override
	public String asString() {
		return toString();
	}
}
