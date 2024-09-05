package org.gaslang.script.lib;

import org.gaslang.script.Iterable;
import org.gaslang.script.ObjectValue;
import org.gaslang.script.Tuple;
import org.gaslang.script.Value;
import org.gaslang.script.api.ScriptType;
import org.gaslang.script.exception.ValueMatchingTypeError;
import org.gaslang.script.run.GasRuntime;

import java.util.HashMap;

public class ScriptNativeObject extends ObjectValue implements NativeObject, Iterable
{
	private final Object jInstance;
	
	public ScriptNativeObject(ScriptType typeInstance, Object jInstance) {
		super(new HashMap<>(), typeInstance);
		this.jInstance = jInstance;
	}

	@Override
	public void set(String name, Value<?> value) {
		jValue().put(name, value);
	}

	public Class<?> getJavaClass() {
		return ((ScriptNativeType)typeInstance).getJavaClass();
	}

	public <T> T getJavaInstance() {
		return (T) jInstance;
	}
	
	public boolean matchValueType(Class<?> arg0) {
		return getJavaClass().equals(arg0);
	}

	public boolean matchValueTypeOrThrow(Class<?> arg0) throws ValueMatchingTypeError {
		if (matchValueType(arg0)) return true;
		else throw new ValueMatchingTypeError();
	}

	void callConstructor(GasRuntime gasRuntime, Tuple args) {
		Value<?> constructor = jValue().get("constructor");
		
		if (constructor == null) return;

		constructor.call(gasRuntime, args);
	}

	@Override
	public HashMap<?, ?> getMap() {
		if (jInstance instanceof Iterable itr) return itr.getMap();
		throw new RuntimeException(jInstance.getClass().getSimpleName() + " isn't an iterable object");
	}
}
