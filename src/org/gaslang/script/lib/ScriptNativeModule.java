package org.gaslang.script.lib;

import org.gaslang.script.*;
import org.gaslang.script.api.*;
import org.gaslang.script.run.GasRuntime;

public class ScriptNativeModule	extends ScriptModule implements NativeObject
{
	private boolean isShared;
	
	public ScriptNativeModule(String name, boolean shared) {
		super(name);
		
		isShared = shared;
	}

	@Override
	public void set(String fieldName, Value<?> value) {
		if (isShared) {
			GasRuntime.GLOBAL_VALUES.put(fieldName, value);
		}
		super.set(fieldName, value);
	}

	@Override
	public Value<?> set(Value<?> arg0, Value<?> arg1) {
		arg0.matchValueTypeOrThrow(ValueType.STRING);
		
		Value<?> value = jValue().get(arg0.asString());
		
		if (value instanceof NativeFieldValue) {
			NativeFieldValue field = (NativeFieldValue)value;
			field.set(arg1);
			return arg1;
		}
		
		throw new RuntimeException();
	}

	@Override
	public Value<?> index(Value<?> arg0) {
		arg0.matchValueTypeOrThrow(ValueType.STRING);
		
		Value<?> value = jValue().get(arg0.asString());
		
		if (value instanceof NativeFieldValue) {
			NativeFieldValue field = (NativeFieldValue)value;
			return field.get();
		}
		
		return value;
	}
}