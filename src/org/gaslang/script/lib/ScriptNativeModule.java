package org.gaslang.script.lib;

import org.gaslang.script.Value;
import org.gaslang.script.ValueType;
import org.gaslang.script.api.ScriptModule;
import org.gaslang.script.run.GasRuntime;

public class ScriptNativeModule	extends ScriptModule implements NativeObject
{
	private final boolean isShared;
	
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
		
		if (value instanceof NativeFieldValue field) {
			field.set(arg1);
			return arg1;
		}
		
		throw new RuntimeException();
	}

	@Override
	public Value<?> index(Value<?> arg0) {
		arg0.matchValueTypeOrThrow(ValueType.STRING);
		
		Value<?> value = jValue().get(arg0.asString());
		
		if (value instanceof NativeFieldValue field) {
			return field.get();
		}
		
		return value;
	}

	@Override
	public String name() {
		return super.getName();
	}
}
